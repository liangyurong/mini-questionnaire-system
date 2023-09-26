package com.lyr.qs.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "问题VO")
public class QuestionVO {

    private Integer id;

    private Integer surveyId;

    private String content;

    private String type;

    private Integer orderNumber;

    private Integer isRequired;

    private List<OptionVO> options;
}
