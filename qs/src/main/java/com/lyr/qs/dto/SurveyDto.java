package com.lyr.qs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyDto {

    private Integer current;
    private Integer size;
    private String title;
    private String description;
    private Integer surveyState;

}
