/*
 * #{copyright}#
 */
package com.hand.hap.audit.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.audit.dto.Audit;
import com.hand.hap.audit.mapper.AuditMapper;
import com.hand.hap.audit.service.IAuditService;
import com.hand.hap.cache.CacheSet;
import com.hand.hap.core.IRequest;

/**
 * 审计serviceImpl.
 * @author xiawang.liu
 *
 */
@Transactional
@Service
public class AuditServiceImpl implements IAuditService {

    @Autowired
    private AuditMapper auditMapper;

    @Override
    public List<Audit> selectAuditEntityAll(IRequest requestContext) {
        return auditMapper.selectAuditEntityAll();
    }

    @Override
    public boolean saveAuditEntityAll(IRequest requestContext, List<Audit> audits) {
        for (Audit audit : audits) {
            self().updateAudit(audit);
        }
        return true;
    }

    @Override
    @CacheSet(cache = "audit")
    public Audit updateAudit(Audit audit) {
        auditMapper.saveAuditEntity(audit);
        return audit;
    }


}
