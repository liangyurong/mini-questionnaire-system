package com.lyr.qs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * 选项表
 *
 * @author yurong333
 * @since 2022-12-30
 */
@Schema(description = "选项表")
@TableName("t_option")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Option implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "选项表主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "选项所属的问题id，对应题目表的主键")
    private Integer questionId;

    @Schema(description = "选项描述")
    private String content;

    @Schema(description = "选项序号，从0开始")
    private Integer orderNumber;


}
