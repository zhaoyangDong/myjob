/*
 * #{copyright}#
 */
package com.hand.hap.system.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hand.hap.account.dto.Role;
import com.hand.hap.account.dto.User;
import com.hand.hap.function.dto.Resource;
import com.hand.hap.system.service.IAccessService;
import com.hand.hap.function.service.IRoleResourceItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hand.hap.function.dto.ResourceItem;
import com.hand.hap.function.mapper.RoleFunctionMapper;
import com.hand.hap.function.service.IResourceItemService;
import com.hand.hap.function.service.IResourceService;

/**
 * @author njq.niu@hand-china.com
 *
 *         2016年3月7日
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AccessServiceImpl implements IAccessService {

    private static final String ACTION_MAINTAIN = "MAINTAIN";

    private Logger logger = LoggerFactory.getLogger(AccessServiceImpl.class);

    private HttpServletRequest request;

    @Autowired
    private IResourceService resourceService;

    @Autowired
    private IResourceItemService resourceItemService;

    @Autowired
    private IRoleResourceItemService roleResourceItemService;
    
    @Autowired
    private RoleFunctionMapper roleFunctionMapper;

    @Override
    public boolean accessMaintain() {
        return self().access(ACTION_MAINTAIN);
    }

    @Override
    public boolean access(String accessCode) {
        if (logger.isDebugEnabled()) {
            logger.debug(new StringBuilder().append("userId: ").append(getUserId()).append(" accessCode: ")
                    .append(accessCode).toString());
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length() + 1);
        Resource resource = resourceService.selectResourceByUrl(url);
        if (resource != null) {
            ResourceItem ri = new ResourceItem();
            ri.setOwnerResourceId(resource.getResourceId());
            ri.setItemId(accessCode);
            ResourceItem resourceItem = resourceItemService.selectResourceItemByResourceIdAndItemId(ri);
            if (resourceItem != null) {
                return roleResourceItemService.hasResourceItem(getRoleId(), resourceItem.getResourceItemId());
            }
        }

        return false;
    }

    @Override
    public boolean accessFunction(String functionCode) {
        Long roleId = getRoleId();
        int result = roleFunctionMapper.selectCountByFunctionCode(roleId, functionCode);
        return result > 0;
    }

    private Long getUserId() {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (Long) session.getAttribute(User.FIELD_USER_ID);
        }
        return null;
    }

    private Long getRoleId() {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (Long) session.getAttribute(Role.FIELD_ROLE_ID);
        }
        return null;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

}
