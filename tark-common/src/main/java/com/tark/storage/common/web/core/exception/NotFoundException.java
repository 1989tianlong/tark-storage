package com.tark.storage.common.web.core.exception;

/**
 * Created by jh on 2017/5/3.
 */
public class NotFoundException extends BusinessException {
    public NotFoundException(String message) {
        super(message, "404");
    }
}
