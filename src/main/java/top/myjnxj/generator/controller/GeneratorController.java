package top.myjnxj.generator.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ResponseHeader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import top.myjnxj.generator.bo.Generator;
import top.myjnxj.generator.common.enums.ResultEnum;
import top.myjnxj.generator.common.util.ResultUtils;
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
@CrossOrigin
public class GeneratorController {
    @Autowired
    GeneratorService generatorService;

    @ApiOperation(value = "生成代码")
    @PostMapping(value = "/generator")
    @ResponseHeader(name = "wtf")
    public void generator(final HttpServletResponse response, @ApiParam("生成的参数") @RequestBody(required = false) Generator generator) throws IOException {

        log.info("generator:{}", generator.toString());
        byte[] result = new byte[0];
        boolean hasException = false;
        try {
            result = generatorService.generator(generator);
        } catch (Exception e) {
            hasException = true;
        }
        if (hasException) {
            log.info("go in");
            response.reset();
            response.setContentType("application/json; charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            IOUtils.write(JSONObject.toJSONString(ResultUtils.error(ResultEnum.DATABASE_ERROR)), response.getOutputStream());

        } else {
            log.info("go out");
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\"springboot.rar\"");
            response.addHeader("Content-Length", "" + result.length);
            response.setContentType("application/octet-stream; charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            IOUtils.write(result, response.getOutputStream());
        }


    }
}

