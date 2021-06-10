package com.swjt.xingzishop.Exception;

import com.swjt.xingzishop.Utils.R;
import com.swjt.xingzishop.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * xingzishop
 * User控制器抛出的错误捕捉
 *
 * @author : wpf
 * @date : 2021-06-09 13:41
 **/
@Slf4j
@RestControllerAdvice(basePackages = "com.swjt.xingzishop.Api")
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleVaildException(MethodArgumentNotValidException e){
        log.error("数据校验出现问题{}，异常类型{}",e.getMessage(),e.getClass());
        BindingResult bindingResult = e.getBindingResult();
        Map<String,String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach(
                fieldError ->{
                    errorMap.put(fieldError.getField(),fieldError.getDefaultMessage());
                }
        );
        return R.error(ResultCode.PARAM_TYPE_ERROR.getCode(),ResultCode.PARAM_TYPE_ERROR.getMessage()).put("data",errorMap);

    }
    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable e){
        log.error("错误  ",e);
        return R.error(ResultCode.COMMON_FAIL.getCode(),ResultCode.COMMON_FAIL.getMessage());
    }
}
