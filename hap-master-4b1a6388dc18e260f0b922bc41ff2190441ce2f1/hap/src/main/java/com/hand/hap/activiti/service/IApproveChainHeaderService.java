package com.hand.hap.activiti.service;

import java.util.List;

import com.hand.hap.activiti.dto.ApproveChainHeader;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;

public interface IApproveChainHeaderService extends IBaseService<ApproveChainHeader>, ProxySelf<IApproveChainHeaderService>{

    ApproveChainHeader selectByUserTask(String ProcessDefinitionId,String userTaskId);

    List<ApproveChainHeader> updateHeadLine(IRequest requestCtx, List<ApproveChainHeader> dto);
}