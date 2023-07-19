package com.lyr.qs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 问题表
 *
 * @author yurong333
 * @since 2022-12-30
 */
@TableName("t_question")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class Question implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 问题表主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 问题所属问卷，对应问卷表主键
     */
    private Integer belongSurveyId;

    /**
     * 问题描述
     */
    private String questionName;

    /**
     * 问题类型
     */
    private String questionType;

    /**
     * 问题类型编码，0-单选题 1-多选题 2-单项填空题
     */
    private Integer questionCode;

    /**
     * 问题在问卷中的排列序号，从0开始
     */
    private Integer orderId;

    /**
     * 是否必答 0非必答 1必答
     */
    private Integer isRequired;


}
