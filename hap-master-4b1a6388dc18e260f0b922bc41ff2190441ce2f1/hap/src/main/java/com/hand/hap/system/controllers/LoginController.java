/*
 * #{copyright}#
 */
package com.hand.hap.system.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import com.hand.hap.intergration.annotation.HapInbound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.account.dto.Role;
import com.hand.hap.account.dto.User;
import com.hand.hap.account.exception.RoleException;
import com.hand.hap.adaptor.ILoginAdaptor;
import com.hand.hap.adaptor.impl.DefaultLoginAdaptor;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.system.dto.ResponseData;

/**
 * 用户控制层.
 * 
 * @author wuyichu
 * @author njq.niu@hand-china.com
 */
@Controller
public class LoginController extends BaseController implements InitializingBean {

    @Autowired(required = false)
    private ILoginAdaptor loginAdaptor;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 显示登陆界面.
     *
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return view
     */
    @RequestMapping(value = { "/login.html", "/login" })
    @Timed
    public ModelAndView loginView(final HttpServletRequest request, final HttpServletResponse response) {
        return getLoginAdaptor().loginView(request, response);
    }

    /**
     * 显示角色选择界面.
     * 
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return view viewModel
     * @throws BaseException
     *             BaseException
     */
    @RequestMapping(value = { "/role.html", "/role" }, method = RequestMethod.GET)
    public ModelAndView roleView(final HttpServletRequest request, final HttpServletResponse response)
            throws BaseException {
        return getLoginAdaptor().roleView(request, response);
    }

    /**
     * 显示主界面.
     * 
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return view
     */
    @RequestMapping(value = { "/", "/index.html" }, method = RequestMethod.GET)
    public ModelAndView indexView(final HttpServletRequest request, final HttpServletResponse response) {
        return getLoginAdaptor().indexView(request, response);
    }

    private ILoginAdaptor getLoginAdaptor() {
        return loginAdaptor;
    }

    /**
     * 角色选择逻辑.
     * 
     * @param role
     *            角色对象
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return view
     * @throws RoleException
     */
    @RequestMapping(value = "/role", method = RequestMethod.POST)
    public ModelAndView selectRole(final Role role, final HttpServletRequest request,
            final HttpServletResponse response) throws RoleException {
        return getLoginAdaptor().doSelectRole(role, request, response);
    }

    /**
     * 超时重新登陆.
     * 
     * @param account
     * @param request
     * @param response
     * @return ResponseData
     * @throws BaseException
     */
    @RequestMapping(value = "/sessionExpiredLogin", method = RequestMethod.POST)
    public ResponseData sessionExpiredLogin(final User account, final HttpServletRequest request,
            final HttpServletResponse response) throws BaseException {
        return getLoginAdaptor().sessionExpiredLogin(account, request, response);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (loginAdaptor == null) {
            loginAdaptor = new DefaultLoginAdaptor();
            applicationContext.getAutowireCapableBeanFactory().autowireBean(loginAdaptor);
        }
    }
}
