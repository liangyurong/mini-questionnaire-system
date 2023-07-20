package com.lyr.qs.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyr.qs.entity.Option;
import com.lyr.qs.exception.CustomException;

import java.util.List;

/**
 * 选项表
 *
 * @author yurong333
 * @since 2022-12-30
 */
public interface OptionService extends IService<Option> {

    /**
     * 生成所有选项
     * @param question 问题的json数据
     * @param questionId 问题id
     */
    void createOptions(JSONObject question, Integer questionId);

    /**
     * 生成选项实体
     * @param option 选项的json数据
     * @param index 选项的顺序
     * @param questionId 问题id
     */
    void createOption(JSONObject option, int index, Integer questionId);

    /**
     * 更新选项实体
     * @param option 选项的json数据
     * @param index 选项的顺序
     * @param questionId 问题id
     */
    void updateOption(JSONObject option,Integer index, Integer questionId) throws CustomException;

    /**
     * 更新或新增 选项实体
     * @param option 选项的json数据
     * @param questionId 问题id
     */
    void updateOrCreateOption(JSONObject option, Integer index, Integer questionId) throws CustomException;

    /**
     * 根据问题id删除问题的所有选项
     * @param questionId
     */
    void removeOptionsByQuestionId(Integer questionId);

    /**
     * 根据问题id获取所有的选项
     * @param questionId 问题id
     * @return
     */
    List<Option> getOptionsByQuestionId(Integer questionId);
}
