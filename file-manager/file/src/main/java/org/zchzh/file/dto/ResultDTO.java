package org.zchzh.file.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zengchzh
 * @date 2021/5/14
 */
@Data
public class ResultDTO<T> implements Serializable {

    private String code;

    private String msg;

    private T data;

    public ResultDTO(T data) {
        this("00000","success", data);
    }

    public ResultDTO(String code, String msg, T data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }


    public static <T> ResultDTO<T> success() {
        return new ResultDTO<>(null);
    }

    public static <T> ResultDTO<T> error(T t) {
        return new ResultDTO<>("99999", "error", t);
    }

}
