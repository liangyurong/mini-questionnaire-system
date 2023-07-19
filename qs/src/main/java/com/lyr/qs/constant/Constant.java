package com.lyr.qs.constant;

/**
 * 常量
 * @author yurong333
 * @since 2023-01-10
 */
public class Constant {

    /**
     * 问卷分页查询显示数据
     */
    public static Integer SURVEY_QUERY_CURRENT = 1;
    public static Integer SURVEY_QUERY_SIZE = 20;

    /**
     * 问卷关键词
     */
    public static String ID = "id";                                // 问卷id
    public static String TITLE = "title";                          // 问卷标题
    public static String DESCRIPTION = "description";              // 问卷描述

    /**
     * 问题关键词
     */
    public static String QUESTIONS = "questions";                  // 问卷的所有问题
    public static String QUESTION_ID = "questionId";               // 问卷的所有问题
    public static String BELONG_SURVEY_ID = "belongSurveyId";      // 问题所属的问卷id
    public static String QUESTION_CODE = "code";                   // 问题code
    public static String QUESTION_NAME = "name";                   // 问题名称

    /**
     * 选项关键词
     */
    public static String OPTIONS = "options";                      // 问题的所有选项
    public static String OPTION_ID = "optionId";                   // 问题的所有选项
    public static String BELONG_QUESTION_ID = "belongQuestionId";  // 选项所属的问题id
    public static String MY_OPTION = "myOption";                   // 选项的内容
    public static String MY_TEXT = "myText";                       // 单项选择题的内容

    /**
     * 问卷可见性 0-不可见 1-可见
     */
    public static Integer SURVEY_VISIBILITY_UNABLE = 0;
    public static Integer SURVEY_VISIBILITY_ABLE = 1;

    /**
     * 问卷状态 0-设计 1-发布 2-结束
     */
    public static Integer SURVEY_STATE_DESIGN = 0;
    public static Integer SURVEY_STATE_PUBLISH = 1;
    public static Integer SURVEY_STATE_FINISH = 2;
    public static Integer SURVEY_STATE_ALL = -1;                    // 全部状态

    /**
     * 问卷是否必答 0非必答 1必答
     */
    public static Integer SURVEY_MUST_ANSWER = 0;
    public static Integer SURVEY_NOT_MUST_ANSWER = 1;

    /**
     * 状态码
     */
    public static Integer SUCCESS = 200;
    public static Integer FAIL = 500;
    public static Integer NOT_FOUND = 404;

}
