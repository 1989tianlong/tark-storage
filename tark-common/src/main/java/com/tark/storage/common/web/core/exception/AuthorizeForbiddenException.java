package com.tark.storage.common.web.core.exception;

/**
 * Created by jh on 2017/5/3.
 */
public class AuthorizeForbiddenException extends BusinessException {

    private static final long serialVersionUID = 3572963710301459860L;

    public AuthorizeForbiddenException(String message) {
        this(message, "403");
    }

    public AuthorizeForbiddenException(String message, String status) {
        super(message, status);
    }

    public AuthorizeForbiddenException(String message, Throwable cause, String status) {
        super(message, cause, status);
    }
}
