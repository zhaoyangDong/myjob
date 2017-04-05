package com.hand.hap.activiti.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.hand.hap.account.dto.User;
import com.hand.hap.activiti.service.IActivitiEntityService;
import com.hand.hap.security.IAuthenticationSuccessListener;

/**
 * @author shengyang.zhou@hand-china.com
 */
@Component
public class ActivitiAuthenticationSuccessListener implements IAuthenticationSuccessListener {

    @Autowired
    private IActivitiEntityService activitiService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        String userName = (String) session.getAttribute(User.FIELD_USER_NAME);
        UserEntity employee = activitiService.getEmployee(userName);
        if (employee == null) {
            session.setAttribute("employeeCode", userName);
        } else {
            session.setAttribute("employeeCode", employee.getId());
        }
        System.out.println(employee);
    }
}
