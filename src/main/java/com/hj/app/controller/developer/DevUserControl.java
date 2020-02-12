package com.hj.app.controller.developer;

import com.hj.app.domain.DevUser;
import com.hj.app.service.developer.DevUserService;
import com.hj.app.utils.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class DevUserControl {
    @Resource
    private DevUserService devUserService;

    @RequestMapping("/devlogin")
    public String devLogin(@RequestParam String devCode, @RequestParam String devPassword,
                           HttpServletRequest request, HttpSession session) {
        DevUser devUser = devUserService.login(devCode, devPassword);
        if (devUser != null) {
            //开发者信息存入session
            session.setAttribute(Constants.DEV_USER_SESSION, devUser);
            //页面跳转(main.jsp)
            return "developer/main";
        } else {
            //返回错误信息
            request.setAttribute("error", "用户名或密码错误!");
            return "devlogin";
        }
    }

    @RequestMapping("/todevlogin")
    public String toDevLogin() {
        return "devlogin";
    }

    @RequestMapping("/platform/main")
    public String main(HttpSession session) {
        if (session.getAttribute(Constants.DEV_USER_SESSION) == null) {
            return "redirect:/todevlogin";
        }
        return "developer/main";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        //清除session信息
        session.removeAttribute(Constants.DEV_USER_SESSION);
        return "devlogin";
    }
}
