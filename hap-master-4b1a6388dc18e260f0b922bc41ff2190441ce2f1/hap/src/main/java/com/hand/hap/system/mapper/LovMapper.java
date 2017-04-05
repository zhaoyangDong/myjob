/*
 * #{copyright}#
 */
package com.hand.hap.system.mapper;

import com.hand.hap.system.dto.Lov;
import com.hand.hap.mybatis.common.Mapper;

/**
 * 
 * @author njq.niu@hand-china.com
 *
 * 2016年2月1日
 */
public interface LovMapper extends Mapper<Lov> {

    Lov selectByCode(String code);

}