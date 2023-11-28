package com.lyr.qs.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

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
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "单选题答题表")
public class AnRadio implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "单选题答题表主键")
    private Integer id;

    @Schema(description = "问卷表的主键")
    private Integer belongSurveyId;

    @Schema(description = "问题表主键")
    private Integer belongQuestionId;

    @Schema(description = "被选中的选项id")
    private Integer optionId;

    @Schema(description = "被选中的选项的排序id，从0开始")
    private Integer orderId;


}
