package com.hand.hap.system.controllers.sys;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.DashBoard;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.dto.UserDashboard;
import com.hand.hap.system.service.IDashBoardService;

/**
 * DashBoardController.
 * 
 * @author zhizheng.yang@hand-china.com
 */

@Controller
public class DashBoardController extends BaseController {

    @Autowired
    private IDashBoardService dashBoardService;
    
    /**
     * home page.
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/home.html")
    public ModelAndView home(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ModelAndView view = new ModelAndView(getViewPath() + "/home");
        List<UserDashboard> dashboards = dashBoardService.selectMyDashboardConfig(requestContext);
        view.addObject("dashboards", dashboards);
        return view;
    }
    
    /**
     * Add dashboard.
     * 
     * @param request
     * @param userDashboards
     * @return
     */
    @RequestMapping(value = "/dashboard/add")
    @ResponseBody
    public ResponseData addMyDashboard(HttpServletRequest request, @RequestBody UserDashboard userDashboard) {
        IRequest requestContext = createRequestContext(request);
        userDashboard.setUserId(requestContext.getUserId());
        userDashboard.setDashboardSequence(0L);
        return new ResponseData(Arrays.asList(dashBoardService.insertMyDashboard(createRequestContext(request), userDashboard)));
    }
    
    /**
     * Save dashboard order.
     * 
     * @param request
     * @param userDashboards
     * @return
     */
    @RequestMapping(value = "/dashboard/update")
    @ResponseBody
    public ResponseData updateMyDashboardConfig(HttpServletRequest request, @RequestBody List<UserDashboard> userDashboards) {
        dashBoardService.updateMyDashboardConfig(createRequestContext(request), userDashboards);
        return new ResponseData();
    }
    
    /**
     * Remove a dashboard.
     * 
     * @param request
     * @param userDashboards
     * @return
     */
    @RequestMapping(value = "/dashboard/remove")
    @ResponseBody
    public ResponseData removeDashboard(HttpServletRequest request, @RequestBody UserDashboard userDashboard) {
        dashBoardService.removeDashboard(createRequestContext(request), userDashboard);
        return new ResponseData();
    }

    /**
     * DASHBOARD数据展示
     * 
     * @param request
     * @param dashBoard
     * @param page
     * @param pagesize
     * @return ResponseData
     */
    @RequestMapping(value = "/sys/dashboard/query")
    @ResponseBody
    public ResponseData query(final DashBoard dashBoard, @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) final int pagesize, final HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(dashBoardService.selectDashBoard(requestContext, dashBoard, page, pagesize));
    }

    /**
     * 删除/批量删除功能
     * 
     * @param dashBoards
     *            dashBoards
     * @param result
     *            BindingResult
     * @param request
     *            HttpServletRequest
     * @return ResponseData
     */
    @RequestMapping(value = "/sys/dashboard/remove")
    @ResponseBody
    public ResponseData remove(@RequestBody final List<DashBoard> dashBoards, final BindingResult result,
            final HttpServletRequest request) {
        dashBoardService.batchDelete(dashBoards);
        return new ResponseData();
    }

    /**
     * 批量更新功能
     * 
     * @param dashBoards
     *            dashBoards
     * @param result
     *            BindingResult
     * @param request
     *            HttpServletRequest
     * @return ResponseData
     */
    @RequestMapping(value = "/sys/dashboard/submit")
    @ResponseBody
    public ResponseData submit(@RequestBody final List<DashBoard> dashBoards, final BindingResult result,
            final HttpServletRequest request) {
        getValidator().validate(dashBoards, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(dashBoardService.batchUpdate(requestContext, dashBoards));
    }

}
