package org.zchzh.file.aop.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.zchzh.file.dto.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.zchzh.file.exception.CommonException;

/**
 * @author zengchzh
 * @date 2021/5/14
 */
@Slf4j
@RestControllerAdvice(basePackages = {"org.zchzh.filemanager.controller"})
public class ResponseControllerAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return !(methodParameter.getParameterType().equals(ResultDTO.class));
//                || methodParameter.hasMethodAnnotation(RemoveWrapper.class));
    }

    @Override
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        // String类型不能直接包装，所以要进行些特别的处理
        if (methodParameter.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // 将数据包装在dto里后，再转换为json字符串返回
                return objectMapper.writeValueAsString(new ResultDTO<>(o));
            } catch (JsonProcessingException e) {
                log.error(e.getMessage());
                throw new CommonException("结果封装失败");
            }
        }
        return new ResultDTO<>(o);
    }
}
