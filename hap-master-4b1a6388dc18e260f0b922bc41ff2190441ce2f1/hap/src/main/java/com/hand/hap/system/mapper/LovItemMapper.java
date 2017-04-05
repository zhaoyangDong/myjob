/*
 * #{copyright}#
 */
package com.hand.hap.system.mapper;

import java.util.List;

import com.hand.hap.system.dto.LovItem;
import com.hand.hap.mybatis.common.Mapper;

/**
 * 
 * @author njq.niu@hand-china.com
 *
 *         2016年2月1日
 */
public interface LovItemMapper extends Mapper<LovItem> {

    List<LovItem> selectByLovId(Long lovId);

    int deleteByLovId(Long lovId);
}