/*
 * #{copyright}#
 */
package com.hand.hap.function.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.function.dto.RoleFunction;
import com.hand.hap.function.dto.RoleResourceItem;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.function.service.IFunctionService;
import com.hand.hap.function.service.IRoleFunctionService;
import com.hand.hap.function.service.IRoleResourceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.function.dto.MenuItem;

/**
 * 功能分配controller.
 * 
 * @author liuxiawang
 * @author njq.niu@hand-china.com
 */
@Controller
@RequestMapping(value = "/sys/rolefunction")
public class RoleFunctionController extends BaseController {

    @Autowired
    private IRoleFunctionService roleFunctionService;

    @Autowired
    private IFunctionService functionService;
    
    @Autowired
    private IRoleResourceItemService roleResourceItemService;

    /**
     * 查询所有菜单,角色拥有的菜单选项打勾.
     *
     * @param request
     *            HttpServletRequest
     * @param roleId
     *            roleId
     * @return ResponseData ResponseData
     * @throws BaseException
     *             BaseException
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData selectRoleFuntion(HttpServletRequest request, @RequestParam(required = false) Long roleId)
            throws BaseException {
        IRequest requestContext = createRequestContext(request);
        List<MenuItem> menus = functionService.selectAllMenus(requestContext);
        if (roleId != null) {
            Long[] ids = roleFunctionService.getRoleFunctionById(roleId);
            updateMenuCheck(menus, ids);

        }
        return new ResponseData(menus);
    }

    /**
     * 处理勾选状态.
     *
     * @param menus
     *            菜单
     * @param ids
     *            functionId
     */
    private void updateMenuCheck(final List<MenuItem> menus, final Long[] ids) {
        if (menus == null || ids == null) {
            return;
        }
        for (MenuItem menuItem : menus) {
            if (menuItem.getChildren() != null && !menuItem.getChildren().isEmpty()) {
                updateMenuCheck(menuItem.getChildren(), ids);
            }
            for (Long id : ids) {
                if (menuItem.getId().equals(id)) {
                    menuItem.setIschecked(Boolean.TRUE);
                }
            }
        }
    }

    /**
     * 对角色分配的功能数据进行保存.
     *
     * @param request
     *            请求上下文
     * @param records
     *            角色功能
     * @return ResponseData ResponseData
     * @throws BaseException BaseException
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData submit(HttpServletRequest request, @RequestBody List<RoleFunction> records)
            throws BaseException {
        return new ResponseData(roleFunctionService.batchUpdate(createRequestContext(request), records));
    }
    
    
    
    
    /**
     * 获取资源权限项.
     * 
     * @param request
     * @param roleId
     * @param functionId
     * @return ResponseData
     */
    @RequestMapping("/queryResourceItems")
    @ResponseBody
    public ResponseData queryResourceItems(HttpServletRequest request, @RequestParam(required = false) Long roleId,
            @RequestParam(required = false) Long functionId) {
        return new ResponseData(
                roleResourceItemService.queryRoleResourceItems(createRequestContext(request), roleId, functionId));
    }
    
    /**
     * 保存角色权限项.
     * 
     * @param request
     * @param roleResourceItems
     * @param roleId
     * @param functionId
     * @return ResponseData
     */
    @RequestMapping("/submitResourceItems")
    @ResponseBody
    public ResponseData submitResourceItems(HttpServletRequest request,
                                            @RequestBody List<RoleResourceItem> roleResourceItems, @RequestParam(required = false) Long roleId,
                                            @RequestParam(required = false) Long functionId) {
        return new ResponseData(roleResourceItemService.batchUpdate(createRequestContext(request), roleResourceItems,
                roleId, functionId));
    }

}
