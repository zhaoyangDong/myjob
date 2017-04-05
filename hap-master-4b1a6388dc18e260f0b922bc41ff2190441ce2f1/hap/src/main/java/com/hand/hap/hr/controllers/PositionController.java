package com.hand.hap.hr.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.TokenException;
import com.hand.hap.hr.dto.Position;
import com.hand.hap.hr.service.IPositionService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;


/**
 * PositionController.
 * 
 * @author hailin.xu@hand-china.com
 */

@Controller
public class PositionController extends BaseController{
	@Autowired
	private IPositionService positionService;
	
	/**
	 * 岗位信息查询.
	 * 
	 * @param position
	 *            position
	 * @param page
	 *            起始页
	 * @param pagesize
	 *            分页大小
	 * @return ResponseData
	 */
	@RequestMapping(value = "/hr/position/query")
	@ResponseBody
	public ResponseData getPosition(HttpServletRequest request, Position position, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
		IRequest iRequest = createRequestContext(request);
		return new ResponseData(positionService.queryPosition(iRequest,position, page, pagesize));

	}

	/**
	 * 岗位信息保存.
	 * 
	 * @param positions
	 *            positions
	 * @param result
	 *            BindingResult
	 * @param request
	 *            HttpServletRequest
	 * @return ResponseData
	 * @throws TokenException
	 */
	@RequestMapping(value = "/hr/position/submit", method = RequestMethod.POST)
	public ResponseData submitPosition(@RequestBody List<Position> positions, BindingResult result, HttpServletRequest request)
			throws TokenException {
		checkToken(request, positions);
		getValidator().validate(positions, result);
		if (result.hasErrors()) {
			ResponseData rd = new ResponseData(false);
			rd.setMessage(getErrorMessage(result, request));
			return rd;
		}
		IRequest requestCtx = createRequestContext(request);
		return new ResponseData(positionService.batchUpdate(requestCtx, positions));
	}
	
	/**
	 * 岗位信息删除.
	 * 
	 * @param positions
	 *            positions
	 * @param result
	 *            BindingResult
	 * @param request
	 *            HttpServletRequest
	 * @return ResponseData
	 * @throws TokenException
	 */
	@RequestMapping(value = "/hr/position/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<Position> positions){
		positionService.batchDelete(positions);
        return new ResponseData();
    }
}
