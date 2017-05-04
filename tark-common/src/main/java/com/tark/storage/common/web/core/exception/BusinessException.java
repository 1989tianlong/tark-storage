package com.tark.storage.common.web.core.exception;

/**
 * 业务异常，用于抛出给前端提示错误信息
 * Created by jh on 2017/5/3.
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -1114219045834565634L;

    private int status = 400;

    public BusinessException(String message) {
        this(message, 400);
    }

    public BusinessException(String message, int status) {
        super(message);
        this.status = status;
    }

    public BusinessException(String message, Throwable cause, int status) {
        super(message, cause);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
