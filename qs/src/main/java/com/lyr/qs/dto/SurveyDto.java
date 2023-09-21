package com.lyr.qs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SurveyDto {

    private Integer current;
    private Integer size;
    private String title;
    private String description;
    private Integer surveyState;

    // 设置默认的参数
    SurveyDto(){
        current = 0;
        size = 10;
    }

}
