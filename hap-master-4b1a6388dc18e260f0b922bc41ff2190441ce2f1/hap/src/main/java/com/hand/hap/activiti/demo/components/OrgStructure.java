/*
 * Copyright Hand China Co.,Ltd.
 */

package com.hand.hap.activiti.demo.components;

import org.springframework.stereotype.Component;

import com.hand.hap.activiti.custom.IActivitiBean;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Component
public class OrgStructure implements IActivitiBean {
    public String getDirector(String starter) {
        return "Jessen";
    }

    public String getDeptLeader(String starter) {
        return "Tony";
    }
}
