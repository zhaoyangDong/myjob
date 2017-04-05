/*
 * #{copyright}#
 */

package com.hand.hap.system.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hap.system.dto.Profile;

/**
 * @author Frank.li
 */
public interface ProfileMapper extends Mapper<Profile> {

    Profile selectByName(String profileName);
}