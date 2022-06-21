package com.tjulab.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 自定义异常类
 */
public class MyException extends RuntimeException{
    private Integer code;  // 异常状态码
    private String msg;    // 异常信息
}
