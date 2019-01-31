package top.myjnxj.generator.common.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.myjnxj.generator.common.util.ResultUtils;
import top.myjnxj.generator.common.vo.Result;

import java.util.List;

/**
 * @author 江南小俊
 * @create 2018-06-17 17:37
 * @desc 控制器增强类-异常统一处理
 **/
@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<String> handler(Exception e) {

        if (e instanceof ResultException) {
            return ResultUtils.error(((ResultException) e).getCode(),e.getMessage());
        }
        else if(e instanceof MethodArgumentNotValidException){
            //处理返回的错误信息
            StringBuffer errorMsg = new StringBuffer();
            MethodArgumentNotValidException c = (MethodArgumentNotValidException) e;
            List<ObjectError> errors = c.getBindingResult().getAllErrors();
            for (ObjectError error : errors) {
                errorMsg.append(error.getDefaultMessage()).append(";");
            }
            return ResultUtils.error(errorMsg.toString());
        }
        else {

            log.info(e.getClass().toString());
            log.info("系统错误");

            return ResultUtils.error(e.getMessage());
        }

    }
}
