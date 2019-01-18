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
import top.myjnxj.generator.service.GeneratorService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    public void generator( final HttpServletResponse response/*@ApiParam("生成的参数") @RequestBody Generator generator*/) throws IOException {
        Generator generator=new Generator();
        generator.setPackageName("top.myjnxj.wechat");
        generator.setUrl("jdbc:mysql://127.0.0.1:3306/wechat?useUnicode=true&characterEncoding=utf-8&userSSL=false&serverTimezone=GMT%2B8");
        generator.setUserName("root");
        generator.setPassword("Jun6311158");
        log.info("generator:{}",generator.toString());
        byte[] result=generatorService.generator(generator);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"springboot.rar\"");
        response.addHeader("Content-Length", "" + result.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(result, response.getOutputStream());

    }
}

