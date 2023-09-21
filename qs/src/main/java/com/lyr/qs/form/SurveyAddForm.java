package com.lyr.qs.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Schema(description = "问卷添加表单")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyAddForm {

    @NotBlank(message = "问卷标题 不能为空")
    @Schema(description = "问卷标题")
    private String title;

    @NotBlank(message = "问卷描述 不能为空")
    @Schema(description = "问卷描述")
    private String description;

    @Valid
    @NotEmpty(message = "问题列表 不能为空")
    @Schema(description = "问题列表")
    private List<QuestionAddForm> questions;

}
