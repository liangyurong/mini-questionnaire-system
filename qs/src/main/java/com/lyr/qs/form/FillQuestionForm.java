package com.lyr.qs.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "问卷填写表单")
@Data
public class FillQuestionForm {

    private Integer questionId;


    private List<FillOptionForm> options;

}
