/*
 * #{copyright}#
 */

package com.hand.hap.function.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hand.hap.core.annotation.Children;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.Condition;
import com.hand.hap.system.dto.BaseDTO;

/**
 * 功能的DTO.
 *
 * @author wuyichu
 */
@MultiLanguage
@Table(name = "sys_function_b")
public class Function extends BaseDTO {

    private static final long serialVersionUID = -8645010673309468576L;

    /**
     * 表ID，主键，供其他表做外键.
     */
    @Id
    @Column
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long functionId;

    /**
     * 功能编码.
     */
    @Column
    private String functionCode;

    /**
     * 描述.
     */
    @Column
    @com.hand.hap.core.annotation.MultiLanguageField
    private String functionDescription;
    
    /**
     * 功能图标.
     */
    @Column
    @Condition(exclude = true)
    private String functionIcon;

    /**
     * 排序号
     */
    @Column
    @Condition(exclude = true)
    private Long functionSequence;

    /**
     * 功能名称.
     */
    @Column
    @MultiLanguageField
    private String functionName;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private String lang;

    /**
     * 模块编码.
     */
    @Column
    private String moduleCode;

    @Column
    private Long parentFunctionId;

    /**
     * 资源ID.
     */
    @Column
    private Long resourceId;

    @Children
    @Transient
    private List<Resource> resources;

    /**
     * 功能类型.
     * TODO:配置类型，界面上选择
     */
    @Column
    private String type = "PAGE";

    public String getFunctionCode() {
        return functionCode;
    }

    public String getFunctionDescription() {
        return functionDescription;
    }

    public String getFunctionIcon() {
        return functionIcon;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public String getFunctionName() {
        return functionName;
    }
    
    public String getLang() {
        return lang;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public Long getParentFunctionId() {
        return parentFunctionId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public String getType() {
        return type;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode == null ? null : functionCode.trim();
    }

    public void setFunctionDescription(String functionDescription) {
        this.functionDescription = functionDescription == null ? null : functionDescription.trim();
    }

    public void setFunctionIcon(String functionIcon) {
        this.functionIcon = functionIcon == null ? null : functionIcon.trim();
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName == null ? null : functionName.trim();
    }

    public void setLang(String sourceLang) {
        this.lang = sourceLang;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode == null ? null : moduleCode.trim();
    }

    public void setParentFunctionId(Long parentFunctionId) {
        this.parentFunctionId = parentFunctionId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Long getFunctionSequence() {
        return functionSequence;
    }

    public void setFunctionSequence(Long functionSequence) {
        this.functionSequence = functionSequence;
    }
}