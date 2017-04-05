package com.hand.hap.core.components;

import com.hand.hap.account.dto.User;
import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.util.StringUtil;
import com.hand.hap.security.CustomUserDetails;
import com.hand.hap.security.IAuthenticationSuccessListener;
import com.hand.hap.system.dto.UserLogin;
import com.hand.hap.system.mapper.UserLoginMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * Created by jialong.zuo@hand-china.com on 2016/10/11. on 2016/10/11.
 */
@Component
public class UserLoginInfoCollection implements IAuthenticationSuccessListener {

    @Autowired
    UserLoginMapper userLoginMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) {

        String ipAddress = getIpAddress(request);

        UserLogin userLogin = new UserLogin();
        userLogin.setUserId((Long)request.getSession(false).getAttribute(User.FIELD_USER_ID));
        String referer = request.getHeader("Referer");
        referer = StringUtils.abbreviate(referer,240);
        userLogin.setReferer(referer);
        userLogin.setUserAgent(request.getHeader("User-Agent"));
        userLogin.setIp(ipAddress);
        userLogin.setLoginTime(new Date());

        userLoginMapper.insertSelective(userLogin);
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute(IRequest.FIELD_LOGIN_ID, userLogin.getLoginId());
        }

    }

    @Override
    public int getOrder() {
        return 999;
    }

    public String getIpAddress(HttpServletRequest request) {

        String ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress= inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }
        return ipAddress;

    }
}
