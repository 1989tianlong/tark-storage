package com.tark.storage.common.web.core.exception;

import com.tark.storage.common.web.core.ResponseMessage;

/**
 * Created by jh on 2017/5/3.
 */
public interface ExceptionHandler {
    <T extends Throwable> boolean support(Class<T> e);
    ResponseMessage handle(Throwable e);
}
