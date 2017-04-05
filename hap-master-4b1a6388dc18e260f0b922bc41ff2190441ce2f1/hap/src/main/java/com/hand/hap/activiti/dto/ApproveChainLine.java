package com.hand.hap.activiti.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

@ExtensionAttribute(disable = true)
@Table(name = "wfl_approve_chain_line")
public class ApproveChainLine extends BaseDTO {
    @Id
    @GeneratedValue
    private Long approveChainLineId;

    private Long approveChainId;

    @NotEmpty
    private String name;

    private String description;

    private String approveType;

    private String assignee;

    private String assignGroup;

    @Transient
    private String assignGroupName;

    private String formKey;

    @OrderBy
    private String sequence;

    private String skipExpression;

    private String breakOnSkip;

    private String enableFlag;

    public void setApproveChainLineId(Long approveChainLineId) {
        this.approveChainLineId = approveChainLineId;
    }

    public Long getApproveChainLineId() {
        return approveChainLineId;
    }

    public void setApproveChainId(Long approveChainId) {
        this.approveChainId = approveChainId;
    }

    public Long getApproveChainId() {
        return approveChainId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setApproveType(String approveType) {
        this.approveType = approveType;
    }

    public String getApproveType() {
        return approveType;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getAssignee() {
        return assignee;
    }

    public String getAssignGroup() {
        return assignGroup;
    }

    public void setAssignGroup(String assignGroup) {
        this.assignGroup = assignGroup;
    }

    public String getAssignGroupName() {
        return assignGroupName;
    }

    public void setAssignGroupName(String assignGroupName) {
        this.assignGroupName = assignGroupName;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSkipExpression(String skipExpression) {
        this.skipExpression = skipExpression;
    }

    public String getSkipExpression() {
        return skipExpression;
    }

    public void setBreakOnSkip(String breakOnSkip) {
        this.breakOnSkip = breakOnSkip;
    }

    public String getBreakOnSkip() {
        return breakOnSkip;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

}
