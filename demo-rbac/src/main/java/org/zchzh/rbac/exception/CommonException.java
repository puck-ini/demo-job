package org.zchzh.rbac.exception;

/**
 * @author zengchzh
 * @date 2021/9/7
 */
public class CommonException extends RuntimeException {
    public CommonException() {
    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(Throwable cause) {
        super(cause);
    }
}
