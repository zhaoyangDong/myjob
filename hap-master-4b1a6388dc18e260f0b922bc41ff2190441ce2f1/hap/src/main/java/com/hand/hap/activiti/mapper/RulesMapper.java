package com.hand.hap.activiti.mapper;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.activiti.dto.WflRules;
import com.hand.hap.mybatis.common.Mapper;

/**
 * @author shengyang.zhou@hand-china.com
 */
public interface RulesMapper extends Mapper<WflRules> {

    WflRules selectByExecution(@Param("processKey") String processKey, @Param("nodeId") String nodeId);

    WflRules selectByRuleCode(String ruleCode);

}
