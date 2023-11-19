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
    public static final Integer SURVEY_QUERY_CURRENT = 1;
    public static final Integer SURVEY_QUERY_SIZE = 20;

    /**
     * 问卷可见性 0-可见 1-不可见
     */
    public static final Integer SURVEY_VISIBILITY_ABLE = 0;
    public static final Integer SURVEY_VISIBILITY_UNABLE = 1;

    /**
     * 问卷状态 0-设计 1-发布 2-结束
     */
    public static final Integer SURVEY_STATE_DESIGN = 0;
    public static final Integer SURVEY_STATE_PUBLISH = 1;
    public static final Integer SURVEY_STATE_FINISH = 2;
    public static final Integer SURVEY_STATE_ALL = -1;                    // 全部状态

    /**
     * 问卷是否必答 0非必答 1必答
     */
    public static final Integer SURVEY_MUST_ANSWER = 0;
    public static final Integer SURVEY_NOT_MUST_ANSWER = 1;

    /**
     * 状态码
     */
    public static final Integer SUCCESS = 200;
    public static final Integer FAIL = 500;
    public static final Integer NOT_FOUND = 404;

    /**
     * 问卷类型
     */
    public static final String QUESTION_TYPE_SINGLE_OPTION = "0";
    public static final String QUESTION_TYPE_MULTIPLE_OPTION = "1";
    public static final String QUESTION_TYPE_SINGLE_FILL = "2";


}
