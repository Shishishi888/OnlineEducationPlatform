package com.tjulab.servicebase.exceptionhandler;

import com.tjulab.commonutils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 全局（统一）异常处理
     */
    @ExceptionHandler(Exception.class)  // Exception.class 所有异常都会执行
    @ResponseBody  // 为了返回数据
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("执行全局异常处理");
    }

    /**
     * 特定异常处理
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody  // 为了返回数据
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("执行ArithmeticException异常处理");
    }

    /**
     * 自定义异常处理
     */
    @ExceptionHandler(MyException.class)
    @ResponseBody  // 为了返回数据
    public R error(MyException e) {
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
