package com.lyr.qs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 问卷表
 *
 * @author yurong333
 * @since 2022-12-30
 */
@Schema(description = "问卷表")
@TableName("t_survey")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Survey implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "问卷表主键")
    private Integer id;

    @Schema(description = "问卷名称")
    private String title;

    @Schema(description = "问卷描述")
    private String description;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    @Schema(description = "问卷状态  0 默认设计状态  1发布 2结束")
    private Integer surveyState;

    @Schema(description = "问卷的题目数")
    private Integer surveyQuestionNum;


    @Schema(description = "回答次数")
    private Integer answerNum;

    @Schema(description = "是否显示  0显示  1不显示 。主要用于问卷删除")
    private Integer visibility;


}
