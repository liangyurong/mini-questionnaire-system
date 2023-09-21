package com.lyr.qs.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "选项添加表单")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionAddForm {

    @NotBlank(message = "选项内容 不能为空")
    @Schema(description = "选项内容")
    private String content;

    @NotNull(message = "选项序号 不能为空")
    @Schema(description = "选项序号，从0开始")
    private Integer orderNumber;
}
