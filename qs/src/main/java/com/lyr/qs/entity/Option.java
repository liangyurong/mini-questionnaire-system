package com.lyr.qs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 选项表
 *
 * @author yurong333
 * @since 2022-12-30
 */
@TableName("t_option")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class Option implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 选项id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 选项所属的问题id，对应题目表的主键
     */
    private Integer BelongQuestionId;

    /**
     * 选项描述
     */
    private String myOption;

    /**
     * 选项序号，从0开始
     */
    private Integer orderId;


}
