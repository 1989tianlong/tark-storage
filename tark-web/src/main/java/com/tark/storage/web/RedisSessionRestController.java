package com.tark.storage.web;

import com.tark.storage.common.web.core.ResponseMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by jh on 2017/5/4.
 */
@RestController
public class RedisSessionRestController {
    @GetMapping("/uid")
    public ResponseMessage uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");

        if (uid == null) {
            uid = UUID.randomUUID();
        }

        session.setAttribute("uid", uid);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uid", uid);
        return ResponseMessage.ok( map);
    }
}
