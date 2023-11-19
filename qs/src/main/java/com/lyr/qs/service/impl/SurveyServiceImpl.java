package com.lyr.qs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
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
import com.lyr.qs.form.*;
import com.lyr.qs.mapper.SurveyMapper;
import com.lyr.qs.service.OptionService;
import com.lyr.qs.service.QuestionService;
import com.lyr.qs.service.SurveyService;
import com.lyr.qs.vo.QuestionVO;
import com.lyr.qs.vo.SurveyVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 问卷表
 *
 * @author yurong333
 * @since 2022-12-30
 */
@Service
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

    // 查询条件
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

    // 当前页
    private int getCurrent(SurveyDto dto) {
        if (dto == null) {
            return Constant.SURVEY_QUERY_CURRENT;
        }else {
            // 若未指定则使用默认值
            return Optional.ofNullable(dto.getCurrent()).orElse(Constant.SURVEY_QUERY_CURRENT);
        }
    }

    // 当前条数
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
    public Integer createQuestionnaire(SurveyAddForm form) {
        // 创建Survey并保存
        Integer surveyId = this.buildSurvey(form.getTitle(), form.getDescription());
        // 创建多个Question并批量保存
        this.buildQuestions(form.getQuestions(), surveyId);
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
    public SurveyVO getSurveyVOById(Integer id) {
        Survey survey =this.getById(id);
        SurveyVO vo = BeanUtil.copyProperties(survey, SurveyVO.class);
        vo.setQuestions(questionService.getQuestionsBySurveyId(id));
        return vo;
    }

    @Override
    public Survey getById(Integer id) {
        return this.lambdaQuery()
                .eq(Survey::getId, id)
                .eq(Survey::getVisibility, Constant.SURVEY_VISIBILITY_ABLE)
                .one();
    }

    /**
     * 1、问题/选项已存在，进行更新
     * 2、问题/选项不存在，进行插入
     * @param form 问卷数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SurveyUpdateForm form) throws CustomException {
        // 找不到问卷 || 问卷不处于设计状态
        Survey survey = this.getById(form.getId());
        if(survey == null || !Objects.equals(survey.getSurveyState(), Constant.SURVEY_STATE_DESIGN)){
            throw new CustomException("找不到问卷 || 问卷不处于设计状态");
        }

        // 更新问卷信息
        survey.setTitle(form.getTitle());
        survey.setDescription(form.getDescription());
        this.updateById(survey);

        // 已存在的问题
        List<Integer> oldQuestionIds = questionService.getQuestionsBySurveyId(form.getId()).stream().map(QuestionVO::getId).collect(Collectors.toList());

        // 需要新增的问题（没有id的就是新增）
        List<QuestionUpdateForm> needAddQuestions = form.getQuestions().stream()
                .filter(question -> ObjectUtil.isNull(question.getId()))
                .collect(Collectors.toList());
        // 新增问题
        for (QuestionUpdateForm updateForm : needAddQuestions) {
            questionService.createQuestion(updateForm, form.getId());
        }

        // 需要更新的问题
        List<QuestionUpdateForm> needUpdateQuestions = form.getQuestions().stream()
                .filter(question -> oldQuestionIds.contains(question.getId()))
                .collect(Collectors.toList());
        // 更新问题和问题的选项
        for (QuestionUpdateForm updateQuestionForm : needUpdateQuestions) {
            // 这里应该有逻辑来更新问题
            questionService.updateQuestionAndOptions(updateQuestionForm);
        }

        // 需要更新的问题id
        List<Integer> needUpdateIds = needUpdateQuestions.stream().map(QuestionUpdateForm::getId).collect(Collectors.toList());
        // 需要删除的问题
        List<Integer> needDeleteQuestionIds = oldQuestionIds.stream()
                .filter(id -> !needUpdateIds.contains(id))
                .collect(Collectors.toList());
        // 删除问题和问题选项
        for (Integer deleteQuestionId : needDeleteQuestionIds) {
            questionService.removeQuestionAndOptions(deleteQuestionId);
        }

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
//        Survey survey = this.getById(id);
//        survey.setVisibility(Constant.SURVEY_VISIBILITY_UNABLE);
//        return this.updateById(survey);
        return true;
    }

    @Override
    public void updateSurveyState(Integer id, Integer surveyState) {
//        SurveyVo survey = this.getById(id);
//        if(survey == null){
//            return;
//        }
//        // 问卷结束则不可变更状态
//        if(survey.getSurveyState().equals(Constant.SURVEY_STATE_FINISH)){
//            return;
//        }
//        survey.setSurveyState(surveyState);
//        this.updateById(survey);
    }

    @Override
    public void fillQuestionnaire(JSONObject json) throws CustomException{


    }

    @Override
    public void checkFillSurveyDataIsEmpty(JSONObject json) throws CustomException {

    }

}
