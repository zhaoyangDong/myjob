/*
 * #{copyright}#
 */
package com.hand.hap.function.dto;

/**
 * 功能显示DTO.
 * 
 * @author njq.niu@hand-china.com
 *
 *         2016年1月27日
 */
public class FunctionDisplay extends Function {

    
    private static final long serialVersionUID = 867166642657571402L;

    private String parentFunctionName;

    private String resourceName;

    public String getParentFunctionName() {
        return parentFunctionName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setParentFunctionName(String parentFunctionName) {
        this.parentFunctionName = parentFunctionName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}
