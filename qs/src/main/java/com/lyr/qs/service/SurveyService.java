package com.lyr.qs.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyr.qs.dto.SurveyDto;
import com.lyr.qs.entity.Survey;
import com.lyr.qs.exception.CustomException;
import com.lyr.qs.form.FillSurveyForm;
import com.lyr.qs.form.SurveyAddForm;
import com.lyr.qs.form.SurveyUpdateForm;
import com.lyr.qs.vo.SurveyVO;

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
     * 创建问卷
     * @param form
     * @throws CustomException
     */
    Integer createQuestionnaire(SurveyAddForm form);

    /**
     * 根据问卷id获取一张问卷内容
     * @param id 问卷id
     * @return
     */
    Survey getById(Integer id) throws CustomException;

    /**
     * 根据问卷id获取问卷,并且该问卷具有可见性，因此不能使用MyBatisPlus自带的getById()方法
     * @param id 问卷id
     * @return
     */
    SurveyVO getSurveyVOById(Integer id);

    /**
     * 更新问卷
     * @param form 问卷数据
     */
    void update(SurveyUpdateForm form) throws CustomException;

    /**
     * 根据问卷id删除问卷。只是将问卷的visibility改为0，在物理层面并没有删除
     */
    void remove(Integer id);

    /**
     * 更新问卷状态
     * @param id 问卷id
     * @param surveyState 问卷状态
     */
    void updateSurveyState(Integer id, Integer surveyState);

    /**
     * 填写问卷
     * @param fillSurveyForm
     */
    void fillQuestionnaire(FillSurveyForm fillSurveyForm) throws CustomException;

}
