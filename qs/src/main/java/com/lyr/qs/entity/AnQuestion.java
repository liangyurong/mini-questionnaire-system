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
@TableName("t_an_question")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "问题答题表")
public class AnQuestion implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "主键")
    private Integer id;

    @Schema(description = "问卷表的主键")
    private Integer surveyId;

    @Schema(description = "问题表主键")
    private Integer questionId;

    @Schema(description = "问题类型")
    private String type;

    @Schema(description = "被选中的选项id列表，用,分隔")
    private String optionIds;

}
