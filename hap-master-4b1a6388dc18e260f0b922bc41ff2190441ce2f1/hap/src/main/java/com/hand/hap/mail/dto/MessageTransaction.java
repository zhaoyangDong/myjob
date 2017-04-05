/*
 * #{copyright}#
 */
package com.hand.hap.mail.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

/**
 * 
 * @author njq.niu@hand-china.com
 *
 * 2016年3月2日
 */
@Table(name = "SYS_MESSAGE_TRANSACTION")
public class MessageTransaction extends BaseDTO {
    
    private static final long serialVersionUID = 6726130570559853932L;

    @Id
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long transactionId;

    private Long messageId;

    private String transactionStatus;

    private String transactionMessage;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getTransactionMessage() {
        return transactionMessage;
    }

    public void setTransactionMessage(String transactionMessage) {
        this.transactionMessage = transactionMessage == null ? null : transactionMessage.trim();
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
    
}