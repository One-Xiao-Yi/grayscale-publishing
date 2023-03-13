package com.xiao.yi.control.aop;

import com.xiao.yi.control.model.reponse.ResponseModel;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author xiaoyi
 * @since 2023/3/10
 */
@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    public ResponseModel<Void> exceptionHandle(Exception e) {
        return ResponseModel.error(e);
    }

}
