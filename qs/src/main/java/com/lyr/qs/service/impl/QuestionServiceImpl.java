package com.lyr.qs.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyr.qs.constant.Constant;
import com.lyr.qs.entity.Option;
import com.lyr.qs.entity.Question;
import com.lyr.qs.enumentity.QuestionEnum;
import com.lyr.qs.exception.CustomException;
import com.lyr.qs.mapper.*;
import com.lyr.qs.service.OptionService;
import com.lyr.qs.service.QuestionService;
import com.lyr.qs.util.EmptyUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * 问题表
 *
 * @author yurong333
 * @since 2022-12-30
 */
@Service
@AllArgsConstructor
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    private OptionService optionService;

    @Override
    public void createQuestionsAndOptions(JSONArray questions, Integer surveyId) throws CustomException {
        int size = questions.size();
        for (int index = 0; index < size; index++) {
            JSONObject question = questions.getJSONObject(index);
            createQuestionAndOptions(question, index, surveyId);
        }
    }

    /**
     * 生成单个问题 (如果是选择题还需要生成选项)
     * @param question 问题
     * @param index    问题顺序
     * @param surveyId 问卷id
     */
    @Override
    public void createQuestionAndOptions(JSONObject question, int index, int surveyId) throws CustomException {
        Integer code = Integer.parseInt(question.getString(Constant.QUESTION_CODE));
        QuestionEnum questionEnum = QuestionEnum.getEnumByCode(code);
        if (null == questionEnum) {
            throw new CustomException(Constant.FAIL,"根据code找不到对应的问题类型");
        }
        // 判断不同类型的题目并保存
        switch (questionEnum) {
            case RADIO:
            case CHECKBOX:
                Question entity = createQuestion(question, index, surveyId);
                optionService.createOptions(question, entity.getId());
                break;
            case SINGLE_FILL:
                createQuestion(question, index, surveyId);
                break;
            default:
                break;
        }
    }


    @Override
    public boolean checkQuestionIsEmpty(JSONObject question) {
        Integer code = Integer.parseInt(question.getString(Constant.QUESTION_CODE));
        QuestionEnum questionEnum = QuestionEnum.getEnumByCode(code);
        if (null == questionEnum) {
            return true;
        }
        // 判断不同类型的题目
        switch (questionEnum) {
            case RADIO:
            case CHECKBOX:
                if (multipleChoiceQuestionIsEmpty(question)) {
                    return true;
                }
                break;
            case SINGLE_FILL:
                if (singleFillQuestionIsEmpty(question)) {
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 检查单选题和多选题，包括标题、选项、类型码，每一处都不能为空
     * mark: multiple是形容选项，因此multipleChoiceQuestion也就是指多个选项的问题，包含单选题和多选题
     */
    private boolean multipleChoiceQuestionIsEmpty(JSONObject question) {
        String code = question.getString(Constant.QUESTION_CODE);
        String name = question.getString(Constant.QUESTION_NAME);
        JSONArray options = question.getJSONArray(Constant.OPTIONS);
        if (EmptyUtils.isEmpty(code) || EmptyUtils.isEmpty(name) || EmptyUtils.isEmpty(options) || options.size() == 0) {
            return true;
        }

        int size = options.size();
        for (int index = 0; index < size; index++) {
            JSONObject option = options.getJSONObject(index);
            if (EmptyUtils.isEmpty(option)) {
                return true;
            }
            String myOption = option.getString(Constant.MY_OPTION);
            if (EmptyUtils.isEmpty(myOption)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查文本题是否为空
     */
    private boolean singleFillQuestionIsEmpty(JSONObject question) {
        String code = question.getString(Constant.QUESTION_CODE);
        String name = question.getString(Constant.QUESTION_NAME);
        return EmptyUtils.isEmpty(code) || EmptyUtils.isEmpty(name);
    }

    @Override
    public void updateQuestionsAndOptions(JSONArray questions, Integer surveyId) throws CustomException {
        int size = questions.size();
        for (int questionIndex = 0; questionIndex < size; questionIndex++) {
            JSONObject question = questions.getJSONObject(questionIndex);
            // 更新/新增单个问题
            Question questionEntity = updateOrCreateQuestion(question, questionIndex, surveyId);
            // 更新/新增单个问题的所有选项
            updateOrCreateOptions(question, questionEntity);
        }
    }

    /**
     * 更新/新增单个问题的所有选项
     */
    private void updateOrCreateOptions(JSONObject question, Question questionEntity) throws CustomException {
        JSONArray options = question.getJSONArray(Constant.OPTIONS);
        // 单项填空题没有选项
        if(EmptyUtils.isEmpty(options)){
            return;
        }
        int optionSize = options.size();
        for (int optionIndex = 0; optionIndex < optionSize; optionIndex++) {
            JSONObject option = options.getJSONObject(optionIndex);
            optionService.updateOrCreateOption(option,optionIndex, questionEntity.getId());
        }
    }

    /**
     * 更新/新增 问题
     */
    private Question updateOrCreateQuestion(JSONObject question, int index, Integer surveyId) throws CustomException {
        String questionIdStr = question.getString(Constant.QUESTION_ID);
        if (EmptyUtils.isEmpty(questionIdStr)) {
            // 新增问题
            return createQuestion(question,index,surveyId);
        } else {
            // 更新问题
            Integer questionId = Integer.parseInt(questionIdStr);
            return updateQuestion(question,questionId,index,surveyId);
        }
    }

    @Override
    public Question createQuestion(JSONObject question, int index, Integer surveyId) {
        String name = question.getString(Constant.QUESTION_NAME);
        Integer code = Integer.parseInt(question.getString(Constant.QUESTION_CODE));
        Question entity = Question.builder()
                .belongSurveyId(surveyId)
                .questionName(name)
                .questionCode(code)
                .questionType(QuestionEnum.getTypeByCode(code))
                .orderId(index)
                .isRequired(Constant.SURVEY_MUST_ANSWER)
                .build();
        this.save(entity);
        return entity;
    }

    @Override
    public Question updateQuestion(JSONObject question, Integer questionId,int index, Integer surveyId) throws CustomException {
        String name = question.getString(Constant.QUESTION_NAME);
        Question entity = this.getById(questionId);
        if(EmptyUtils.isEmpty(entity)){
            throw new CustomException(Constant.FAIL,"根据问题id找不到问题实体!!! 当前的问题名称是"+name);
        }
        entity.setQuestionName(name);
        entity.setOrderId(index);
        this.updateById(entity);
        return entity;
    }

    /**
     * 删除问题后不用考虑其他问题的排列序号，因为在编辑完成提交之后会统一处理问题的排列序号
     *    比如12345，删除了问题3，不需要重新排列1245问题的序号。
     * @param id 问题id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeQuestionById(Integer id) {
        // 删除问题
        this.removeById(id);
        // 删除问题下的所有选项
        optionService.removeOptionsByQuestionId(id);
        // todo 删除问题下的所有回答
    }

    @Override
    public List<Question> getQuestionsBySurveyId(Integer surveyId) {
        return this.lambdaQuery().eq(Question::getBelongSurveyId, surveyId).list();
    }

    @Override
    public List<Question> getQuestionsAndOptionsBySurveyId(Integer surveyId) {
        // 获取所有问题
        List<Question> questions = getQuestionsBySurveyId(surveyId);
        // 组装选项
        for (Question question : questions) {
            List<Option> options = optionService.getOptionsByQuestionId(question.getId());
            question.setOptions(options);
            // todo 组装问题的其他部分
        }
        return questions;
    }

}
