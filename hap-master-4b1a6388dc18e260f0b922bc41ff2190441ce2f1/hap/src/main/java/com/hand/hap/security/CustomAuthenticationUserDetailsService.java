package com.hand.hap.security;

import com.hand.hap.account.dto.User;
import com.hand.hap.account.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by hailor on 16/6/16.
 */
public class CustomAuthenticationUserDetailsService implements AuthenticationUserDetailsService {

    @Autowired
    private IUserService userService;

    Logger logger = LoggerFactory.getLogger(CustomAuthenticationUserDetailsService.class);
    @Override
    public UserDetails loadUserDetails(Authentication authentication) throws UsernameNotFoundException {
        logger.debug("===========================================================");
        logger.debug(authentication.getPrincipal().toString());

        User user = userService.selectByUserName(authentication.getPrincipal().toString());
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails userDetails = null;
        if (user == null) {
             userDetails = new CustomUserDetails(0L, authentication.getPrincipal().toString(),
                    "", true, true, true, true, authorities);
        }else{
            userDetails = new CustomUserDetails(user.getUserId(), user.getUserName(),
                    user.getPasswordEncrypted(), true, true, true, true, authorities);
        }




        return userDetails;
    }
}
