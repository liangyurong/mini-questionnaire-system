package com.lyr.qs.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * 单项填空题答题表
 *
 * @author yurong333
 * @since 2022-12-30
 */
@Schema(description = "单项填空题答题表")
@TableName("t_an_text")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnText implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "单项填空题答案id")
    private Integer id;

    @Schema(description = "问卷id")
    private Integer surveyId;

    @Schema(description = "问题id")
    private Integer questionId;

    @Schema(description = "答案")
    private String myText;


}
