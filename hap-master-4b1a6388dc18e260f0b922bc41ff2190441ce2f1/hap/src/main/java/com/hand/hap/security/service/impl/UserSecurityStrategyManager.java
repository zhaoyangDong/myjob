package com.hand.hap.security.service.impl;

import com.hand.hap.core.AppContextInitListener;
import com.hand.hap.security.IUserSecurityStrategy;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Qixiangyu on 2016/12/22.
 */
@Component
public class UserSecurityStrategyManager implements AppContextInitListener {

    List<IUserSecurityStrategy> userAuthenticationList= Collections.emptyList();
    @Override
    public void contextInitialized(ApplicationContext applicationContext) {
        userAuthenticationList= new ArrayList<>(applicationContext.getBeansOfType(IUserSecurityStrategy.class).values());
        Collections.sort(userAuthenticationList);
    }

    public  List<IUserSecurityStrategy> getUserAuthenticationList(){
        return userAuthenticationList;
    }
}
