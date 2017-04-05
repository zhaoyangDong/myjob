/*
 * #{copyright}#
 */
package com.hand.hap.account.service;

import com.hand.hap.account.dto.User;
import com.hand.hap.account.exception.UserException;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;

import java.util.List;


/**
 * 用户信息服务接口.
 * 
 * @author Zhaoqi
 *
 */
public interface IUserInfoService extends ProxySelf<IUserInfoService> {

    /**
     * 创建新用户.
     * 
     * @param request
     *            统一上下文
     * @param ipointUser
     *            用户信息
     * @return 响应信息
     * @throws UserException
     *             抛出创建用户失败异常
     * @throws Exception
     *             抛出短信接口异常
     */
    User create(IRequest request, @StdWho User ipointUser) throws UserException, Exception;

    /**
     * 更新用户信息.
     * 
     * @param request
     *            统一上下文
     * @param ipointUser
     *            用户信息
     * @return 响应信息
     * @throws UserException
     *             抛出更新用户失败异常
     * @throws Exception
     *             抛出短信接口异常
     */
    User update(IRequest request, @StdWho User ipointUser) throws UserException, Exception;

    /**
     * 查询用户信息.
     * 
     * @param request
     *            统一上下文
     * @param ipointUser
     *            包含传入的用户查找依据
     * @param page
     *            分页信息
     * @param pagesize
     *            分页信息
     * @return 返回查询到的用户
     * @throws UserException
     *             抛出未找到用户的业务异常
     */
    List<User> getUsers(IRequest request, User ipointUser, int page, int pagesize)
            throws UserException;

    /**
     * 用户密码修改前校验参数是否有效.
     * 
     * @param request
     *            统一上下文
     * @param oldPwd
     *            原密码
     * @param newPwd
     *            新密码
     * @param newPwdAgain
     *            新密码再次输入
     * @param accountId
     *            用户账户ID
     * @return 返回是否通过校验
     * @throws UserException
     *             抛出验证密码失败的业务异常
     */
    boolean validatePassword(IRequest request, String oldPwd, String newPwd, String newPwdAgain, Long accountId)
            throws UserException;

    /**
     * 用户密码修改前校验参数是否有效.
     * 
     * @param request
     *            统一上下文
     * @param newPwd
     *            新密码
     * @param newPwdAgain
     *            新密码再次输入
     * @param accountId
     *            用户账户ID
     * @return 返回是否通过校验
     * @throws UserException
     *             抛出验证密码失败的业务异常
     */
    boolean validateAccountPassword(IRequest request, String newPwd, String newPwdAgain, Long accountId)
            throws UserException;

    /**
     * 忘记密码,接收用户ID查询用户详细信息.
     * 
     * @param request
     *            统一上下文
     * @param userId
     *            用户ID
     * @return 返回查询到的用户信息
     * @throws UserException
     *             抛出未找到用户的业务异常
     */
    User selectUserByPrimaryKey(IRequest request, Long userId) throws UserException;

    /**
     * 忘记密码,接收用户登录名查询用户详细信息.
     * 
     * @param request
     *            统一上下文
     * @param loginName
     *            用户登录名
     * @return 返回查询到的用户信息
     * @throws UserException
     *             抛出未找到用户的业务异常
     */
    User selectUserByName(IRequest request, String loginName) throws UserException;
    
    List<User> isExistsUser(IRequest request, User ipointUser) throws UserException;
    
    List<User> isExistsPhone(IRequest request, User ipointUser) throws UserException;
    
    List<User> isExistsEmail(IRequest request, User ipointUser) throws UserException;
    
}
