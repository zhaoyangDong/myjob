/*
 * #{copyright}#
 */

package com.hand.hap.system.mapper;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hap.system.dto.CodeValue;
import org.apache.ibatis.annotations.Param;

/**
 * @author shengyang.zhou@hand-china.com
 */
public interface CodeValueMapper extends Mapper<CodeValue> {

    int deleteByCodeId(CodeValue key);

    int deleteTlByCodeId(CodeValue key);

    List<CodeValue> selectCodeValuesByCodeName(String codeName);

    List<CodeValue> queryMsgTemCodeLov(@Param("value") String value, @Param("meaning") String meaning);
    
    List<CodeValue> queryEmlAccountCodeLov(@Param("value") String value, @Param("meaning") String meaning);
}