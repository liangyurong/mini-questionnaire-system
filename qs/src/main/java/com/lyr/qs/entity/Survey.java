package com.lyr.qs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 问卷表
 *
 * @author yurong333
 * @since 2022-12-30
 */
@TableName("t_survey")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Survey implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 问卷名称
     */
    private String title;

    /**
     * 问卷描述
     */
    private String description;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 问卷状态  0 默认设计状态  1发布 2结束
     */
    private Integer surveyState;

    /**
     * 问卷的题目数
     */
    private Integer surveyQuestionNum;

    /**
     * 回答次数
     */
    private Integer answerNum;

    /**
     * 是否显示  1显示 0不显示 。主要用于问卷删除
     */
    private Integer visibility;


}
