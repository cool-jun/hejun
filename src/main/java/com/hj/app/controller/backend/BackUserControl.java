package com.hj.app.controller.backend;

import com.hj.app.domain.BackendUser;
import com.hj.app.service.backend.BackendUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class BackUserControl {
    @Resource
    private BackendUserService backendUserService;

    @RequestMapping("/tobackendlogin")
    public String toBackUser() {
        return "backendlogin";
    }

    @RequestMapping("/backenduserlogin")
    public String backUserLogin(String userCode, String userPassword,
                                HttpServletRequest request, HttpSession session) {
        BackendUser backendUser = backendUserService.getBackendUser(userCode);
        if (backendUser.getUserPassword().equals(userPassword)) {
            session.setAttribute("userSession", backendUser);
            return "backend/main";
        }
        request.setAttribute("error", "你输入的用户名或密码错误！");
        return "backendlogin";
    }

}
