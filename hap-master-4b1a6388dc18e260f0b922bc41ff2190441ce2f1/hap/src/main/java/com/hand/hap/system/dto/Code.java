/*
 * #{copyright}#
 */
package com.hand.hap.system.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.core.annotation.Children;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.mybatis.annotation.Condition;

/**
 * CodeDTO.
 *
 * @author runbai.chen
 */
@MultiLanguage
@Table(name = "sys_code_b")
public class Code extends BaseDTO {
    
    private static final long serialVersionUID = 2776430709705510697L;

    /**
     * 快码类型.
     */
    @Condition(operator = LIKE)
    @Column
    @NotNull
    private String code;

    /**
     * 表ID，主键，供其他表做外键.
     */
    @Id
    @Column
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long codeId;

    @Children
    @Transient
    private List<CodeValue> codeValues;

    /**
     * 快码描述.
     */
    @Column
    @com.hand.hap.core.annotation.MultiLanguageField
    @NotEmpty
    @Condition(operator = LIKE)
    private String description;

    public String getCode() {
        return code;
    }

    public Long getCodeId() {
        return codeId;
    }

    public List<CodeValue> getCodeValues() {
        return codeValues;
    }

    public String getDescription() {
        return description;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public void setCodeId(Long codeId) {
        this.codeId = codeId;
    }

    public void setCodeValues(List<CodeValue> codeValues) {
        this.codeValues = codeValues;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}