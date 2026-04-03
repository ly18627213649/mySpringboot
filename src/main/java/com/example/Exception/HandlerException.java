package com.example.Exception;

import com.example.proto.ResponseResult;
import com.example.proto.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 * @author ly
 * @since 2019/7/15 16:57
 */
@RestControllerAdvice
public class HandlerException {
    private final Logger log = LoggerFactory.getLogger(HandlerException.class);
    /**
     * 1.处理参数验证异常(@Validated)
     * @param e
     * @return ResponseResult
     */
    @ExceptionHandler({BindException.class})
    public ResponseResult MethodArgumentNotValidExceptionHandler(BindException e) {
        // 从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 捕获到参数验证异常, 统一返回结果集
        return ResponseResult.success(ResultCode.ERROR_VALIDATE,objectError.getDefaultMessage());
    }

    /**
     * 2.处理自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseResult ServiceExceptionHandler(ServiceException e) {
        // 打印日志
        log.error(e.getMessage(), e);
        // 返回结果集
        return new ResponseResult(e.getCode(), e.getMsg(), e.getMessage());
    }
}
