package com.tark.storage.common.web;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by jh on 2017/5/3.
 */
public class BaseController {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    /**
     * spring ModelAttribute
     * 放置在方法上面：表示请求该类的每个Action前都会首先执行它，也可以将一些准备数据的操作放置在该方法里面
     */
    @ModelAttribute
    public void setBaseController(HttpServletRequest request,HttpServletResponse response){
        this.request=request;
        this.response=response;
        this.session=request.getSession();
    }
}
