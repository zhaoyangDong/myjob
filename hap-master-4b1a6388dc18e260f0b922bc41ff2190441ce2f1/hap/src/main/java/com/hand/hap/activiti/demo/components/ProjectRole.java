package com.hand.hap.activiti.demo.components;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import com.hand.hap.activiti.custom.IActivitiBean;

@Component
public class ProjectRole implements IActivitiBean {

    public String role(String roleCode) {
        System.out.println(roleCode);
        return "jessen";
    }

    public String c(DelegateExecution execution, String roleCode) {
        System.out.println(roleCode);
        return "jessen";
    }

    public String a(DelegateExecution execution, String roleCode) {
        System.out.println(roleCode);
        return "jessen";
    }

    public String e(DelegateExecution execution, String roleCode) {
        System.out.println(roleCode);
        return "jessen";
    }

    public String d(DelegateExecution execution, String roleCode) {
        System.out.println(roleCode);
        return "jessen";
    }


    /**
     * 获取会签的人
     * @param execution
     * @return
     */
    public List<String> getCollection(DelegateExecution execution){
        return Arrays.asList("rodgers","jessen","eric");
    }

}
