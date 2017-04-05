package com.hand.hap.security.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hand.hap.account.dto.User;
import com.hand.hap.account.exception.UserException;
import com.hand.hap.account.service.IUserService;
import com.hand.hap.security.CustomUserDetails;

/**
 * Created by hailor on 16/6/12.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.selectByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found:" + username);
        }

        checkUserException(user);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetails userDetails = new CustomUserDetails(user.getUserId(), user.getUserName(),
                user.getPasswordEncrypted(), true, true, true, true, authorities);
        return userDetails;
    }

    private void checkUserException(User user) {
        UserException ue = null;
        if (User.STATUS_LOCK.equalsIgnoreCase(user.getStatus())) {
            ue = new UserException(UserException.ERROR_USER_LOCKED, null);
        } else if (User.STATUS_EXPR.equalsIgnoreCase(user.getStatus())) {
            ue = new UserException(UserException.ERROR_USER_EXPIRED, null);
        } else if (user.getStartActiveDate() != null
                && user.getStartActiveDate().getTime() > System.currentTimeMillis()) {
            ue = new UserException(UserException.ERROR_USER_NOT_ACTIVE, null);
        } else if (user.getEndActiveDate() != null && user.getEndActiveDate().getTime() < System.currentTimeMillis()) {
            ue = new UserException(UserException.ERROR_USER_EXPIRED, null);
        }
        if (ue != null) {
            throw new RuntimeException(ue);
        }
    }

}
