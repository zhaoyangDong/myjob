/*
 * #{copyright}#
 */
package com.hand.hap.mail;

/**
 * 优先级枚举
 * @author Clerifen Li
 */
public enum PriorityLevelEnum {
    
    VIP("VIP"),
    NORMAL("NORMAL");
    
    private String code;
    
    private PriorityLevelEnum(String code){
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }
}