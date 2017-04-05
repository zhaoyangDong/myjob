package com.hand.hap.generator.controllers;

import com.hand.hap.generator.service.IHapGeneratorService;
import com.hand.hap.generator.dto.GeneratorInfo;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jialong.zuo@hand-china.com on 2016/10/24.
 */
@Controller
@RequestMapping(value = "/generator")
public class HapGeneratorController extends BaseController {
    @Autowired
    IHapGeneratorService service;

    @RequestMapping(value = "/alltables", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData showTables() {
        return new ResponseData(service.showTables());
    }

    @RequestMapping(value = "/newtables")
    @ResponseBody
    public int generatorTables(GeneratorInfo generatorInfo) {

        /*
         * GeneratorInfo info=new GeneratorInfo();
         * info.setProjectPath("D:/JetBrains/workspaces/hap-parent/hap");
         * info.setParentPackagePath("com/hand/hap"); info.setPackagePath("tt");
         * info.setTargetName("sys_file");
         * 
         * info.setControllerName("FileController.java");
         * info.setDtoName("File.java");
         * info.setImplName("FileServiceImpl.java");
         * info.setMapperName("FileMapper.java");
         * info.setMapperXmlName("FileMapper.xml");
         * info.setServiceName("IFileService.java");
         * info.setHtmlName("file.html");
         * info.setHtmlModelName("htmlemptymodel.ftl");
         * info.setHtmlStatus("Create");
         * 
         * info.setControllerStatus("Create"); info.setDtoStatus("Create");
         * info.setImplStatus("Create"); info.setMapperStatus("Create");
         * info.setMapperXmlStatus("NotOperation");
         * info.setServiceStatus("Create"); info.setHtmlStatus("Create");
         */

        int rs = service.generatorFile(generatorInfo);
        return rs;
    }

}
