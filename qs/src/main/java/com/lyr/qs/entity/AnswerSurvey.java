package com.lyr.qs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 答卷表的基本信息
 *
 * @author yurong333
 * @since 2022-12-30
 */
@TableName("t_answer_survey")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class AnswerSurvey implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 答卷信息表主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 问卷表主键
     */
    private Integer surveyId;

    /**
     * 是否可见 0-可见 1-不可见
     */
    private Integer visibility;

    /**
     * 填写日期
     */
    private LocalDateTime createTime;


}
