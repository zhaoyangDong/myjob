/*
 * #{copyright}#
 */
package com.hand.hap.mail.mapper;

import java.util.List;

import com.hand.hap.mail.dto.MessageEmailWhiteList;
import com.hand.hap.mybatis.common.Mapper;

/**
 * @author Clerifen Li
 */
public interface MessageEmailWhiteListMapper extends Mapper<MessageEmailWhiteList> {

    int deleteByConfigId(Long configId);
    
    List<MessageEmailWhiteList> selectByConfigId(Long configId);

}