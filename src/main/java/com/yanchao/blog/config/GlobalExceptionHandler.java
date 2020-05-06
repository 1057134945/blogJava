package com.yanchao.blog.config;

import com.yanchao.blog.vo.ResponseDataVO;
import com.yanchao.blog.vo.ResponseVO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常拦截
 * 
 * @author: 王彦超[wang_yc@suixingpay.com]
 * @date: Apr 29, 2020 4:19:54 PM
 * @version: V1.0
 * @review: 王彦超[wang_yc@suixingpay.com]/Apr 29, 2020 4:19:54 PM
 */
@ResponseBody
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResultException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseVO<ResponseDataVO> handleResultException() {
        return ResponseVO.error();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseVO<ResponseDataVO> handleException() {
        return ResponseVO.error();
    }
}
