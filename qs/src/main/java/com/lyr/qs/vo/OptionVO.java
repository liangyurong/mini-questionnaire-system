package com.lyr.qs.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "选项VO")
public class OptionVO {

    private Integer id;

    private Integer questionId;

    private String content;

    private Integer orderNumber;

}
