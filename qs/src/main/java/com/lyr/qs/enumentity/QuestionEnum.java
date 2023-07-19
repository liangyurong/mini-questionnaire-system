package com.lyr.qs.enumentity;

import java.util.Arrays;

/**
 * 问题类型枚举类。此类在生成问卷的时候用到。
 *
 * 目前只使用到单选题，多选题，单项填空题
 */
public enum QuestionEnum {

    RADIO(0,"单选题"),
    CHECKBOX(1,"多选题"),
    SINGLE_FILL(2,"单项填空题"),
    ;

    private Integer code;
    private String type;

    QuestionEnum(Integer code, String type){
        this.code = code;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public Integer getCode() {
        return code;
    }

    /**
     * 通过code获取问题类型enum
     */
    public static QuestionEnum getEnumByCode(Integer code) {
        if(code == null){
            return null;
        }
        return Arrays.stream(QuestionEnum.values())
                .filter(v -> code.equals(v.getCode()))
                .findFirst()
                .orElse(null);
    }

    /**
     * 通过type获得code
     */
    public static Integer getCodeByType(String type){
        if (type == null) {
            return null;
        }
        for (QuestionEnum en : QuestionEnum.values()) {
            if (type.equals(en.getType())) {
                return en.getCode();
            }
        }
        return null;
    }

    /**
     * 通过code获得type
     */
    public static String getTypeByCode(Integer code){
        if (code == null) {
            return null;
        }
        for (QuestionEnum en : QuestionEnum.values()) {
            if (code.equals(en.getCode())) {
                return en.getType();
            }
        }
        return null;
    }

}