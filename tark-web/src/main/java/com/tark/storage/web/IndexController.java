package com.tark.storage.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jh on 2017/5/2.
 */

@Controller
public class IndexController {
    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "hello tark storage";
    }

}
