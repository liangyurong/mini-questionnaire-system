package com.lyr.qs.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "问题添加表单")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAddForm {

    @NotBlank(message = "问题内容 不能为空")
    @Schema(description = "问题内容")
    private String content;

    @NotBlank(message = "问题类型编码 不能为空")
    @Schema(description = "问题类型编码，0-单选题 1-多选题 2-单项填空题")
    private String type;

    @NotNull(message = "是否必答 不能为空")
    @Schema(description = "是否必答 0非必答 1必答")
    private Integer isRequired;

    @Valid
    // @NotEmpty(message = "选项不能为空")
    private List<OptionAddForm> options;

}
