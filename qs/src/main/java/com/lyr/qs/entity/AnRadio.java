package com.lyr.qs.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 单选题答题表
 *
 * @author yurong333
 * @since 2022-12-30
 */
@TableName("t_an_radio")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class AnRadio implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 单选题答题表主键
     */
    private Integer id;

    /**
     * 问卷表的主键
     */
    private Integer belongSurveyId;

    /**
     * 问题表主键
     */
    private Integer belongQuestionId;

    /**
     * 被选中的选项id
     */
    private Integer optionId;

    /**
     * 被选中的选项的排序id，从0开始
     */
    private Integer orderId;


}
