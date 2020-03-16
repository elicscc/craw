package com.example.craw.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.craw.service.ex.EmptyException;

/**
 * 所用控制器类的父类
 */
public abstract class BaseController {

    protected static final Integer SUCCESS = 20;
    // 静态常量的命名：所有字母都大写，单词用_隔开，尽量说明白，不要嫌长
    protected static final Integer ERROR_USERNAME_DUPLICATE = 404;

    /**
     * 对控制器中的异常进行统一处理
     *
     * @param e 异常对象
     * @return JsonResult封装响应信息
     */
    @ExceptionHandler({EmptyException.class, IOException.class, RuntimeException.class})
    @ResponseBody
    public JsonResult handlerException(Throwable e) {
        // 根据不同异常的类型提供不同的处理方式
        // 现在的处理方式是根据不同的类型，返回不同的状态码
        JsonResult jr = new JsonResult();
        jr.setMessage(e.getMessage());

//        if (e instanceof EmptyException) {
//            jr.setStatus(ERROR_USERNAME_DUPLICATE);
//        } else if (e instanceof UserNotFoundException) {
//            jr.setStatus(31);
//        } else if
//        (e instanceof PasswordNotMatchException) {
//            jr.setStatus(32);
//        } else if (e
//                instanceof AddressCountLimitException) {
//            jr.setStatus(33);
//        } else if (e
//                instanceof AccessDeniedException) {
//            jr.setStatus(34);
//        } else if (e instanceof
//                ProductNotFoundException) {
//            jr.setStatus(35);
//        } else if (e instanceof
//                AddressNotFoundException) {
//            jr.setStatus(36);
//        } else if (e instanceof
//                CartNotFoundException) {
//            jr.setStatus(37);
//        } else if (e instanceof
//                OrderNotFoundException) {
//            jr.setStatus(38);
//        } else if (e instanceof
//                ProductOutOfStockException) {
//            jr.setStatus(39);
//        } else if (e instanceof
//                InsertException) {
//            jr.setStatus(40);
//        } else if (e instanceof UpdateException) {
//            jr.setStatus(41);
//        } else if (e instanceof FileEmptyException) {
//            jr.setStatus(50);
//        } else if (e instanceof FileSizeException) {
//            jr.setStatus(51);
//        } else if (e instanceof FileTypeException) {
//            jr.setStatus(52);
//        } else if (e instanceof FileStateException) {
//            jr.setStatus(53);
//        } else if (e instanceof FileIOException) {
//            jr.setStatus(54);
//        }

        return jr;
    }

}
