package com.hand.hap.security;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hand.hap.account.dto.User;
import com.hand.hap.message.profile.SystemConfigListener;
import com.hand.hap.mybatis.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;


/**
 * Created by hailor on 16/6/12.
 */
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements SystemConfigListener{

    @Autowired
    private ApplicationContext applicationContext;

    private RequestCache requestCache = new HttpSessionRequestCache();


    private Logger logger = LoggerFactory.getLogger(getClass());

    {
        setDefaultTargetUrl("/index");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        clearAuthenticationAttributes(request);
        Map<String, IAuthenticationSuccessListener> listeners = applicationContext
                .getBeansOfType(IAuthenticationSuccessListener.class);
        List<IAuthenticationSuccessListener> list = new ArrayList<>();
        list.addAll(listeners.values());
        Collections.sort(list);
        IAuthenticationSuccessListener successListener = null;
        try {
            for (IAuthenticationSuccessListener listener : list) {
                successListener = listener;
                successListener.onAuthenticationSuccess(request, response, authentication);
            }
            HttpSession session = request.getSession(false);
            session.setAttribute(User.LOGIN_CHANGE_INDEX,"CHANGE");
        } catch (Exception e) {
            logger.error("authentication success, but error occurred in " + successListener, e);
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            request.setAttribute("error", true);
            request.setAttribute("exception", e);

            request.getRequestDispatcher("/login").forward(request, response);
            return;
        }
        String requestURI = request.getRequestURI();
        boolean isCas = requestURI.endsWith("/login/cas");
        if(isCas) {
            //拿到登录以前的url
            SavedRequest savedRequest = this.requestCache.getRequest(request, response);
            if (savedRequest != null) {
                String targetUrl = savedRequest.getRedirectUrl();
                String defaultTarget = getDefaultTargetUrl();
                if ( ! "/index".equalsIgnoreCase(defaultTarget)) {
                   targetUrl = getDefaultTargetUrl()+"?targetUrl="+targetUrl;
                }
                this.getRedirectStrategy().sendRedirect(request, response, targetUrl);
                return;
            }
        }
        handle(request, response, authentication);
    }

    @Override
    public List<String> getAcceptedProfiles() {
         return Arrays.asList("DEFAULT_TARGET_URL");
    }

    @Override
    public void updateProfile(String profileName, String profileValue) {
        if(StringUtil.isNotEmpty(profileValue)) {
            setDefaultTargetUrl(profileValue);
        }
    }
}
