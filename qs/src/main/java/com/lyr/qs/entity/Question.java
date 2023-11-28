package com.lyr.qs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Schema(description = "问题表")
@TableName("t_question")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Question implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "问题表主键")
    private Integer id;

    @Schema(description = "问题所属问卷，对应问卷表主键")
    private Integer surveyId;

    @Schema(description = "问题描述")
    private String content;

    @Schema(description = "问题类型 0-单选题 1-多选题 2-单项填空题")
    private String type;

    @Schema(description = "问题在问卷中的排列序号，从0开始")
    private Integer orderNumber;

    @Schema(description = "是否必答 0非必答 1必答")
    private Integer isRequired;

}
