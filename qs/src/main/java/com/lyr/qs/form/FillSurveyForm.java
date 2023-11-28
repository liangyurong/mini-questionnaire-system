package com.lyr.qs.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "问卷填写表单")
@Data
public class FillSurveyForm {

    @Schema(description = "问卷id")
    @NotNull(message = "问卷id不能为空")
    private Integer surveyId;

    @Schema(description = "userId")
    @NotNull(message = "userId 不能为空")
    private Integer userId;

    @Schema(description = "姓名")
    @NotBlank(message = "姓名 不能为空")
    private String name;

    @Schema(description = "手机号")
    @NotBlank(message = "手机号 不能为空")
    private String phone;

    @Schema(description = "问题列表")
    @NotEmpty(message = "问题列表   不能为空")
    private List<FillQuestionForm> questions;
    

}
