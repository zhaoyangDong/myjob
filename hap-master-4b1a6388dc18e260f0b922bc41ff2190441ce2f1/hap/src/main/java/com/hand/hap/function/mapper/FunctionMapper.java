/*
 * #{copyright}#
 */

package com.hand.hap.function.mapper;

import java.util.List;
import java.util.Map;

import com.hand.hap.function.dto.Function;
import com.hand.hap.function.dto.FunctionDisplay;
import com.hand.hap.function.dto.Resource;
import com.hand.hap.mybatis.common.Mapper;

/**
 * @author wuyichu
 */
public interface FunctionMapper extends Mapper<Function> {

    List<Resource> selectExistsResourcesByFunction(Map<String, Object> params);

    List<Resource> selectNotExistsResourcesByFunction(Map<String, Object> params);

    List<FunctionDisplay> selectFunctions(Function function);
}