package com.lyr.qs.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 多项选择题答题表
 *
 * @author yurong333
 * @since 2022-12-30
 */
@TableName("t_an_checkbox")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class AnCheckbox implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 多项选择题主键
     */
    private Integer id;

    /**
     * 问卷id
     */
    private Integer belongSurveyId;

    /**
     * 题目id
     */
    private Integer belongQuestionId;

    /**
     * 选项id
     */
    private Integer optionId;

    /**
     * 选项的排序id
     */
    private Integer orderId;


}
