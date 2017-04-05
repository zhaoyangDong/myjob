/*
 * #{copyright}#
 */
package com.hand.hap.account.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hand.hap.security.IUserSecurityStrategy;
import com.hand.hap.security.service.impl.UserSecurityStrategyManager;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.hand.hap.account.constants.UserConstants;
import com.hand.hap.account.dto.User;
import com.hand.hap.account.exception.UserException;
import com.hand.hap.account.mapper.UserMapper;
import com.hand.hap.account.service.IUserInfoService;
import com.hand.hap.account.service.IUserService;
import com.hand.hap.core.IRequest;
import com.hand.hap.mail.ReceiverTypeEnum;
import com.hand.hap.mail.dto.MessageReceiver;
import com.hand.hap.mail.service.IMessageService;
import com.hand.hap.security.PasswordManager;
import com.hand.hap.system.service.IProfileService;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户信息服务接口实现.
 * 
 * @author Zhaoqi
 *
 */
@Service
@Transactional
public class UserInfoServiceImpl implements IUserInfoService {

    private final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    // 跳转
    protected static final String REDIRECT = "redirect:";

    private static final Long BASE_MENBER_EIGHT = 8L;

    @Autowired
    private PasswordManager passwordManager;
    @Autowired
    private IUserService userService;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IProfileService profileService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    UserSecurityStrategyManager userSecurityStrategyManager;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public User create(IRequest request, User user) throws Exception {
        // String tmpPassword = "";
        /*
         * if (logger.isDebugEnabled()) {
         * logger.debug("此处应调用随机程序生成随机口令,暂时口令设置为-11111111"); }
         */
        // 调用程序生成随机口令
        String tmpPassword = generateRandomPassword();

        // 账号临时密码失效时间
        String expiryHourStr = profileService.getProfileValue(request, "SYS.TEMP_PWD_EXPIRY_DATE");
        int expiryHour;
        if (expiryHourStr == null) {
            expiryHour = 1;
        } else {
            expiryHour = Integer.parseInt(expiryHourStr);
        }
        Date expiryDate = DateUtils.addHours(new Date(), expiryHour);
        // tmpPassword = UserConstants.TMP_PASSWORD;
        user.setPassword(tmpPassword);
        user = userService.insertSelective(request, user);
        if (user == null) {
            throw new UserException(UserException.USER_INSERT_FAIL, null);
        }
        // 成功创建USER之后
        if (logger.isDebugEnabled()) {
            logger.debug("成功调用HAP创建USER {}", user.toString());
        }
        // 调用接口发送短信通知用户
        List<MessageReceiver> receiverlist = new ArrayList<MessageReceiver>();
        MessageReceiver receiver = new MessageReceiver();
        Map<String, Object> data = new HashMap<String, Object>();
        receiver.setMessageAddress(user.getEmail());
        receiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
        receiver.setReceiverId(user.getUserId());
        receiverlist.add(receiver);
        // 设置邮件模板里的数据
        data.put("tmpPassword", tmpPassword);
        data.put("limit", UserConstants.DEFAULT_PAGE); // 临时口令有效期为1个小时
        data.put("userName", user.getUserName());
        messageService.sendEmailMessage(-1L, null, UserConstants.TEMP_PWD_MESSAGE_TEMPLATE, "PASSWORD", data,
                receiverlist, null);
        // messageService.addMessage(null,
        // UserConstants.TEMP_PWD_MESSAGE_TEMPLATE, data, null, receiverlist);

        return user;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public User update(IRequest request, User user) throws Exception {
        // 成功调用HAP创建USER之后
        user = userService.updateByPrimaryKeySelective(request, user);
        if (user == null) {
            throw new UserException(UserException.USER_UPDATE_FAIL, null);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("成功update USER {}", user.getUserId());
        }
        return user;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public List<User> getUsers(IRequest request, User user, int page, int pagesize) throws UserException {
        PageHelper.startPage(page, pagesize);
        return userMapper.select(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public boolean validatePassword(IRequest request, String oldPwd, String newPwd, String newPwdAgain, Long userId)
            throws UserException {
        // 获取指定用户密码
        User tmp = new User();
        tmp.setUserId(userId);

        User userInDB = userService.selectByPrimaryKey(request, tmp);
        // 执行验证策略
        List<IUserSecurityStrategy> userSecurityStrategies =  userSecurityStrategyManager.getUserAuthenticationList();
        for(IUserSecurityStrategy userSecurityStrategy : userSecurityStrategies) {
           userSecurityStrategy.passwordVerifyStrategy(request, userInDB, oldPwd, newPwd, newPwdAgain);
        }
        // 验证新密码有效1-格式
        // String regPwdOne
        // ="^(?![^a-zA-Z]+$)(?!\\D+$)[a-zA-Z0-9._`~!@#$%^&*()+-={}:;<>?,\\\\\"\'\\[\\]]{8,}$";
        /*
         * if (!newPwd.matches(UserConstants.PASSWORD_FORMAT)) { throw new
         * UserException(UserException.USER_PASSWORD_REQUIREMENT, null); }
         */
        return true;

    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public boolean validateAccountPassword(IRequest request, String newPwd, String newPwdAgain, Long userId)
            throws UserException {
        // 两次密码一致性检查
        if (!newPwd.equals(newPwdAgain)) {
            throw new UserException(UserException.USER_PASSWORD_NOT_SAME_TWICE, null);
        }
        // 获取指定用户密码
        User tmp = new User();
        tmp.setUserId(userId);
        User account = userService.selectByPrimaryKey(request, tmp);
        String pwd = account.getPasswordEncrypted();
        // 验证新密码有效1-格式
        // String regex =
        // "^[a-zA-Z0-9._`~!@#$%^&*()+-={}:;<>?,\\\\\"\'\\[\\]]{8,}$"; //
        // 此处最短应为8
        if (!newPwd.matches(UserConstants.PASSWORD_FORMAT)) {
            throw new UserException(UserException.USER_PASSWORD_REQUIREMENT, null);
        }
        // 验证新密码有效2-与旧密码不一致
        if (passwordManager.matches(newPwd, pwd)) {
            throw new UserException(UserException.USER_PASSWORD_SAME, null);
        }
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public User selectUserByPrimaryKey(IRequest request, Long userId) throws UserException {
        User user = new User();
        user.setUserId(userId);
        List<User> checkUsers = userService.select(request, user, UserConstants.DEFAULT_PAGE,
                UserConstants.DEFAULT_PAGE_SIZE);
        if (checkUsers.isEmpty()) {
            throw new UserException(UserException.USER_NOT_EXIST, null);
        }
        User checkUser = checkUsers.get(0);
        if (!UserConstants.USER_STATUS_ACTIVE.equals(checkUser.getStatus())) {
            throw new UserException(UserException.USER_EXPIRED, null);
        }
        return checkUser;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public User selectUserByName(IRequest request, String userName) throws UserException {
        User user = new User();
        user.setUserName(userName);
        User checkUser = userMapper.selectByUserName(userName);
        if (checkUser == null) {
            throw new UserException(UserException.USER_NOT_EXIST, null);
        }
        if (!UserConstants.USER_STATUS_ACTIVE.equals(checkUser.getStatus())) {
            throw new UserException(UserException.USER_EXPIRED, null);
        }
        return checkUser;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<User> isExistsUser(IRequest request, User user) throws UserException {
        List<User> list = validateUser(request, user);
        if (!list.isEmpty()) {
            throw new UserException(UserException.USER_EXIST, new Object[] {});
        }
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<User> isExistsPhone(IRequest request, User user) throws UserException {
        List<User> list = validatePhone(request, user);
        if (!list.isEmpty()) {
            throw new UserException(UserException.PHONE_EXIST, new Object[] {});
        }
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<User> isExistsEmail(IRequest request, User user) throws UserException {
        List<User> list = validateEmail(request, user);
        if (!list.isEmpty()) {
            throw new UserException(UserException.EMAIL_EXIST, new Object[] {});
        }
        return list;
    }

    /**
     * 用户名校验通用方法.
     * 
     * @param request
     *            统一上下文
     * @param user
     *            包含用户名信息
     * @throws UserException
     *             抛出验证用户非空的业务异常
     */
    private List<User> validateUser(IRequest request, User user) throws UserException {
        // 非空验证
        // if (user.getUserName() == null || "".equals(user.getUserName())) {
        // throw new UserException(UserException.USER_NOT_ISEMPTY, new Object[]
        // {});
        // }
        // 格式验证
        if (!user.getUserName().matches(UserConstants.UESR_NAME_REGEX)) {
            throw new UserException(UserException.USER_FORMAT, new Object[] {});
        }
        User checkUser = new User();
        checkUser.setUserName(user.getUserName());
        List<User> list = userMapper.select(checkUser);
        return list;
    }

    /**
     * 邮箱校验通用方法.
     * 
     * @param request
     *            统一上下文
     * @param user
     *            查询依据-email
     * @throws UserException
     *             抛出邮箱格式错误的业务异常
     */
    private List<User> validateEmail(IRequest request, User user) throws UserException {
        // 非空验证
        // if (user.getEmail() == null || "".equals(user.getEmail())) {
        // throw new UserException(UserException.EMAIL_NOT_ISEMPTY, new Object[]
        // {});
        // }
        // 格式验证
        if (!user.getEmail().matches(UserConstants.EMAIL_REGEX)) {
            throw new UserException(UserException.EMAIL_FORMAT, new Object[] {});
        }
        List<User> list = new ArrayList<User>();
        if (UserConstants.USER_TYPE_INNER.equals(user.getUserType())) {
            User checkUser = new User();
            checkUser.setEmail(user.getEmail());
            list = userMapper.select(checkUser);
        }
        return list;
    }

    /**
     * 手机号码校验通用方法.
     * 
     * @param request
     *            统一上下文
     * @param user
     *            查询依据-phone
     * @throws UserException
     *             抛出手机未通过验证的业务异常
     */
    private List<User> validatePhone(IRequest request, User user) throws UserException {
        // 非空验证
        // if (user.getPhoneNo() == null || "".equals(user.getPhoneNo())) {
        // throw new UserException(UserException.PHONE_NOT_ISEMPTY, new Object[]
        // {});
        // }
        // 格式验证
        if (!user.getPhone().matches(UserConstants.PHONE_REGEX)) {
            throw new UserException(UserException.PHONE_FORMAT, new Object[] {});
        }
        List<User> list = new ArrayList<User>();
        if (UserConstants.USER_TYPE_INNER.equals(user.getUserType())) {
            User checkUser = new User();
            checkUser.setPhone(user.getPhone());
            list = userMapper.select(checkUser);
        }
        return list;
    }

    /*
     * 找回用户-生成八位随机密码.
     */
    private String generateRandomPassword() {
        // 验证码
        String password = "";
        for (int i = 0; i < BASE_MENBER_EIGHT; i++) {
            password = password + (int) (Math.random() * 9);
        }
        return password;
    }
}
