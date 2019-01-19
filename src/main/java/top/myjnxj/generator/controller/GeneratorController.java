package top.myjnxj.generator.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ResponseHeader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.myjnxj.generator.bo.Generator;
import top.myjnxj.generator.entity.Table;
import top.myjnxj.generator.service.GeneratorService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static top.myjnxj.generator.util.DAOUtils.queryTableAndTableColumns;

/**
 * @ClassName GeneratorController
 * @Description TODO
 * @Author JXNJ
 * @Date 2019/1/16 15:16
 * @Version 1.0
 **/
@Controller
@Slf4j
@RequestMapping("/api")
public class GeneratorController {
    @Autowired
    GeneratorService generatorService;

    @ApiOperation(value = "生成代码")
    @GetMapping(value = "/generator")
    @ResponseHeader(name = "wtf")
    public void generator( final HttpServletResponse response ,@ApiParam("生成的参数") @RequestBody(required = false) Generator generator) throws IOException {

        log.info("generator:{}",generator.toString());
        byte[] result=generatorService.generator(generator);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"springboot.rar\"");
        response.addHeader("Content-Length", "" + result.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(result, response.getOutputStream());

    }
}

