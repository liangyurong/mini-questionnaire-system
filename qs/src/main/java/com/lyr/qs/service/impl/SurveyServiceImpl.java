package com.lyr.qs.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyr.qs.constant.Constant;
import com.lyr.qs.dto.SurveyDto;
import com.lyr.qs.entity.Option;
import com.lyr.qs.entity.Question;
import com.lyr.qs.entity.Survey;
import com.lyr.qs.exception.CustomException;
import com.lyr.qs.form.OptionAddForm;
import com.lyr.qs.form.QuestionAddForm;
import com.lyr.qs.form.SurveyAddForm;
import com.lyr.qs.mapper.SurveyMapper;
import com.lyr.qs.service.OptionService;
import com.lyr.qs.service.QuestionService;
import com.lyr.qs.service.SurveyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 问卷表
 *
 * @author yurong333
 * @since 2022-12-30
 */
@Service
@AllArgsConstructor
@Slf4j
public class SurveyServiceImpl extends ServiceImpl<SurveyMapper, Survey> implements SurveyService {

    @Resource
    private QuestionService questionService;
    @Resource
    private OptionService optionService;

    // todo 首先从redis中获取
    @Override
    public Page<Survey> page(SurveyDto dto) {
        // 查询条件
        LambdaQueryWrapper<Survey> wrapper = this.getWrapper(dto);
        // 当前页，分页大小
        int current = this.getCurrent(dto);
        int size = this.getSize(dto);
        // 分页结果
        return page(new Page<>(current, size), wrapper);
    }

    private LambdaQueryWrapper<Survey> getWrapper(SurveyDto dto){
        LambdaQueryWrapper<Survey> wrapper = new LambdaQueryWrapper<>();
        if(dto == null){
            wrapper.eq(Survey::getVisibility, Constant.SURVEY_VISIBILITY_ABLE).orderByDesc(Survey::getCreateTime);
        }else {
            // 设置查询条件：问卷可见、按照创建时间降序排序、匹配Title、匹配Description、匹配SurveyState
            wrapper.eq(Survey::getVisibility, Constant.SURVEY_VISIBILITY_ABLE)
                    .orderByDesc(Survey::getCreateTime)
                    .like(!StringUtils.isEmpty(dto.getTitle()), Survey::getTitle, dto.getTitle())
                    .like(!StringUtils.isEmpty(dto.getDescription()), Survey::getDescription, dto.getDescription())
                    .eq(!StringUtils.isEmpty(dto.getSurveyState()), Survey::getSurveyState, dto.getSurveyState());
        }
        return wrapper;
    }


    private int getCurrent(SurveyDto dto) {
        if (dto == null) {
            return Constant.SURVEY_QUERY_CURRENT;
        }else {
            // 若未指定则使用默认值
            return Optional.ofNullable(dto.getCurrent()).orElse(Constant.SURVEY_QUERY_CURRENT);
        }
    }

    private int getSize(SurveyDto dto) {
        if(dto == null){
            return Constant.SURVEY_QUERY_SIZE;
        }else {
            // 若未指定或超过最大限制则使用默认值
            return Optional.ofNullable(dto.getSize()).filter(s -> s <= Constant.SURVEY_QUERY_SIZE).orElse(Constant.SURVEY_QUERY_SIZE);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createQuestionnaire(SurveyAddForm surveyAddForm) {
        // 创建Survey并保存
        Integer surveyId = this.buildSurvey(surveyAddForm.getTitle(), surveyAddForm.getDescription());
        // 创建多个Question并批量保存
        this.buildQuestions(surveyAddForm.getQuestions(), surveyId);
        return surveyId;
    }

    // 创建单个Survey并保存
    private Integer buildSurvey(String title, String description) {
        Survey survey = Survey.builder()
                .title(title)
                .description(description)
                .createTime(new Date())
                .surveyState(Constant.SURVEY_STATE_DESIGN)
                .surveyQuestionNum(0)
                .visibility(Constant.SURVEY_VISIBILITY_ABLE)
                .answerNum(0)
                .build();
        this.save(survey);
        return survey.getId();
    }

    // 创建多个Question并批量保存
    private void buildQuestions(List<QuestionAddForm> questionAddForms, Integer surveyId) {
        for (int i = 0; i < questionAddForms.size(); i++) {
            QuestionAddForm questionAddForm = questionAddForms.get(i);
            Integer questionId = this.buildQuestion(questionAddForm, surveyId,i);
            // 创建多个Option并保存
            this.buildOptions(questionAddForm.getOptions(), questionId);
        }
    }

    // 创建单个question并保存
    private Integer buildQuestion(QuestionAddForm add,Integer surveyId,Integer orderNumber){
        Question question = new Question();
        question.setSurveyId(surveyId);
        question.setContent(add.getContent());
        question.setType(add.getType());
        question.setOrderNumber(orderNumber);
        question.setIsRequired(Constant.SURVEY_MUST_ANSWER);
        questionService.save(question);
        return question.getId();
    }

    // 创建多个option并保存
    private void buildOptions(List<OptionAddForm> optionAddForms, Integer questionId) {
        for (int i = 0; i < optionAddForms.size(); i++) {
            this.buildOption(optionAddForms.get(i),questionId,i);
        }
    }

    // 创建单个option并保存
    private Integer buildOption(OptionAddForm optionAddForm, Integer questionId,Integer orderNumber) {
        Option option = new Option();
        option.setQuestionId(questionId);
        option.setContent(optionAddForm.getContent());
        option.setOrderNumber(orderNumber);
        optionService.save(option);
        return option.getId();
    }

    @Override
    public Survey getById(Integer id) {
        Survey survey = getSurveyById(id);
        if (survey == null) {
            return null;
        }
        // 根据问卷id获取所有问题和问题的所有选项（仅限选择题）
        List<Question> questions =  questionService.getQuestionsAndOptionsBySurveyId(id);
//        survey.setQuestions(questions);
        return survey;
    }

    @Override
    public Survey getSurveyById(Integer id) {
        return this.lambdaQuery()
                .eq(Survey::getId, id)
                .eq(Survey::getVisibility,Constant.SURVEY_VISIBILITY_ABLE)
                .one();
    }

    /**
     * 1、问题/选项已存在，进行更新
     * 2、问题/选项不存在，进行插入
     * @param json 问卷数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateQuestionnaire(JSONObject json) throws CustomException {
        checkSurveyId(json.getString(Constant.ID));

        // 更新问卷实体
        Integer id = Integer.parseInt(json.getString(Constant.ID));
        String title = json.getString(Constant.TITLE);
        String description = json.getString(Constant.DESCRIPTION);
        updateSurvey(id,title,description);

        // 更新问题实体和选项实体
        JSONArray questions = json.getJSONArray(Constant.QUESTIONS);
        questionService.updateQuestionsAndOptions(questions,id);
    }

    /**
     * 更新问卷时，需要检查问卷id是否为空
     */
    private void checkSurveyId(String id) throws CustomException {
        if(StringUtils.isEmpty(id)){
            throw new CustomException(500,"问卷id为空，请仔细检查再提交");
        }
    }

    /**
     * 更新问卷
     */
    private void updateSurvey(Integer id, String title, String description) throws CustomException {
        Survey survey = this.getSurveyById(id);
        if(survey == null){
            // todo 抛出自定义的异常，事务会回滚吗？
            throw new CustomException(Constant.FAIL,"根据问卷id找不到问卷实体");
        }
        survey.setTitle(title);
        survey.setDescription(description);
        this.saveOrUpdate(survey);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void remove(Integer id) {
        // 删除问卷
        this.deleteById(id);
        // 删除问题
        List<Integer> questionIds = questionService.deleteQuestionsBySurveyId(id);
        // 删除选项
        optionService.deleteOptionsByQuestionIds(questionIds);
        // 删除选项答案 TODO
    }

    // 根据id删除Survey
    private boolean deleteById(Integer id) {
        Survey survey = this.getById(id);
        survey.setVisibility(Constant.SURVEY_VISIBILITY_UNABLE);
        return this.updateById(survey);
    }

    @Override
    public void updateSurveyState(Integer id, Integer surveyState) {
        Survey survey = this.getById(id);
        if(survey == null){
            return;
        }
        // 问卷结束则不可变更状态
        if(survey.getSurveyState().equals(Constant.SURVEY_STATE_FINISH)){
            return;
        }
        survey.setSurveyState(surveyState);
        this.updateById(survey);
    }

    @Override
    public void fillQuestionnaire(JSONObject json) throws CustomException{
        checkFillSurveyDataIsEmpty(json);
        Integer id = Integer.parseInt(json.getString(Constant.ID));

    }

    @Override
    public void checkFillSurveyDataIsEmpty(JSONObject json) throws CustomException {

    }

}
