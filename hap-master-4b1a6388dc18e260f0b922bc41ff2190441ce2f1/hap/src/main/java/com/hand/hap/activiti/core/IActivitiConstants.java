package com.hand.hap.activiti.core;

/**
 * @author shengyang.zhou@hand-china.com
 */
public interface IActivitiConstants {
    String PROP_COMMENT = "comment";
    String COMMENT_ACTION = "action";
    String COMMENT_COMPLETE_BY = "complete_by";
    String COMMENT_DELEGATE_BY = "delegate_by";

    String PROP_APPROVE_RESULT = "approveResult";

    String APPROVED = "APPROVED";
    String REJECTED = "REJECTED";

    String ACTION_JUMP = "jump";

    String NUMBER_OF_INSTANCES = "nrOfInstances";
    String NUMBER_OF_ACTIVE_INSTANCES = "nrOfActiveInstances";
    String NUMBER_OF_COMPLETED_INSTANCES = "nrOfCompletedInstances";

    String NUMBER_OF_APPROVED = "nrOfApproved";
    String NUMBER_OF_REJECTED = "nrOfRejected";

    String HISTORY_SUFFIX = "__his__";

    default boolean isApproved(String v) {
        return APPROVED.equalsIgnoreCase(v);
    }

    default boolean isRejected(String v) {
        return REJECTED.equalsIgnoreCase(v);
    }
}
