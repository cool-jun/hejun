package com.hj.app.controller.developer;

import com.hj.app.domain.DevUser;
import com.hj.app.service.developer.DevUserService;
import com.hj.app.utils.Constants;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class DevUserControl {

    @Resource
    private DevUserService devUserService;

    @RequestMapping("/login")
    public String devLogin(@Param("devCode") String devCode, @Param("devPassword") String devPassword, HttpServletRequest request, HttpSession session) {

        DevUser devUser = devUserService.login(devCode, devPassword);

        if (devUser != null) {
            session.setAttribute(Constants.DEV_USER_SESSION, devUser);
            return "developer/main";
        } else {
            //返回错误信息
            request.setAttribute("error", "用户名或密码错误!");
            return "devlogin";
        }
    }

    @RequestMapping("/toDevLogin")
    public String toLogin() {
        return "devlogin";
    }

    @RequestMapping("/paltform/main")
    public String main(HttpSession session) {
        if (session.getAttribute(Constants.DEV_USER_SESSION) == null) {
            return "redirect:/toDevLogin";
        }
        return "developer/main";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(Constants.DEV_USER_SESSION);
        return "devlogin";
    }

//    @RequestMapping("/toRegister")
//    public String toRegister() {
//        return "/devregister.jsp";
//    }

//    @RequestMapping("/toBackEndMain")
//    public String toBackEndMain() {
//        return "backend/main";
//    }

//    @RequestMapping("/checkUserName")
//    @ResponseBody
//    public String checkUserName(String devName) {
//        String userName = devUserService.checkUserName(devName);
//        if (userName == null) {
//            return "ok";
//        } else {
//            return "false";
//        }
//    }

}
