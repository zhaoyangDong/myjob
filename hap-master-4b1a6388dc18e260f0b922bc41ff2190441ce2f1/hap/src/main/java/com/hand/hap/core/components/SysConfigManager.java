package com.hand.hap.core.components;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hand.hap.message.profile.SystemConfigListener;

/**
 * 系统配置
 *
 * @author xuhailin@hand-china.com update qixiangyu@hand-china.com
 */
@Component
public class SysConfigManager implements SystemConfigListener {

    // system title
    private String sysTitle = "Hand Application Platform";

    // 是否角色合并
    private boolean roleMergeFlag = true;

    //第一次登录是否强制修改密码
    private boolean resetPwFlag = false;

    public String getSysTitle() {
        return sysTitle;
    }

    public boolean getRoleMergeFlag() {
        return roleMergeFlag;
    }

    public boolean getResetPwFlag() {
        return resetPwFlag;
    }

    @Override
    public List<String> getAcceptedProfiles() {
        return Arrays.asList("SYS_TITLE", "USER_ROLE_MERGE","FIRST_LOGIN_RESET_PASSWORD");
    }

    @Override
    public void updateProfile(String profileName, String profileValue) {
        if ("SYS_TITLE".equalsIgnoreCase(profileName)) {
            this.sysTitle = profileValue;
        } else if ("USER_ROLE_MERGE".equalsIgnoreCase(profileName)) {
            if ("N".equalsIgnoreCase(profileValue)) {
                this.roleMergeFlag = false;
            } else {
                this.roleMergeFlag = true;
            }
        }else if("FIRST_LOGIN_RESET_PASSWORD".equalsIgnoreCase(profileName)){
            if("Y".equalsIgnoreCase(profileValue)){
                this.resetPwFlag = true;
            }else{
                this.resetPwFlag = false;
            }
        }
    }

}
