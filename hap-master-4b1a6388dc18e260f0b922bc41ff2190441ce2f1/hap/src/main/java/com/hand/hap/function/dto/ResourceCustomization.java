package com.hand.hap.function.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

/**
 * 资源合并配置表
 * @author zhizheng.yang@hand-china.com
 *
 */

@Table(name = "sys_resource_customization")
@ExtensionAttribute(disable = true)
public class ResourceCustomization extends BaseDTO {

    @Id
    @Column
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long resourceCustomizationId;

    @Column
    private Long resourceId;

    @Column
    private String url;

    @Column
    private Integer sequence;
    
    @Column
    private String description;
    
    @Column
    private String enableFlag;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public Long getResourceCustomizationId() {
        return resourceCustomizationId;
    }

    public void setResourceCustomizationId(Long resourceCustomizationId) {
        this.resourceCustomizationId = resourceCustomizationId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}
