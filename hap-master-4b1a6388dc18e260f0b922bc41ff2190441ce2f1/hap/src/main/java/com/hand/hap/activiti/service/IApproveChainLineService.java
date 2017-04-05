package com.hand.hap.activiti.service;

import java.util.List;

import com.hand.hap.activiti.dto.ApproveChainLine;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;

public interface IApproveChainLineService extends IBaseService<ApproveChainLine>, ProxySelf<IApproveChainLineService>{
    List<ApproveChainLine> selectByHeaderId(IRequest iRequest ,Long headerId);

    List<ApproveChainLine> selectByNodeId(IRequest iRequest , String key, String nodeId);
}