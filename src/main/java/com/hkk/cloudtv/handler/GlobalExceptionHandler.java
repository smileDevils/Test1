package com.hkk.cloudtv.handler;

import com.hkk.cloudtv.core.utils.ResponseUtil;
import org.apache.shiro.session.ExpiredSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.function.ServerResponse;

import java.io.FileNotFoundException;

/**
 * <p>
 *     Title: GlobalExceptionHandler.java
 * </p>
 *
 * <p>
 *     Description: 全局异常处理类, 增强的 Controller
 *     全局异常处理
 *     全局数据绑定
*      全局数据预处理
 * </p>
 *
 * <author>
 *     Hkk
 * </author>
 */
@ControllerAdvice
@Order
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public Object badArgumentHandler(IllegalArgumentException e){
        logger.error(e.getMessage(),e);
        return ResponseUtil.badArgumentValue();
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Object badArgumentHandler(NullPointerException e){
        logger.error(e.getMessage(),e);
        return ResponseUtil.nullPointException();
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Object ArithmeticException(ArithmeticException e){
        logger.error(e.getMessage(), e);
        return ResponseUtil.arithmeticException();
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object seriousHandler(Exception e) {
        logger.error(e.getMessage(), e);
        return ResponseUtil.serious();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        logger.error(e.getMessage(), e);
        return ResponseUtil.httpRequestMethodNotSupportedException();
    }

    @ExceptionHandler(FileNotFoundException.class)
    public Object fileNotFoundException(FileNotFoundException e){
        logger.error(e.getMessage());
        return ResponseUtil.fileNotFoundException();
    }

    @ResponseBody
    @ExceptionHandler(value = ExpiredSessionException.class)
    public Object handleExpiredSessionException(ExpiredSessionException e) {
        logger.debug("ExpiredSessionException", e);
        return ResponseUtil.expired();
    }

   //NumberFormatException

}
