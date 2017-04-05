/*
 * #{copyright}#
 */
package com.hand.hap.account.constants;


import com.hand.hap.core.BaseConstants;

/**
 * 常量.
 * 
 * @author chenjingxiong
 */
public class UserConstants implements BaseConstants {
    /**
     * 密码管理 - 忘记密码 - 验证码有效时间(s).
     */
    public static final Long SC_VERIFICODE_TIME_LIMIT = (long) 3600; // 忘记密码验证码时效(s)
    /**
     * 密码管理 - 忘记密码 - 验证码发送时间间隔(s).
     */
    public static final Long SC_VERIFICODE_SEND_INTERVAL = (long) 60; // 忘记密码验证码发送时间间隔(s)
    /**
     * 密码管理 - 忘记密码 - 隐藏信息使用的符号.
     */
    public static final char SC_HIDE_SYMBOL = '*'; // 使用星号隐藏信息
    /**
     * 密码管理 - 忘记密码 - 隐藏手机信息后几位(MagicNumber).
     */
    public static final int SC_PHONE_AFTER_HIDE_REMINE = 4; // 隐藏手机信息时显示后4位
    /**
     * 密码管理 - 忘记密码 - 邮箱的@符号.
     */
    public static final String SC_EMAIL_AT = "@";
    /**
     * 密码管理 - 忘记密码 - 一秒等于一千毫秒(MagicNumber).
     */
    public static final int SC_MILLIS_PER_MINUTE = 1000; // 一秒等于1000毫秒
    /**
     * 密码管理 - 忘记密码 - 频繁点击提示等待.
     */
    public static final String SC_STILL_HAVE_TO_WAIT = "still have to wait about ";
    /**
     * 密码管理 - 忘记密码 - 时间单位秒.
     */
    public static final String SC_SECOND = "s";
    /**
     * 密码管理 - 忘记密码 - 验证码发送方式-手机.
     */
    public static final String SC_VERIFY_WAY_PHONE = "PHONE";
    /**
     * 密码管理 - 忘记密码 - 验证码发送方式-邮箱.
     */
    public static final String SC_VERIRY_WAY_EMAIL = "EMAIL";
    /**
     * 密码管理 - 忘记密码 - 验证码长度(MagicNumber).
     */
    public static final int SC_VERIFICODE_LENGTH = 6; // 验证码长度
    /**
     * 密码管理 - 忘记密码 - 验证码随机程序的基础字符串. 生成8位随机不重复数字,因不重复而使用
     */
    public static final String SC_VERIFICODE_BASE_STR = "0123456789";
    /**
     * 密码管理 - 忘记密码 - ICaptchaManager验证码使用的常量.
     */
    public static final String KEY_VERIFICODE = "verifiCode"; // ICaptchaManager验证码使用的常量
    /**
     * 密码管理 - 忘记密码 - session中功能专有字段-UserId.
     */
    public static final String FILED_SC_USER_ID = "scUserId";
    /**
     * 密码管理 - 忘记密码 - session中功能专有字段-VerifiCode.
     */
    public static final String FILED_SC_VERIFICODE = "scVerifiCode";
    /**
     * 密码管理 - 忘记密码 - session中功能专有字段-VerifiCodeBirth-验证码产生时间戳.
     */
    public static final String FILED_SC_VERIFICODE_BIRTHDAY = "scVerifiCodeBirth";
    /**
     * 密码管理 - 忘记密码 - 页面失效.
     */
    public static final String PAGE_VALIDATE_EXPIRED = "redirect";
    /**
     * 用户管理 - 修改密码 - 返回重新登录时的选择角色页面链接.
     */
    public static final String VIEW_ROLE_SELECT = "/role";
    
    public static final String VIEW_INDEX_SELECT = "/";
    /**
     * 用户管理 - 修改密码 - 登录成功页面链接.
     */
    public static final String USER_PWD_URL = "/sys/um/sys_login_success.html";
    /**
     * 用户管理 - 个人信息 - 默认用户.
     */
    public static final String ACCOUNT_TYPE_USER = "USER";
    /**
     * 用户管理 - 用户状态(有效).
     */
    public static final String USER_STATUS_ACTIVE = "ACTV";
    /**
     * 用户管理 - 用户状态(新增).
     */
    public static final String USER_STATUS_NEW = "NEW";
    /**
     * 调用userService的selectUsers时. 必须传入分页信息page
     */
    public static final Integer DEFAULT_PAGE = 1;
    /**
     * 调用userService的selectUsers时. 必须传入分页信息pageSize
     */
    public static final Integer DEFAULT_PAGE_SIZE = 10;
    /**
     * 正则表达式-loginName.
     */
    public static final String UESR_NAME_REGEX = "^[A-Za-z0-9]{6,20}$";
    /**
     * 正则表达式-userName.
     */
    public static final String USER_NAME_REGEX = "^[\\s\\S]{1,20}$";
    /**
     * 正则表达式-phone.
     */
    public static final String PHONE_REGEX = "^1[3|4|5|8][0-9]\\d{4,8}";
    /**
     * 正则表达式-email.
     */
    public static final String EMAIL_REGEX = "^([\\s\\S]*)+@([\\S\\s]*)+(\\.([\\S\\s]*)+)+$";
    /**
     * 首次登录标记.
     */
    public static final String FIRST_LOGIN_FLAG = "first";
    /**
     * 首次登录标记-Y.
     */
    public static final String FIRST_LOGIN_FLAG_Y = "Y";
    /**
     * 首次登录标记-N.
     */
    public static final String FIRST_LOGIN_FLAG_N = "N";
    /**
     * 两个业务异常-序号1.
     */
    public static final String TWO_EXCEPTION_SEQ_1 = "1.";
    /**
     * 两个业务异常-序号2.
     */
    public static final String TWO_EXCEPTION_SEQ_2 = "\n2.";
    /**
     * 用户管理 - 用户失效.
     */
    public static final String USER_STATUS_EXPR = "EXPR";
    /**
     * 用户管理 - 密码格式校验.
     */
    public static final String PASSWORD_FORMAT = "^(?![^a-zA-Z]+$)(?!\\D+$)[a-zA-Z0-9._`~!@#$%^&*()+-={}:;<>?,\\\\\"\'\\[\\]]{8,}$";
    /**
     * 用户管理 - 随机生成的临时密码.
     */
    public static final String TMP_PASSWORD = "11111111";
    /**
     * 用户管理 - 密码有效时间.
     */
    public static final int PWD_EXPRIRY_DATE = 1000 * 60 * 60;
    /**
     * 密码管理 - 忘记密码 - 验证码已发送至邮箱.
     */
    public static final String VERIFICATION_SENT_EMAIL = "msg.info.system.verification_sent_email";
    /**
     * 密码管理 - 忘记密码 - 验证码已发送至手机.
     */
    public static final String VERIFICATION_SENT_PHONE = "msg.info.system.verification_sent_phone";

    /**
     * 密码管理 - 忘记密码 - 专用KEY标记存放在redis模板的描述.
     */
    public static final String FORGET_PWD_KEY = "dsis.forget.pwd.key";

    /**
     * 密码管理 - 忘记密码 - 专用KEY标记.
     */
    public static final String UUID_KEY = "uuidKey";

    /**
     * 密码管理 - 忘记密码 - 专用KEY标记的默认过期时间-5分钟.
     */
    public static final int KEY_EXPIRE = 60 * 5;

    /**
     * 密码管理 - 忘记密码 - 专用KEY标记过期时间-10分钟.
     */
    public static final int KEY_EXPIRE_TEN = 60 * 10;
    
    /**
     * 密码管理 - 忘记密码 - 专用KEY标记过期时间-60分钟.
     */
    public static final int KEY_EXPIRE_SIXTY_MIN = 60 * 60;

    /**
     * 密码管理 - 忘记密码 - 专用KEY标记不匹配.
     */
    public static final String KEY_NOT_MATCH = "key.not.match";

    /**
     * 密码管理 - 忘记密码 - 页面跳转.
     */
    public static final String REDIRECT = "redirect:";

    /**
     * 密码管理 - 忘记密码 - 默认VIEW_HOME.
     */
    public static final String DEFAULT_VIEW_HOME = "admin";

    /**
     * 密码管理 - 忘记密码 - 验证用户名页面.
     */
    public static final String VIEW_FORGET_PWD = "/sys/sc/sc_forget_pwd.html";

    /**
     * 密码管理 - 忘记密码 - 登录页面.
     */
    public static final String VIEW_LOGIN = "/login.html";

    /**
     * 密码管理 - 忘记密码 - 登录页面.
     */
    public static final String VIEW_UPDATE_PWD = "/updatePassword";

    /**
     * 密码管理 - 忘记密码 - 验证验证码页面.
     */
    public static final String VIEW_VALIDATE = "/sys/sc/sc_validate";

    /**
     * 密码管理 - 忘记密码 - 验证码验证方式.
     */
    public static final String VALIDATE_WAY = "way";

    /**
     * 日期格式 - yyyy-MM-dd.
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 发送限制 - 超过两次.
     */
    public static final String SENT_LIMIT_ERROR = "当天发送超过2次限制";

    /**
     * 用户类型 - IPONT: ipoint用户.
     */
    public static final String USER_TYPE_IPONT = "IPONT";

    /**
     * 用户类型 - INNER: 内部用户.
     */
    public static final String USER_TYPE_INNER = "INNER";

    /**
     * 用户类型 - MEM: Member用户.
     */
    public static final String USER_TYPE_MEM = "MEM";
    
    /**
     * 消息模板-临时口令.
     */
    public static final String TEMP_PWD_MESSAGE_TEMPLATE = "EMAIL_USER_TEMP_PWD";
    
    /**
     * 消息模板-忘记密码.
     */
    public static final String EMAIL_SC_FORGET_PASSWORD = "EMAIL_SC_FORGET_PASSWORD";



    /**
     * 忘记账号-消息模板-发送验证码到电话.
     */
    public static final String PHONE_FORGET_ACCOUNT_VCODE = "PHONE_FORGET_ACCOUNT_VCODE";
    /**
     * 忘记账号-消息模板-发送验证码到邮箱.
     */
    public static final String EMAIL_FORGET_ACCOUNT_VCODE = "EMAIL_FORGET_ACCOUNT_VCODE";
    /**
     * 忘记账号-消息模板-发送账号到电话消息模板.
     */
    public static final String FORGET_ACCOUNT_PHONE = "FORGET_ACCOUNT_PHONE";
    /**
     * 忘记账号-消息模板-发送账号到邮箱.
     */
    public static final String FORGET_ACCOUNT_EMAIL = "FORGET_ACCOUNT_EMAIL";
    /**
     * 新建用户或管理员修改密码时产出临时口令-发送账号到短信.
     */
    public static final String PHONE_USER_TEMP_PWD = "PHONE_USER_TEMP_PWD";
    /**
     * 消息模板-新会员欢迎短信.
     */
    public static final String PHONE_NEW_MEMBER_WELCOME = "PHONE_NEW_MEMBER_WELCOME";

    /**
     * 消息模板-会员重置密码短信.
     */
    public static final String PHONE_NEW_MEMBER_PASSWORD = "PHONE_NEW_MEMBER_PASSWORD";
    /**
     * 消息模板-忘记密码-发送短信.
     */
    public static final String PHONE_SC_FORGET_PASSWORD = "PHONE_SC_FORGET_PASSWORD";
    

}
