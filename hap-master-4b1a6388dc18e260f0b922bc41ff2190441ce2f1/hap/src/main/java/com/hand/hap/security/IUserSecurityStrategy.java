package com.hand.hap.security;

import com.hand.hap.account.dto.User;
import com.hand.hap.account.exception.UserException;
import com.hand.hap.core.IRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Qixiangyu on 2016/12/22.
 * 自定义用户安全策略 ，注意order，默认10
 */
public interface IUserSecurityStrategy extends Comparable<IUserSecurityStrategy>{

    /**
     *  在登录成功以后，跳转到index页面前对用户作出一些逻辑判断，可以重定向到其他页面
     *
     * @param user
     * @return 重定向到modelandview，返回空则正常跳转到index
     *
     */
    ModelAndView loginVerifyStrategy(User user, HttpServletRequest request);

    /**
     *  自定义密码验证策略
     *
     * @param user 用户信息
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @param newPwdAgain   再次输入的新密码
     *
     */
    void passwordVerifyStrategy(IRequest request ,User user,String oldPwd, String newPwd, String newPwdAgain) throws UserException;


    /**
     *  创建新用户之前，对用户做一些处理
     *
     *  @return 处理后的user对象,一定要返回一个user，默认返回传入的user
     */
    default User beforeCreateUser(IRequest request ,User user){return user ;}

    default int getOrder() {return 10;}

    @Override
    default int compareTo(IUserSecurityStrategy o) {
        return getOrder() - o.getOrder();
    }

}
