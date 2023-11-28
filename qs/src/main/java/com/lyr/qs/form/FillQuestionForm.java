package com.lyr.qs.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "问题填写表单")
@Data
public class FillQuestionForm {

    @Schema(description = "问题表主键")
    @NotNull(message = "问题id 不能为空")
    private Integer questionId;

    @Schema(description = "问题类型")
    @NotBlank(message = "问题类型 不能为空")
    private String type;

    @Schema(description = "被选中的选项id列表")
    private String optionIds;

    @Schema(description = "文本内容")
    private String myText;

}
