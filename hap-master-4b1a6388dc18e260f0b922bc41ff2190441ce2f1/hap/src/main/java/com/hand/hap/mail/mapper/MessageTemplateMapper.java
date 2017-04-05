/*
 * #{copyright}#
 */

package com.hand.hap.mail.mapper;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mail.dto.MessageTemplate;
import com.hand.hap.mybatis.common.Mapper;

public interface MessageTemplateMapper extends Mapper<MessageTemplate> {
    MessageTemplate selectByCode(String templateCode);

    MessageTemplate getMsgTemByCode(@Param("templateId") Long templateId, @Param("templateCode") String templateCode);
}