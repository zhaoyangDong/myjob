/*
 * #{copyright}#
 */
package com.hand.hap.mail.mapper;

import java.util.List;

import com.hand.hap.mail.dto.MessageEmailConfig;
import com.hand.hap.mybatis.common.Mapper;


/**
 * @author Clerifen Li
 */
public interface MessageEmailConfigMapper extends Mapper<MessageEmailConfig> {

    List<MessageEmailConfig> selectMessageEmailConfigs(MessageEmailConfig record);

    Integer queryMsgConfigQuanties();
}