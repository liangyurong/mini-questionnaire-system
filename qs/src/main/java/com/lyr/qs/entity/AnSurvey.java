package com.lyr.qs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 答卷表的基本信息
 *
 * @author yurong333
 * @since 2022-12-30
 */
@TableName("t_an_survey")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnSurvey implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "答卷信息表主键")
    private Integer id;

    @Schema(description = "用户id")
    private Integer userId;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "问卷表主键")
    private Integer surveyId;

    @Schema(description = "是否可见 0-可见 1-不可见")
    private Integer visibility;

    @Schema(description = "填写日期")
    private LocalDateTime createTime;


}
