/*
 * #{copyright}#
 */
package com.hand.hap.mail;

/**
 * 邮箱状态枚举
 * @author Clerifen Li
 */
public enum EmailStatusEnum {
    
    SUCCESS("SUCCESS"),
    ERROR("ERROR");
    
    private String code;
    
    private EmailStatusEnum(String code){
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }
}