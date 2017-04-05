/*
 * #{copyright}#
 */
package com.hand.hap.function.dto;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 功能资源DTO.
 * 
 * @author wuyichu
 */
@Table(name = "sys_function_resource")
public class FunctionResource extends BaseDTO {

    private static final long serialVersionUID = 2205839053452054599L;

    @Id
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long funcSrcId;

    private Long functionId;

    private Long resourceId;

    public Long getFuncSrcId() {
        return funcSrcId;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setFuncSrcId(Long funcSrcId) {
        this.funcSrcId = funcSrcId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
}