package com.tark.storage.exception;

import com.tark.storage.common.web.core.ResponseMessage;
import com.tark.storage.common.web.core.exception.BusinessException;
import com.tark.storage.common.utils.WebUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;


/**
 * Created by jh on 2017/5/3.
 */
@ControllerAdvice(annotations = Controller.class)
public class ControllerExceptionTranslator {
    @ExceptionHandler(BusinessException.class)
    ModelAndView handleExceptionView(BusinessException exception, HttpServletResponse response) {
        response.setStatus(exception.getStatus());
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addAllObjects(ResponseMessage.error(exception.getMessage(), exception.getStatus()).toMap());
        modelAndView.addObject("exception", exception);
        modelAndView.addObject("absPath", WebUtil.getAbsPath(WebUtil.getHttpServletRequest()));
        return modelAndView;
    }

    @ExceptionHandler(Throwable.class)
    ModelAndView handleExceptionView(Throwable exception, HttpServletResponse response) {
        response.setStatus(500);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addAllObjects(ResponseMessage.error(exception.getMessage(), 500).toMap());
        modelAndView.addObject("exception", exception);
        modelAndView.addObject("absPath", WebUtil.getAbsPath(WebUtil.getHttpServletRequest()));
        return modelAndView;
    }
}
