package com.tark.storage.common.web.core.exception;

/**
 * Created by jh on 2017/5/3.
 */
public class AuthorizeException extends BusinessException {

    private static final long serialVersionUID = 4617997445985132549L;

    public AuthorizeException(String message) {
        this(message, "401");
    }

    public AuthorizeException(String message, String status) {
        super(message, status);
    }

    public AuthorizeException(String message, Throwable cause, String status) {
        super(message, cause, status);
    }
}
