package com.lyr.qs.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyr.qs.entity.Question;
import com.lyr.qs.exception.CustomException;

import java.util.List;

/**
 * 问题表
 *
 * @author yurong333
 * @since 2022-12-30
 */
public interface QuestionService extends IService<Question> {

    /**
     * 创建所有问题和所有选项
     * @param questions 所有问题的json数据
     * @param surveyId 问卷id
     */
    void createQuestionsAndOptions(JSONArray questions, Integer surveyId) throws CustomException;

    /**
     * 创建单个问题
     * @param question
     * @param index
     * @param surveyId
     */
    void createQuestionAndOptions(JSONObject question, int index, int surveyId) throws CustomException;

    /**
     * 检查单选题、多选题、单项填空题的数据是否为空，只要有一处为空则返回true，否则返回false
     * @param question
     * @return
     */
    boolean checkQuestionIsEmpty(JSONObject question);

    /**
     * 生成单选题/多选题/单项填空题
     * @param question 问题
     * @param index 问题顺序
     * @param surveyId 问卷id
     * @return 问题实体
     */
    Question createQuestion(JSONObject question, int index, Integer surveyId);

    /**
     * 更新问题
     * @param question
     * @param questionId
     * @param index
     * @param surveyId
     * @return
     * @throws CustomException
     */
    Question updateQuestion(JSONObject question,Integer questionId, int index, Integer surveyId) throws CustomException;

    /**
     * 更新问题实体和选项实体
     * @param questions
     */
    void updateQuestionsAndOptions(JSONArray questions,Integer surveyId) throws CustomException;

    /**
     * 根据问题id删除问题
     * @param id 问题id
     */
    void removeQuestionById(Integer id);

    /**
     * 根据问卷id获取所有问题
     * @param surveyId 问卷id
     * @return 所有问题
     */
    List<Question> getQuestionsBySurveyId(Integer surveyId);

    /**
     * 根据问卷id获取所有问题和问题的所有选项（仅限选择题）
     * @param surveyId 问卷id
     * @return 所有问题和问题的所有选项（仅限选择题）
     */
    List<Question> getQuestionsAndOptionsBySurveyId(Integer surveyId);
}
