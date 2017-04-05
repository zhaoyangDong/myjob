package com.hand.hap.intergration.controllers;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.intergration.dto.HapInterfaceLine;
import com.hand.hap.intergration.mapper.HapInterfaceLineMapper;
import com.hand.hap.intergration.service.IHapInterfaceLineService;
import com.hand.hap.cache.impl.ApiConfigCache;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * @author jiguang.sun@hand-china.com
 * @version 2016/7/26.
 */
@Controller
@RequestMapping("/sys/api")
public class HapInterfaceLineController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(HapInterfaceLineController.class);

    @Autowired
    private IHapInterfaceLineService lineService;

    @Autowired
    private ApiConfigCache apiCache;

    @Autowired
    private HapInterfaceLineMapper hmsLineMapper;


    /*
    * get line and lineTl by lineId and language
    * */
    @RequestMapping(value = "/queryLine", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getLineList(HttpServletRequest request, @ModelAttribute HapInterfaceLine lineAndLineTlDTO) {
        logger.info("query line by LineAndLineTlDTO  lineId:{}", lineAndLineTlDTO.getLineId());
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(lineService.getLineAndLineTl(iRequest,lineAndLineTlDTO));
    }


    /*
    * 新增一个接口
    * */
    @RequestMapping(value = "/insertLine", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData insertLine(HttpServletRequest request, @RequestBody List<HapInterfaceLine> hmsInterfaceLines) {
       // logger.info("add line by LineAndLineTlDTO  headerId:{}", hmsInterfaceLine.getHeaderId());
        IRequest iRequest = createRequestContext(request);
        for(HapInterfaceLine hmsInterfaceLine : hmsInterfaceLines) {
            hmsInterfaceLine.setLineId(UUID.randomUUID().toString());
            hmsInterfaceLine.setLineDescription(hmsInterfaceLine.getLineName());
            int result = lineService.insertLine(iRequest, hmsInterfaceLine);
            if (result <= 0) {
                return new ResponseData(false);
            }
        }
        return new ResponseData(hmsInterfaceLines);

    }


    /*
    * 更新
    * */
    @RequestMapping(value = "/updateLine", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData updateLine(HttpServletRequest request,  @RequestBody List<HapInterfaceLine> hmsInterfaceLines) {
        //logger.info("update line by hmsInterfaceLine  lineId:{}", hmsInterfaceLine.getLineId());


        IRequest iRequest = createRequestContext(request);
        for(HapInterfaceLine hmsInterfaceLine : hmsInterfaceLines) {
            hmsInterfaceLine.setLineDescription(hmsInterfaceLine.getLineName());
            int result = lineService.updateLine(iRequest, hmsInterfaceLine);
            if (result <= 0) {
                return new ResponseData(false);
            }
        }
        return new ResponseData(hmsInterfaceLines);
    }

    /*
   * 删除
   * */
    @RequestMapping(value = "/deleteLine", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteLine(HttpServletRequest request, @RequestBody List<HapInterfaceLine> interfaceLines) {
        logger.info("delete line by hmsInterfaceLine  size:", interfaceLines.size());
        lineService.batchDelete(interfaceLines);
        return  new ResponseData();
    }

    /*
    * 根据headerId获取lines
    * */
    @RequestMapping(value = "/getLinesByHeaderId", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getLinesByHeaderId(HttpServletRequest request,  HapInterfaceLine lineAndLineTlDTO,
                                           @RequestParam(defaultValue = DEFAULT_PAGE) final int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) final int pagesize) {

        IRequest iRequest = createRequestContext(request);

        return new ResponseData(lineService.getLinesByHeaderId(iRequest,lineAndLineTlDTO,page,pagesize));
    }


}
