package com.lyr.qs.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyr.qs.entity.Option;
import com.lyr.qs.exception.CustomException;
import com.lyr.qs.form.OptionUpdateForm;
import com.lyr.qs.vo.OptionVO;

import java.util.List;

/**
 * 选项表
 *
 * @author yurong333
 * @since 2022-12-30
 */
public interface OptionService extends IService<Option> {

    /**
     * 根据问题id获取选项列表
     */
    List<OptionVO> getOptionsByQuestionId(Integer questionId);


    /**
     * 根据问题id删除选项
     */
    void deleteOptionsByQuestionId(Integer questionId);

    /**
     * 根据问题id列表删除选项
     */
    void deleteOptionsByQuestionIds(List<Integer> questionIds);

    /**
     * 更新问题的所有选项
     * @param questionId
     * @param options
     */
    void updateOptions(Integer questionId, List<OptionUpdateForm> options);

    /**
     * 生成所有选项
     * @param question 问题的json数据
     * @param questionId 问题id
     */


    /**
     * 生成选项实体
     * @param option 选项的json数据
     * @param index 选项的顺序
     * @param questionId 问题id
     */


    /**
     * 更新选项实体
     * @param option 选项的json数据
     * @param index 选项的顺序
     * @param questionId 问题id
     */

    /**
     * 更新或新增 选项实体
     * @param option 选项的json数据
     * @param questionId 问题id
     */

    /**
     * 根据问题id删除问题的所有选项
     * @param questionId
     */

    /**
     * 根据问题id获取所有的选项
     * @param questionId 问题id
     * @return
     */
}
