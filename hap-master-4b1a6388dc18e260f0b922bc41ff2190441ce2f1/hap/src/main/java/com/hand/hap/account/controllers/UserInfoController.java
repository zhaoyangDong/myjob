/*
 * #{copyright}#
 */
package com.hand.hap.account.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.mail.Session;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.hand.hap.account.constants.UserConstants;
import com.hand.hap.account.dto.SendRetrieve;
import com.hand.hap.account.dto.User;
import com.hand.hap.account.exception.UserException;
import com.hand.hap.account.service.ISendRetrieveService;
import com.hand.hap.account.service.IUserInfoService;
import com.hand.hap.account.service.IUserService;
import com.hand.hap.core.IRequest;
import com.hand.hap.mail.ReceiverTypeEnum;
import com.hand.hap.mail.dto.MessageReceiver;
import com.hand.hap.mail.service.IMessageService;
import com.hand.hap.security.PasswordManager;
import com.hand.hap.security.captcha.ICaptchaManager;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.Language;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.ISysConfigService;

/**
 * 用户管理controller.
 * 
 * @author Zhaoqi
 *
 */
@Controller
public class UserInfoController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICaptchaManager captchaManager;
    @Autowired
    private ISendRetrieveService sendRetrieveService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private IMessageService messageService;

    @Autowired
    private PasswordManager passwordManager;

    /**
     * 更新个人信息.
     * 
     * @param request
     *            统一上下文
     * @param user
     *            包含需要更新的字段信息
     * @return 响应信息
     * @throws UserException
     *             抛出更新用户信息失败的异常
     * @throws Exception
     *             抛出短信接口异常
     */
    @RequestMapping(value = "/sys/um/updateUserInfo")
    public ResponseData updateUserInfo(HttpServletRequest request, @RequestBody User user) throws Exception {
        IRequest iRequest = createRequestContext(request);
       /// user.setUserId(iRequest.getUserId());

        // 匹配电话格式.
        if (!user.getPhone().matches(UserConstants.PHONE_REGEX)) {
            throw new UserException(UserException.PHONE_FORMAT, new Object[] {});
        }
        // 匹配邮箱格式.
        if (!user.getEmail().matches(UserConstants.EMAIL_REGEX)) {
            throw new UserException(UserException.EMAIL_FORMAT, new Object[] {});
        }
        User newUser=new User();
        newUser.setPhone(user.getPhone());
        newUser.setEmail(user.getEmail());
        newUser.setUserId(iRequest.getUserId());
        userInfoService.update(iRequest, newUser);
        List<User> list = new ArrayList<>();
        list.add(user);
        return new ResponseData(list);
    }

    /**
     * 校验用户.
     * 
     * @param request
     *            统一上下文
     * @param user
     *            用户信息
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return 响应信息
     * @throws UserException
     *             抛出用户已存在的业务异常
     */
    @RequestMapping(value = "/sys/um/isExistsUser")
    @ResponseBody
    public ResponseData isExistsUser(HttpServletRequest request, User user,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) throws UserException {
        IRequest requestContext = createRequestContext(request);
        List<User> list = userInfoService.isExistsPhone(requestContext, user);
        return new ResponseData(list);
    }

    /**
     * 校验联系电话.
     * 
     * @param request
     *            统一上下文.
     * @param user
     *            用户信息
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return 响应信息
     * @throws UserException
     *             抛出联系方式已存在的业务异常
     */
    @RequestMapping(value = "/sys/um/isExistsPhone")
    public ResponseData isExistsPhone(HttpServletRequest request, User user,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) throws UserException {
        IRequest contextRequest = createRequestContext(request);
        List<User> list = userInfoService.isExistsPhone(contextRequest, user);
        return new ResponseData(list);
    }

    /**
     * 验证邮箱是否被占用.
     * 
     * @param request
     *            统一上下文
     * @param user
     *            用户信息
     * @param page
     *            分页信息page
     * @param pagesize
     *            分页信息pagesize
     * @return 返回检查结果
     * @throws UserException
     *             抛出邮箱被占用的业务异常
     */
    @RequestMapping(value = "/sys/um/isExistsEmail", method = RequestMethod.POST)
    public ResponseData isExistsEmail(HttpServletRequest request, User user,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) throws UserException {
        IRequest contextRequest = createRequestContext(request);
        List<User> list = userInfoService.isExistsEmail(contextRequest, user);
        return new ResponseData(list);
    }

    /**
     * 校验邮箱并发送信息.
     * 
     * @param request
     *            统一上下文
     * @param user
     *            用户信息
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @param result
     *            自动校验
     * @return 响应信息
     * @throws Exception
     */
    @RequestMapping(value = "/sys/um/sendMessageByEmail")
    public ResponseData sendMessageByEmail(HttpServletRequest request, User user,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize, BindingResult result) throws Exception {
        IRequest contextRequest = createRequestContext(request);
        // 校验
        // getValidator().validate(user, result);
        // if (result.hasErrors()) {
        // ResponseData rd = new ResponseData(false);
        // rd.setMessage(getErrorMessage(result, request));
        // return rd;
        // }
        // 非空验证
        if (StringUtils.isEmpty(user.getEmail())) {
            throw new UserException(UserException.EMAIL_NOT_ISEMPTY, new Object[] {});
        }
        // 格式验证
        if (!user.getEmail().matches(UserConstants.EMAIL_REGEX)) {
            throw new UserException(UserException.EMAIL_FORMAT, new Object[] {});
        }
        List<User> userNameList = userService.select(contextRequest, user, page, pagesize);
        Cookie cookie = WebUtils.getCookie(request, captchaManager.getCaptchaKeyName());
        // 校验码
        if (cookie == null || !captchaManager.checkCaptcha(cookie.getValue(),
                request.getParameter(UserConstants.KEY_VERIFICODE))) {
            throw new UserException(UserException.LOGIN_VERIFICATION_CODE_ERROR, new Object[] {});
        }
        if (userNameList.isEmpty()) {
            throw new UserException(UserException.EMAIL_NOT_EXIST, new Object[] {});
        }
        if ((UserConstants.USER_STATUS_EXPR).equals(userNameList.get(0).getStatus())) {
            throw new UserException(UserException.USER_EXPIRED, new Object[] {});
        }
        SendRetrieve sendRetrieve = new SendRetrieve();
        sendRetrieve.setUserId(userNameList.get(0).getUserId());
        sendRetrieve.setCreatedBy(userNameList.get(0).getUserId());
        sendRetrieve.setLastUpdatedBy(userNameList.get(0).getUserId());
        sendRetrieve.setEmail(user.getEmail());
        sendRetrieveInsert(request, sendRetrieve);
        // 发送验证码到邮箱
        List<MessageReceiver> receiverlist = new ArrayList<MessageReceiver>();
        MessageReceiver receiver = new MessageReceiver();
        Map<String, Object> data = new HashMap<String, Object>();
        receiver.setMessageAddress(user.getEmail());
        receiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
        receiver.setReceiverId(userNameList.get(0).getUserId());
        receiverlist.add(receiver);
        // 设置message模板里的数据
        data.put("userNameList", userNameList);
        messageService.sendEmailMessage(-1L, null, UserConstants.FORGET_ACCOUNT_EMAIL, "PASSWORD", data, receiverlist,
                null);
        // messageService.addMessage(null, "forget_account_email", data, null,
        // receiverlist);
        if (logger.isDebugEnabled()) {
            logger.debug("你的用户名是：{}", userNameList.get(0).getUserId());
        }
        return new ResponseData(userNameList);
    }

    /**
     * 发送限制.
     * 
     * @param request
     *            统一上下文
     * @param sendRetrieve
     *            发送限制dto
     * @return 记录数量
     */
    private Integer sendRetrieveInsert(HttpServletRequest request, SendRetrieve sendRetrieve) throws UserException {
        SimpleDateFormat df = new SimpleDateFormat(UserConstants.DATE_FORMAT); // 设置日期格式
        try {
            // sendRetrieve.setRetrieveType("NAME"); // 类型先写死
            sendRetrieve.setRetrieveDate(df.parse(df.format(new Date()))); // insert当前时间
            sendRetrieve.setCreationDate(new Date());
            sendRetrieve.setLastUpdateDate(new Date());
        } catch (ParseException e) {
            if (logger.isErrorEnabled()) {
                logger.error(UserException.DATE_FORMAT, e);
            }
        }
        Integer result = sendRetrieveService.insert(createRequestContext(request), sendRetrieve);
        return result;
    }

    /**
     * 根据条件查询用户信息.
     * 
     * @param request
     *            统一上下文
     * @param user
     *            包含查询条件
     * @param page
     *            页码
     * @param pagesize
     *            页数
     * @return ResponseData 响应信息
     * @throws UserException
     *             抛出未找到用户的业务异常
     */
    @RequestMapping(value = "/sys/um/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData query(HttpServletRequest request, User user,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) throws UserException {
        IRequest requestContext = createRequestContext(request);
        List<User> list = userInfoService.getUsers(requestContext, user, page, pagesize);
        if (logger.isDebugEnabled()) {
            logger.debug("查询出的用户信息：{}", list.toString());
        }
        return new ResponseData(list);
    }

    // /**
    // * 查询单条用户信息.
    // *
    // * @param request
    // * 统一上下文
    // * @param userId
    // * 用户ID
    // * @param page
    // * 页码
    // * @param pagesize
    // * 页数
    // * @return ResponseData 响应信息
    // * @throws UserException
    // * 抛出未找到用户的业务异常
    // */
    // @RequestMapping(value = "/sys/um/getSingleUser", method =
    // RequestMethod.POST)
    // @ResponseBody
    // public ResponseData getSingleUser(HttpServletRequest request, Long
    // userId,
    // @RequestParam(defaultValue = DEFAULT_PAGE) int page,
    // @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) throws
    // UserException {
    // IRequest requestContext = createRequestContext(request);
    // User user = new User();
    // user.setUserId(userId);
    // List<User> list = userInfoService.getUsers(requestContext, user, page,
    // pagesize);
    // if (logger.isDebugEnabled()) {
    // logger.debug("查询单条用户信息时用户登录名:{}", list.get(0).getUserName());
    // }
    // return new ResponseData(list);
    // }

    @RequestMapping(value = "/sys/um/sys_user_info.html")
    @ResponseBody
    public ModelAndView userInfo(final HttpServletRequest request) throws UserException {
        ModelAndView mv = new ModelAndView(getViewPath() + "/sys/um/sys_user_info");
        IRequest requestContext = createRequestContext(request);
        User user = userInfoService.selectUserByPrimaryKey(requestContext, requestContext.getUserId());
        Integer length = passwordManager.getPasswordMinLength();
        String complexity = passwordManager.getPasswordComplexity();
        mv.addObject("user", user);
        mv.addObject("length", length);
        mv.addObject("complexity", complexity);
        return mv;
    }

    /**
     * 用户登录密码更改,输入新密码点击确定修改密码.
     * 
     * @param request
     *            统一上下文
     * @param oldPwd
     *            当前密码
     * @param newPwd
     *            新密码
     * @param newPwdAgain
     *            再次输入密码
     * @return 响应信息
     * @throws UserException
     *             抛出密码更新失败的异常
     */
    @RequestMapping(value = "/sys/um/updatePassword", method = RequestMethod.POST)
    public ResponseData updatePassword(HttpServletRequest request, String oldPwd, String newPwd, String newPwdAgain)
            throws UserException {
        IRequest iRequest = createRequestContext(request);
        Long accountId = iRequest.getUserId();
        HttpSession session = request.getSession(false);
        Object expire = session.getAttribute(User.PASSWORD_EXPIRE_VERIFY);
        iRequest.setAttribute(User.PASSWORD_EXPIRE_VERIFY, expire);
        if (userInfoService.validatePassword(iRequest, oldPwd, newPwd, newPwdAgain, accountId)) {
            userService.updatePassword(accountId, newPwd);
            // 如果用户密码过期 修改成功后 清除session
            if (session != null) {
                if (expire != null) {
                    session.removeAttribute(User.PASSWORD_EXPIRE_VERIFY);
                    // 更新是否第一次登录的状态
                    userService.updateNotFirstLogin(accountId, User.NOT_FIRST_LOGIN_STATUS);
                }
            }
            return new ResponseData(true);
        }
        return new ResponseData(false);
    }

    /**
     * 管理员重置用户密码.
     * 
     * @param request
     * @param password
     * @return
     * @throws UserException
     */
    @RequestMapping(value = "/sys/um/resetPasswordAdmin", method = RequestMethod.POST)
    public ResponseData updatePassword(HttpServletRequest request, String password, Long userId) throws UserException {
        userService.updatePassword(userId, password);
        userService.updateNotFirstLogin(userId, User.FIRST_LOGIN_STATUS);
        return new ResponseData(true);
    }

    /**
     * 忘记密码,第一步验证用户名.
     * 
     * @param request
     *            统一上下文
     * @param userName
     *            需要验证的用户名
     * @return 返回信息给ajax回调
     * @throws UserException
     *             抛出验证码验证的业务异常
     */
    @RequestMapping(value = "/sys/sc/checkUser", method = RequestMethod.POST)
    public ResponseData checkUser(HttpServletRequest request, String userName) throws UserException {
        // 判断用户是否输入完整信息
        StringBuilder errorMessage = new StringBuilder();
        if (StringUtils.isEmpty(userName)) {
            errorMessage.append(UserException.ENTER_USERNAME);
        }
        String verifyCode = request.getParameter(UserConstants.KEY_VERIFICODE);
        if (StringUtils.isEmpty(verifyCode)) {
            if (errorMessage.length() == 0) {
                errorMessage.append(UserException.ENTER_VERIFICATION);
            } else { // 两个异常-用户没有输入用户名和密码时,功能文档说要加序号
                errorMessage.insert(0, UserConstants.TWO_EXCEPTION_SEQ_1);
                errorMessage.append(UserConstants.TWO_EXCEPTION_SEQ_2).append(UserException.ENTER_VERIFICATION);
            }
        }
        if (errorMessage.length() > 0) {
            throw new UserException(errorMessage.toString(), new Object[] {});
        }

        IRequest requestContext = createRequestContext(request);
        Cookie cookie = WebUtils.getCookie(request, captchaManager.getCaptchaKeyName());
        // 校验码
        if (cookie == null || !captchaManager.checkCaptcha(cookie.getValue(),
                request.getParameter(UserConstants.KEY_VERIFICODE))) {
            throw new UserException(UserException.LOGIN_VERIFICATION_CODE_ERROR, new Object[] {});
        }
        // 验证数据库是否有这个用户-不区分大小写-在service里已经做了用户不存在和用户失效的校验
        User checkUser = userInfoService.selectUserByName(requestContext, userName);
        // 用户名验证成功,跳转到选择验证方式界面
        // 生成一个key,策略采用uuid方式
        String key = generateCaptchaKey();
        // 将四位数字的验证码保存到Session中。
        redisTemplate.opsForValue().set(UserConstants.FORGET_PWD_KEY + ":" + key, checkUser.getUserId().toString(),
                UserConstants.KEY_EXPIRE_SIXTY_MIN, TimeUnit.SECONDS);
        // 处理返回信息
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put(UserConstants.UUID_KEY, key);
        list.add(map);
        return new ResponseData(list);
    }

    /**
     * 拦截访问/sys/sc/sc_validate页面.
     * 
     * @param uuidKey
     *            key
     * @return 检查redis是否有钥匙,没有则跳转到验证用户名页面
     */
    @RequestMapping(value = "/sys/sc/sc_validate")
    @ResponseBody
    public ModelAndView scValidate(String uuidKey) {
        ModelAndView mav = new ModelAndView();
        // 先检查uuidKey是否正确
        String uk = redisTemplate.opsForValue().get(UserConstants.FORGET_PWD_KEY + ":" + uuidKey);
        if (StringUtils.isEmpty(uk)) { // key不对,未通过验证
            mav.setViewName(UserConstants.REDIRECT + UserConstants.VIEW_FORGET_PWD);
        } else {
            mav.setViewName(getViewPath() + UserConstants.VIEW_VALIDATE);
            mav.addObject(UserConstants.UUID_KEY, uuidKey);
        }
        return mav;
    }

    /**
     * 忘记密码,取得验证码界面ajax请求user数据填充页面.
     * 
     * @param request
     *            统一上下文
     * @param uuidKey
     *            key
     * @return 响应信息
     * @throws UserException
     *             抛出未找到用户的业务异常
     */
    @RequestMapping(value = "/sys/sc/getSingleUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getSingleUserInfo(HttpServletRequest request, String uuidKey) throws UserException {
        IRequest requestContext = createRequestContext(request);
        Long userId = Long.parseLong(redisTemplate.opsForValue().get(UserConstants.FORGET_PWD_KEY + ":" + uuidKey));
        User checkUser = userInfoService.selectUserByPrimaryKey(requestContext, userId);
        // new一个user用来存放需要返回的三个字符串信息
        User returnUser = new User();
        // 隐藏部分userName/email/phone信息---start
        // userName
        StringBuilder secretUserName = new StringBuilder(checkUser.getUserName());
        for (int i = 2; i < secretUserName.length() - 2; i++) { // 用户名6-20位,显示前两位后两位
            secretUserName.setCharAt(i, UserConstants.SC_HIDE_SYMBOL);
        }
        returnUser.setUserName(secretUserName.toString());
        // email
        if (StringUtils.isNotBlank(checkUser.getEmail())) { // 如果用户有邮箱
            StringBuilder secretEmail = new StringBuilder(checkUser.getEmail());
            int subLength = secretEmail.substring(0, secretEmail.indexOf(UserConstants.SC_EMAIL_AT)).length();
            if (subLength > 2) { // @之前的长度大于2位显示后两位
                for (int i = 0; i < subLength - 2; i++) {
                    secretEmail.setCharAt(i, UserConstants.SC_HIDE_SYMBOL);
                }
            } else { // 否则,显示最后一位
                for (int i = 0; i < subLength - 1; i++) {
                    secretEmail.setCharAt(i, UserConstants.SC_HIDE_SYMBOL);
                }
            }
            returnUser.setEmail(secretEmail.toString());
        }
        // phone
        StringBuilder secretPhone = new StringBuilder(checkUser.getPhone());
        for (int i = 0; i < secretPhone.length() - UserConstants.SC_PHONE_AFTER_HIDE_REMINE; i++) { // 手机,显示后四位
            secretPhone.setCharAt(i, UserConstants.SC_HIDE_SYMBOL);
        }
        returnUser.setPhone(secretPhone.toString());
        // 隐藏部分userName/email/phone信息---end
        List<User> list = new ArrayList<User>();
        list.add(returnUser);
        return new ResponseData(list);
    }

    /**
     * 忘记密码,点击发送验证码到邮箱或手机.
     * 
     * @param request
     *            统一上下文
     * @param uuidKey
     *            key
     * @return 返回发送情况信息给前台
     * @throws UserException
     *             抛出发送验证码相关的业务异常
     * @throws Exception
     *             抛出短信接口异常
     */
    @RequestMapping(value = "/sys/sc/sendCaptcha", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData sendCaptcha(HttpServletRequest request, String uuidKey) throws Exception {
        IRequest requestContext = createRequestContext(request);
        ResponseData result = new ResponseData();
        // 检查是否属于频繁点击
        if (redisTemplate.opsForValue().get(UserConstants.FILED_SC_VERIFICODE_BIRTHDAY) != null) {
            Long birthInMillis = Long
                    .parseLong(redisTemplate.opsForValue().get(UserConstants.FILED_SC_VERIFICODE_BIRTHDAY));
            Long nowInMillis = new Date().getTime();
            // 距离上次发送验证码不足60秒
            if ((nowInMillis - birthInMillis)
                    / UserConstants.SC_MILLIS_PER_MINUTE < UserConstants.SC_VERIFICODE_SEND_INTERVAL) {
                result.setCode(UserConstants.SC_STILL_HAVE_TO_WAIT
                        + (UserConstants.SC_VERIFICODE_SEND_INTERVAL
                                - (nowInMillis - birthInMillis) / UserConstants.SC_MILLIS_PER_MINUTE)
                        + UserConstants.SC_SECOND);
                return result;
            }
        }
        Long userId = null;
        // 再检查uuidKey是否过期
        String uk = redisTemplate.opsForValue().get(UserConstants.FORGET_PWD_KEY + ":" + uuidKey);
        if (StringUtils.isEmpty(uk)) { // key已失效,报错
            result.setCode(UserConstants.PAGE_VALIDATE_EXPIRED);
            return result;
        } else {
            userId = Long.parseLong(uk);
        }
        User checkUser = userInfoService.selectUserByPrimaryKey(requestContext, userId);
        String verifyCode = "";
        // 随机生成6位不重复数字验证码
        String oldCode = redisTemplate.opsForValue().get(UserConstants.FILED_SC_VERIFICODE);
        if (oldCode == null) {
            verifyCode = generateVerifyCode();
        } else {
            verifyCode = oldCode;
        }
        redisTemplate.opsForValue().set(UserConstants.FILED_SC_VERIFICODE, verifyCode,
                UserConstants.KEY_EXPIRE_SIXTY_MIN, TimeUnit.SECONDS);
        // 发送验证码到邮箱
        List<MessageReceiver> receiverlist = new ArrayList<MessageReceiver>();
        MessageReceiver receiver = new MessageReceiver();
        Map<String, Object> data = new HashMap<String, Object>();
        receiver.setMessageAddress(checkUser.getEmail());
        receiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
        receiver.setReceiverId(userId);
        receiverlist.add(receiver);
        // 设置message模板里的数据
        data.put("verifyCode", verifyCode);
        data.put("limit", UserConstants.SC_VERIFICODE_TIME_LIMIT);
        data.put("userName", checkUser.getUserName());
        messageService.sendEmailMessage(-1L, null, UserConstants.EMAIL_SC_FORGET_PASSWORD, "PASSWORD", data,
                receiverlist, null);
        // messageService.addMessage(null,
        // UserConstants.EMAIL_SC_FORGET_PASSWORD, data, null, receiverlist);
        if (logger.isDebugEnabled()) {
            logger.debug("此时发送验证码到用户邮箱(--->{}),可能用到的参数userId={},验证码为{}", checkUser.getEmail(), userId, verifyCode);
        }
        // 发送验证码之后,在session里存当前时间防止用户频繁点击
        if (redisTemplate.opsForValue().get(UserConstants.FILED_SC_VERIFICODE_BIRTHDAY) == null) {
            redisTemplate.opsForValue().set(UserConstants.FILED_SC_VERIFICODE_BIRTHDAY,
                    Long.toString(new Date().getTime()), UserConstants.KEY_EXPIRE_SIXTY_MIN, TimeUnit.SECONDS);
        }
        List<Object> list = new ArrayList<Object>();
        Map<String, String> map = new HashMap<String, String>();
        // map.put("verifyCode", verifyCode);
        list.add(map);
        result.setRows(list);
        return result;
    }

    /**
     * 忘记密码,验证验证码.
     * 
     * @param request
     *            统一上下文
     * @param captcha
     *            验证码
     * @param way
     *            验证方式
     * @param uuidKey
     *            key
     * @return 返回验证情况给前台
     * @throws UserException
     *             抛出验证验证码相关业务异常
     */
    @RequestMapping(value = "/sys/sc/checkCaptcha")
    public ResponseData checkCaptcha(HttpServletRequest request, String captcha, String way, String uuidKey)
            throws UserException {
        HttpSession session = request.getSession();
        Cookie cookie = WebUtils.getCookie(request, captchaManager.getCaptchaKeyName());
        // 四位验证码
        if (cookie == null || !captchaManager.checkCaptcha(cookie.getValue(),
                request.getParameter(UserConstants.KEY_VERIFICODE))) {
            throw new UserException(UserException.LOGIN_VERIFICATION_CODE_ERROR, new Object[] {});
        }
        // 检查用户是否输入校验码
        if (StringUtils.isEmpty(captcha)) {
            throw new UserException(UserException.ENTER_CAPTCHA, new Object[] {});
        }
        // 检查session里是否有校验码
        if (redisTemplate.opsForValue().get(UserConstants.FILED_SC_VERIFICODE) == null) {
            throw new UserException(UserException.CAPTCHA_EXPIRED, new Object[] {});
        }
        String verifiCode = redisTemplate.opsForValue().get(UserConstants.FILED_SC_VERIFICODE);
        if (!verifiCode.equals(captcha)) { // 验证码不正确
            throw new UserException(UserException.CAPTCHA_ERROR, new Object[] {});
        }
        // 如果校验码正确,验证校验码是否已经过期
        Long birthInMillis = Long
                .parseLong(redisTemplate.opsForValue().get(UserConstants.FILED_SC_VERIFICODE_BIRTHDAY));
        Long nowInMillis = new Date().getTime();
        if ((nowInMillis - birthInMillis)
                / UserConstants.SC_MILLIS_PER_MINUTE > UserConstants.SC_VERIFICODE_TIME_LIMIT) { // 距离上次发送验证码超过600秒
            throw new UserException(UserException.CAPTCHA_EXPIRED, new Object[] {});
        }
        // 校验码验证成功
        // 其实验证成功就相当于登录了,这里模拟一个登录后的状态,直接复用首次登录修改密码的页面
        Long userId = Long.parseLong(redisTemplate.opsForValue().get(UserConstants.FORGET_PWD_KEY + ":" + uuidKey));
        session.setAttribute(User.FIELD_USER_ID, userId);
        // 首次登录标记设置为N
        session.setAttribute(UserConstants.FIRST_LOGIN_FLAG, UserConstants.FIRST_LOGIN_FLAG_N);
        // 清除忘记密码无用的redis变量
        redisTemplate.execute((RedisCallback<Object>) (connection) -> {
            connection.del((UserConstants.FORGET_PWD_KEY + ":" + uuidKey).getBytes());
            connection.del(UserConstants.FILED_SC_VERIFICODE.getBytes());
            connection.del(UserConstants.FILED_SC_VERIFICODE_BIRTHDAY.getBytes());
            return null;
        });
        // 返回成功到前台,前台执行跳转到修改密码界面
        return new ResponseData();
    }

    /**
     * 生成captchaKey.
     * 
     * @return 返回 UUID 形式的 captchaKey
     */
    private String generateCaptchaKey() {
        return UUID.randomUUID().toString();
    }

    /**
     * 随机生成6位不重复数字验证码.
     * 
     * @return 验证码
     */
    private String generateVerifyCode() {
        StringBuilder sb = new StringBuilder();
        String str = UserConstants.SC_VERIFICODE_BASE_STR;
        Random r = new Random();
        for (int i = 0; i < UserConstants.SC_VERIFICODE_LENGTH; i++) {
            int num = r.nextInt(str.length());
            sb.append(str.charAt(num));
            str = str.replace((str.charAt(num) + ""), "");
        }
        return sb.toString();
    }
}
