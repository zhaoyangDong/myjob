/*
 * #{copyright}#
 */

package com.hand.hap.system.controllers.sys;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.Profile;
import com.hand.hap.system.dto.ProfileValue;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.IProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author frank.li
 */
@Controller
public class ProfileController extends BaseController {


    @Autowired
    private IProfileService profileService;

    @RequestMapping(value = "/sys/profile/query")
    @ResponseBody
    public ResponseData getProfiles(Profile profile, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        return new ResponseData(profileService.selectProfiles(profile, page, pagesize));
    }

    @RequestMapping(value = "/sys/profilevalue/query")
    @ResponseBody
    public ResponseData getProfileValues(ProfileValue value) {
        return new ResponseData(profileService.selectProfileValues(value));
    }

    @RequestMapping(value = "/sys/profilevalue/querylevelvalues")
    @ResponseBody
    public ResponseData getLevelValues(ProfileValue value,Long levelId, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        return new ResponseData(profileService.selectLevelValues(value,levelId, page, pagesize));
    }

    @RequestMapping(value = "/sys/profile/submit", method = RequestMethod.POST)
    public ResponseData submitProfiles(@RequestBody List<Profile> profiles, BindingResult result,
            HttpServletRequest request) {
        // validator.validate(profiles, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(profileService.batchUpdate(requestContext, profiles));
    }

    @RequestMapping(value = "/sys/profile/remove", method = RequestMethod.POST)
    public ResponseData deleteProfile(@RequestBody List<Profile> profiles, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        profileService.batchDelete(requestContext, profiles);

        return new ResponseData();
    }

    @RequestMapping(value = "/sys/profilevalue/remove", method = RequestMethod.POST)
    public ResponseData removeProfileValues(@RequestBody List<ProfileValue> profileValues, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        profileService.batchDeleteValues(requestContext, profileValues);
//        System.out.println("removeProfileValues");
        return new ResponseData();
    }
}
