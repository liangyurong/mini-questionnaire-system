package com.lyr.qs.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Schema(description = "问卷VO")
public class SurveyVO {

    private Integer id;

    private String title;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    private Integer surveyState;

    private Integer surveyQuestionNum;

    private Integer answerNum;

    private Integer visibility;

    private List<QuestionVO> questions;

}
