package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常统一处理类
 */
@RestControllerAdvice
public class ExceptionCatch {
    //记录日志
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);
    //定义非自定义异常的异常码(谷歌的map 只读不能更改 线程安全)
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCETIONS;
    //用来构建ImmutableMap
    protected static ImmutableMap.Builder<Class<? extends Throwable>,ResultCode> builder = ImmutableMap.builder();
    //用来添加未知的异常码
    static {
        //请求类型为@RequestBody 错误时触发
        builder.put(HttpMessageNotReadableException.class,CommonCode.INVALID_PARAM);
    }
    //处理自定义的异常类型
    @ExceptionHandler(CustomException.class)
    public ResponseResult customException(CustomException customException){
        LOGGER.error("catch exception:{}",customException.getMessage());
        return new ResponseResult(customException.getResultCode());
    }
    //处理无法预知的异常类型（由框架所抛出的）
    @ExceptionHandler(Exception.class)
    public ResponseResult exception( Exception exception){
        LOGGER.error("catch exception:{}",exception.getMessage());
        //如果未知错误集合为空的，给其赋值
        if(EXCETIONS==null) EXCETIONS=builder.build();
        //如果集合存在非自定义类型，则返回对应的错误代码 否则返回99999
        if(EXCETIONS.containsKey(exception.getClass()))
            return new ResponseResult(EXCETIONS.get(exception));
        return new ResponseResult(CommonCode.SERVER_ERROR);
    }
}
