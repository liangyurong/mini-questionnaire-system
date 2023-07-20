package com.lyr.qs.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyr.qs.constant.Constant;
import com.lyr.qs.dto.SurveyDto;
import com.lyr.qs.entity.Survey;
import com.lyr.qs.exception.CustomException;
import com.lyr.qs.mapper.SurveyMapper;
import com.lyr.qs.service.QuestionService;
import com.lyr.qs.service.SurveyService;
import com.lyr.qs.util.EmptyUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.Date;
import java.util.Optional;

/**
 * 问卷表 服务实现类
 *
 * @author yurong333
 * @since 2022-12-30
 */
@Service
@AllArgsConstructor
@Slf4j
public class SurveyServiceImpl extends ServiceImpl<SurveyMapper, Survey> implements SurveyService {

    private QuestionService questionService;

    // todo 首先从redis中获取
    // 对应的 `handleException` 方法需要位于 `ExceptionUtil` 类中，并且必须为 static 函数.
    @Override
    public Page<Survey> page(SurveyDto dto) {
        // 如果查询参数为空，则使用默认分页查询条件
        if (dto == null) {
            return page(new Page<>(Constant.SURVEY_QUERY_CURRENT, Constant.SURVEY_QUERY_SIZE), null);
        }
        // 获取当前页码，若未指定则使用默认值
        int current = Optional.ofNullable(dto.getCurrent()).orElse(Constant.SURVEY_QUERY_CURRENT);
        // 获取每页显示数量，若未指定或超过最大限制则使用默认值
        int size = Optional.ofNullable(dto.getSize()).filter(s -> s <= Constant.SURVEY_QUERY_SIZE).orElse(Constant.SURVEY_QUERY_SIZE);
        // 设置查询条件：问卷可见、按照创建时间降序排序、匹配Title、匹配Description、匹配SurveyState
        LambdaQueryWrapper<Survey> wrapper = new LambdaQueryWrapper<Survey>()
                .eq(Survey::getVisibility, Constant.SURVEY_VISIBILITY_ABLE)
                .orderByDesc(Survey::getCreateTime)
                .like(!StringUtils.isEmpty(dto.getTitle()), Survey::getTitle, dto.getTitle())
                .like(!StringUtils.isEmpty(dto.getDescription()), Survey::getDescription, dto.getDescription())
                .eq(!StringUtils.isEmpty(dto.getSurveyState()), Survey::getSurveyState, dto.getSurveyState());
        return page(new Page<>(current, size), wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createQuestionnaire(JSONObject json) throws CustomException {
        // 检查问卷的合法性
        checkCreateSurveyDataIsEmpty(json);
        // 生成问卷实体
        String title = json.getString(Constant.TITLE);
        String description = json.getString(Constant.DESCRIPTION);
        Survey survey = createSurvey(title, description);
        // 生成所有问题实体和所有选项实体
        JSONArray questions = json.getJSONArray(Constant.QUESTIONS);
        questionService.createQuestionsAndOptions(questions, survey.getId());
    }


    @Override
    public void checkCreateSurveyDataIsEmpty(JSONObject json) throws CustomException {
        // 所有数据是否为空
        if (EmptyUtils.isEmpty(json)) {
            throw new CustomException(Constant.FAIL,"问卷数据为空");
        }

        // 问卷标题是否为空
        String title = json.getString(Constant.TITLE);
        if (EmptyUtils.isEmpty(title)) {
            throw new CustomException(Constant.FAIL,"问卷标题为空");
        }
        // 问卷描述是否为空
        String description = json.getString(Constant.DESCRIPTION);
        if (EmptyUtils.isEmpty(description)) {
            throw new CustomException(Constant.FAIL,"问卷描述为空");
        }
        // 问题数量是否为空
        JSONArray questions = json.getJSONArray(Constant.QUESTIONS);
        if (EmptyUtils.isEmpty(questions) || questions.size() == 0) {
            throw new CustomException(Constant.FAIL,"问题数量为空");
        }
        // 所有问题的题目和选项是否为空
        int size = questions.size();
        for (int index = 0; index < size; index++) {
            JSONObject question = questions.getJSONObject(index);
            if (questionService.checkQuestionIsEmpty(question)) {
                throw new CustomException(Constant.FAIL,"问题标题或选项标题或选项数量为空，请仔细检查");
            }
        }
    }


    @Override
    public Survey createSurvey(String title, String description) {
        Survey survey = Survey.builder()
                .title(title)
                .description(description)
                .createTime(new Date())
                .surveyState(Constant.SURVEY_STATE_DESIGN)
                .surveyQuestionNum(0)
                .visibility(Constant.SURVEY_VISIBILITY_ABLE)
                .surveyQuestionNum(0)
                .answerNum(0)
                .build();
        this.save(survey);
        return survey;
    }

    @Override
    public JSONObject getQuestionnaireById(Integer id) {
        // 获取Survey
        Survey survey = getSurveyById(id);
        if (survey == null) {
            return null;
        }
        // 根据问卷id获取所有问题(选择题包含选项)
        JSONArray questions = questionService.getQuestionsBySurveyId(id);
        // 组装问卷数据
        JSONObject surveyData = new JSONObject();
        surveyData.put(Constant.TITLE,survey.getTitle());
        surveyData.put(Constant.DESCRIPTION,survey.getDescription());
        surveyData.put(Constant.QUESTIONS,questions);
        return surveyData;
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
        // 检查问卷数据合法性
        checkCreateSurveyDataIsEmpty(json);
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

    @Override
    public void deleteById(Integer id) {
        Survey survey = this.getById(id);
        survey.setVisibility(Constant.SURVEY_VISIBILITY_UNABLE);
        this.updateById(survey);
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
