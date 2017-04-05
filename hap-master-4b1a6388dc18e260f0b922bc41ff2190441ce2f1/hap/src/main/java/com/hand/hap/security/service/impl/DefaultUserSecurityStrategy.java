package com.hand.hap.security.service.impl;

import com.hand.hap.account.dto.User;
import com.hand.hap.account.exception.UserException;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.components.SysConfigManager;
import com.hand.hap.mybatis.util.StringUtil;
import com.hand.hap.security.IUserSecurityStrategy;
import com.hand.hap.security.PasswordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by Qixiangyu on 2016/12/22.
 * 用户安全策略默认实现类
 */
@Component
public class DefaultUserSecurityStrategy implements IUserSecurityStrategy {

    String VIEW_UPDATE_PASSWORD = "sys/um/sys_update_password";

    @Autowired
    private PasswordManager passwordManager;

    @Autowired
    SysConfigManager sysConfigManager;

    @Override
    public ModelAndView loginVerifyStrategy(User user, HttpServletRequest request) {

        String reason = null;
        HttpSession session = request.getSession(false);
        // 判断密码是否失效
        if (user.getLastPasswordUpdateDate() != null && passwordManager.getPasswordInvalidTime() > 0
                && daysBetween(user.getLastPasswordUpdateDate(), new Date()) >= passwordManager
                        .getPasswordInvalidTime()) {
            reason = "EXPIRE";
            //需要跳转修改密码页面，不需要输入旧密码请设置
            session.setAttribute(User.PASSWORD_EXPIRE_VERIFY,reason);
        }
        if (user.getFirstLogin() != null && sysConfigManager.getResetPwFlag()) {
            if ("Y".equalsIgnoreCase(user.getFirstLogin())) {
                reason = "RESET";
                //需要跳转修改密码页面，不需要输入旧密码请设置
                session.setAttribute(User.PASSWORD_EXPIRE_VERIFY,reason);
            }
        }

        if (StringUtil.isNotEmpty(reason)) {
            if (!"ADMIN".equalsIgnoreCase(user.getUserName())) {
                ModelAndView mv = new ModelAndView(VIEW_UPDATE_PASSWORD);
                mv.addObject("user", user);
                mv.addObject("length", passwordManager.getPasswordMinLength());
                mv.addObject("complexity", passwordManager.getPasswordComplexity());
                mv.addObject("resetReason", reason);
                return mv;
            }
        }
        return null;
    }

    @Override
    public void passwordVerifyStrategy(IRequest request ,User user, String oldPwd, String newPwd, String newPwdAgain) throws UserException {

        // 不为空则需要强制修改密码 不需要验证旧密码
        Object verifyOldPw = request.getAttribute(User.PASSWORD_EXPIRE_VERIFY);

        // 密码不为空校验
        if (verifyOldPw == null) {
            if ("".equals(oldPwd) || "".equals(newPwd) || "".equals(newPwdAgain)) {
                throw new UserException(UserException.PASSWORD_NOT_EMPTY, null);
            }
        } else {
            // 不验证旧密码
            if ("".equals(newPwd) || "".equals(newPwdAgain)) {
                throw new UserException(UserException.PASSWORD_NOT_EMPTY, null);
            }
        }

        Integer length = passwordManager.getPasswordMinLength();
        String complexity = passwordManager.getPasswordComplexity();
        if (newPwd.length() < length) {
            throw new UserException(UserException.USER_PASSWORD_LENGTH_INSUFFICIENT, null);

        } else {
            if ("no_limit".equals(complexity)) {

            } else if ("digits_and_letters".equals(complexity) && !newPwd.matches(".*[0-9]+.*")
                    && !newPwd.matches(".*[a-zA-Z]+.*")) {
                throw new UserException(UserException.USER_PASSWORD_REQUIREMENT, null);
            } else if ("digits_and_case_letters".equals(complexity) && !newPwd.matches(".*[a-z]+.*")
                    && !newPwd.matches(".*[A-Z]+.*") && !newPwd.matches(".*[0-9]+.*")) {
                throw new UserException(UserException.USER_PASSWORD_REQUIREMENT, null);
            }
        }
        // 两次密码一致性检查
        if (!newPwd.equals(newPwdAgain)) {
            throw new UserException(UserException.USER_PASSWORD_NOT_SAME_TWICE, null);
        }

        String pwd = user.getPasswordEncrypted();
        // 验证旧密码是否正确
        // 密码过期 则不需要验证旧密码
        if (verifyOldPw == null) {
            if (!passwordManager.matches(oldPwd, pwd)) {
                throw new UserException(UserException.USER_PASSWORD_WRONG, null);
            }
        }

        // 验证新密码有效2-与旧密码不一致
        if (passwordManager.matches(newPwd, pwd)) {
            throw new UserException(UserException.USER_PASSWORD_SAME, null);
        }
    }

    public int daysBetween(Date smdate, Date bdate) {
        int between_days = (int) ((bdate.getTime() - smdate.getTime()) / (1000 * 3600 * 24));
        return between_days;
    }

    @Override
    public int getOrder() {
        return 9999;
    }
}
