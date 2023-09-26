package com.lyr.qs.config;

import com.lyr.qs.constant.Constant;
import com.lyr.qs.exception.CustomException;
import com.lyr.qs.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.concurrent.TimeoutException;

/**
 * 全局异常捕捉处理
 * @author yurong333
 * @since 2023-01-10
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(CustomException.class)
    public R handleMpException(CustomException e) {
        return new R(Constant.FAIL, e.toString());
    }

    /**
     * 这个是为了抓取意想不到的异常，因为我们是无法抓取到全部的异常的
     */
    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        return new R(Constant.FAIL, e.toString());
    }

    @ExceptionHandler(RuntimeException.class)
    public R runtimeException(RuntimeException e) {
        return new R(Constant.FAIL, e.toString());
    }

    @ExceptionHandler(TimeoutException.class)
    public R timeoutException(TimeoutException e) {
        return new R(Constant.FAIL, e.toString());
    }

}

