package com.lyr.qs.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyr.qs.dto.SurveyDto;
import com.lyr.qs.entity.Survey;
import com.lyr.qs.exception.CustomException;

/**
 * 问卷表
 *
 * @author yurong333
 * @since 2022-12-30
 */
public interface SurveyService extends IService<Survey> {

    /**
     * 分页查询问卷
     * 1、问卷必须是可见的
     * 2、按照时间倒序排序也就是最新时间的记录在前面
     * @return
     */
    Page<Survey> page(SurveyDto dto);

    /**
     * 创建一张问卷，包含问题和选项
     * @param json 问卷数据
     */
    void createQuestionnaire(JSONObject json) throws CustomException;

    /**
     * 保存问卷实体
     * @param title
     * @param description
     * @return
     */
    Survey createSurvey(String title, String description);

    /**
     * 根据问卷id获取一张问卷内容
     * @param id 问卷id
     * @return
     */
    Survey getSurveyDataById(Integer id);

    /**
     * 根据问卷id获取问卷,并且该问卷具有可见性，因此不能使用MyBatisPlus自带的getById()方法
     * @param id 问卷id
     * @return
     */
    Survey getSurveyById(Integer id);

    /**
     * 更新问卷
     * @param json 问卷数据
     */
    void updateQuestionnaire(JSONObject json) throws CustomException;

    /**
     * 根据问卷id删除问卷。只是将问卷的visibility改为0，在物理层面并没有删除
     */
    void deleteById(Integer id);

    /**
     * 更新问卷状态
     * @param id 问卷id
     * @param surveyState 问卷状态
     */
    void updateSurveyState(Integer id, Integer surveyState);

    /**
     * 填写问卷
     * @param json
     */
    void fillQuestionnaire(JSONObject json) throws CustomException;

    /**
     * 创建问卷时，需要检查问卷合法性
     *      1、传递的问卷数据不能为空
     *      2、问卷标题不能为空
     *      3、问卷描述不能为空
     *      4、问题数量不能为0
     *      5、问题题目不能为空
     *      6、选项数量不能为0
     *      7、选项题目不能为空
     *  @param json 问卷数据
     */
    void checkCreateSurveyDataIsEmpty(JSONObject json) throws CustomException;

    /**
     * 检查填写数据是否符合规则
     *
     * @param json
     */
    void checkFillSurveyDataIsEmpty(JSONObject json) throws CustomException;
}
