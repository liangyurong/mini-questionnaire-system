package com.lyr.qs.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "选项更新表单")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionUpdateForm {

    // 新增的选项，id是空的
    // 需要更新的选项才有id
    @Schema(description = "选项id")
    private Integer id;

    @NotBlank(message = "选项内容 不能为空")
    @Schema(description = "选项内容")
    private String content;

    @NotNull(message = "选项序号 不能为空")
    @Schema(description = "选项序号，从0开始")
    private Integer orderNumber;


}
