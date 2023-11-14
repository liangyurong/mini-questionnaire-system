package com.lyr.qs.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyr.qs.entity.Question;
import com.lyr.qs.exception.CustomException;
import com.lyr.qs.form.QuestionUpdateForm;
import com.lyr.qs.vo.QuestionVO;

import java.util.List;

/**
 * 问题表
 *
 * @author yurong333
 * @since 2022-12-30
 */
public interface QuestionService extends IService<Question> {

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
     * 备注：虽然是更新表单，但这里是新增问题
     * 生成单选题/多选题/单项填空题
     * @param form 问题表单
     * @param surveyId 问卷id
     * @return
     */
    Question createQuestion(QuestionUpdateForm form,Integer surveyId);


    /**
     * 更新问题和问题的选项
     * @param updateQuestionForm
     * @return
     * @throws CustomException
     */
    Question updateQuestionAndOptions(QuestionUpdateForm updateQuestionForm) throws CustomException;

    /**
     * 根据问题id删除问题和问题的全部选项
     * @param questionId 问题id
     */
    void removeQuestionAndOptions(Integer questionId);

    /**
     * 根据问卷id获取所有问题
     * @param surveyId 问卷id
     * @return 所有问题
     */
    List<QuestionVO> getQuestionsBySurveyId(Integer surveyId);


    /**
     * 根据问卷id删除所有的问题
     */
    List<Integer> deleteQuestionsBySurveyId(Integer surveyId);
}
