package com.lyr.qs.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/**
 * 单项填空题答题表
 *
 * @author yurong333
 * @since 2022-12-30
 */
@TableName("t_an_text")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnText implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 单项填空题答案id
     */
    private Integer id;

    /**
     * 问卷id
     */
    private Integer belongSurveyId;

    /**
     * 问题id
     */
    private Integer belongQuestionId;

    /**
     * 答案
     */
    private String myText;


}
