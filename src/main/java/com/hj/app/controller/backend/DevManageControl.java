package com.hj.app.controller.backend;

import com.hj.app.domain.DevUser;
import com.hj.app.service.backend.DevService;
import com.hj.app.utils.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class DevManageControl {
    @Resource
    private DevService devService;

    @RequestMapping("/listalldev")
    public String getAllDevInfo(Model model,
                                @RequestParam(value = "queryCode", required = false) String queryCode,
                                @RequestParam(value = "queryName", required = false) String queryName,
                                @RequestParam(value = "pageIndex", required = false) String pageIndex) {

        List<DevUser> devUserList;

        int pageSize = 3;
        int currentPageNo = 1;

        if (pageIndex != null) {
            currentPageNo = Integer.parseInt(pageIndex);
        }

        int totalCount;
        totalCount = devService.getDevInfoCount(queryCode, queryName);

        //总页数
        Page pages = new Page();
        pages.setCurrentPageNo(currentPageNo);
        pages.setPageSize(pageSize);
        pages.setTotalCount(totalCount);

        int totalPageCount = pages.getTotalPageCount();
        if (currentPageNo < 1) {
            currentPageNo = 1;
        } else if (currentPageNo > totalPageCount) {
            currentPageNo = totalPageCount;
        }

        devUserList = devService.getDevInfoList(queryCode, queryName, currentPageNo, pageSize);

        model.addAttribute("devInfoList", devUserList);
        model.addAttribute("pages", pages);

        return "backend/devmanage";
    }

    @RequestMapping("deleteDev")
    public String deleteDevUser(@RequestParam("id") int id) {
        devService.deleteDevById(id);
        return "backend/main";
    }

    @RequestMapping("/modifyDev")
    public String getModifyDevUser(@RequestParam("id") int id, Model model) {
        DevUser devUser = devService.getDevUserById(id);
        model.addAttribute("dev", devUser);
        return "backend/devmodify";
    }

    @RequestMapping("/devinfomodifysave")
    public String ModifyDevUser(@RequestParam(value = "id", required = false) int id,
                                @RequestParam(value = "devName", required = false) String devName,
                                @RequestParam(value = "devCode", required = false) String devCode,
                                @RequestParam(value = "devEmail", required = false) String devEmail,
                                @RequestParam(value = "devInfo", required = false) String devInfo) {
        devService.modifyDevUser(id, devCode, devName, devEmail, devInfo);
        return "backend/main";
    }
}
