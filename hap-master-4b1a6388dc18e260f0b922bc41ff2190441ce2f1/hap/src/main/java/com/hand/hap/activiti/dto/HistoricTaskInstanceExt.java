package com.hand.hap.activiti.dto;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntityImpl;
import org.activiti.rest.service.api.history.HistoricTaskInstanceResponse;
import org.springframework.util.ReflectionUtils;

/**
 * @author shengyang.zhou@hand-china.com
 */
public class HistoricTaskInstanceExt extends HistoricTaskInstanceEntityImpl {
    private String comment;

    private String assigneeName;

    public HistoricTaskInstanceExt() {

    }

    public HistoricTaskInstanceExt(HistoricTaskInstance response) {
        ReflectionUtils.doWithFields(response.getClass(), f -> {
            try {
                f.setAccessible(true);
                f.set(this, f.get(response));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
