package org.zchzh.filemanager.aop.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zchzh.filemanager.dto.ResultDTO;
import org.zchzh.filemanager.exception.CommonException;

import java.util.Objects;

/**
 * @author zengchzh
 * @date 2021/5/14
 */

@Slf4j
@RestControllerAdvice(basePackages = {"org.zchzh.filemanager.controller"})
public class ExceptionAdvice {


    @ExceptionHandler(CommonException.class)
    public ResultDTO<String> commonExceptionHandler(CommonException e) {
        return ResultDTO.error(e.getMessage());
    }
    /**
     * 获取 Validator 校验异常 转换成自定义异常，处理被 @RequestBody 标识的类参数
     * @param e MethodArgumentNotValidException 异常
     * @return 返回 dto
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultDTO<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return ResultDTO.error(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }


    /**
     * 处理不被 @RequestBody 标识的类参数
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public ResultDTO<String> bindExceptionHandler(BindException e) {
        return ResultDTO.error(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    /**
     * 处理非 java bean
     * @param e
     * @return
     */
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResultDTO<String> constraintViolationExceptionHandler(ConstraintViolationException e) {
//        return ResultDTO.error(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
//    }
}
