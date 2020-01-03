package com.hj.app.controller.backend;

import com.hj.app.domain.*;
import com.hj.app.service.backend.AppService;
import com.hj.app.service.developer.AppCategoryService;
import com.hj.app.service.developer.AppInfoService;
import com.hj.app.service.developer.AppVersionService;
import com.hj.app.service.developer.DataDictionaryService;
import com.hj.app.utils.Constants;
import com.hj.app.utils.PageSupport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AppCheckControl {
    @Resource
    private AppService appService;
    @Resource
    private AppCategoryService appCategoryService;
    @Resource
    private AppVersionService appVersionService;
    @Resource
    private DataDictionaryService dataDictionaryService;

    @RequestMapping("applist")
    public String getAllAppInfo(Model model,
                                @RequestParam(value = "querySoftwareName", required = false) String querySoftwareName,
                                @RequestParam(value = "queryStatus", required = false) String _queryStatus,
                                @RequestParam(value = "queryPlatformId", required = false) String _queryPlatformId,
                                @RequestParam(value = "queryCategoryLevel1", required = false) String _queryCategoryLevel1,
                                @RequestParam(value = "queryCategoryLevel2", required = false) String _queryCategoryLevel2,
                                @RequestParam(value = "queryCategoryLevel3", required = false) String _queryCategoryLevel3,
                                @RequestParam(value = "pageIndex", required = false) String pageIndex) {

        List<AppInfo> appInfoList;
        List<DataDictionary> statusList;
        List<DataDictionary> platFormList;

        //列出一级分类列表，二级和三级分类列表可通过异步ajax获取
        List<AppCategory> categoryLevel1List;
        List<AppCategory> categoryLevel2List;
        List<AppCategory> categoryLevel3List;

        int pageSize = Constants.pageSize;
        int currentPageNo = 1;

        if (pageIndex != null) {
            currentPageNo = Integer.parseInt(pageIndex);
        }

        Integer queryStatus = null;
        if (_queryStatus != null && !_queryStatus.equals("")) {
            queryStatus = Integer.parseInt(_queryStatus);
        }

        Integer queryCategoryLevel1 = null;
        if(_queryCategoryLevel1 != null && !_queryCategoryLevel1.equals("")){
            queryCategoryLevel1 = Integer.parseInt(_queryCategoryLevel1);
        }

        Integer queryCategoryLevel2 = null;
        if(_queryCategoryLevel2 != null && !_queryCategoryLevel2.equals("")){
            queryCategoryLevel2 = Integer.parseInt(_queryCategoryLevel2);
        }

        Integer queryCategoryLevel3 = null;
        if(_queryCategoryLevel3 != null && !_queryCategoryLevel3.equals("")){
            queryCategoryLevel3 = Integer.parseInt(_queryCategoryLevel3);
        }

        Integer queryPlatformId = null;
        if(_queryPlatformId != null && !_queryPlatformId.equals("")){
            queryPlatformId = Integer.parseInt(_queryPlatformId);
        }

        int totalCount;
        totalCount = appService.getAppInfoCount(querySoftwareName, queryCategoryLevel1, queryCategoryLevel2,
                queryCategoryLevel3, queryPlatformId);

        //总页数
        PageSupport pages = new PageSupport();
        pages.setCurrentPageNo(currentPageNo);
        pages.setPageSize(pageSize);
        pages.setTotalCount(totalCount);

        int totalPageCount = pages.getTotalPageCount();
        if (currentPageNo < 1) {
            currentPageNo = 1;
        } else if (currentPageNo > totalPageCount) {
            currentPageNo = totalPageCount;
        }

        appInfoList = appService.getAppInfoList(querySoftwareName, queryCategoryLevel1,
                queryCategoryLevel2, queryCategoryLevel3, queryPlatformId, currentPageNo, pageSize);

        statusList = this.getDataDictionaryList("APP_STATUS");
        platFormList = this.getDataDictionaryList("APP_PLATFORM");
        categoryLevel1List = appCategoryService.getAppCategoryListByParentId(null);

        model.addAttribute("appInfoList", appInfoList);
        model.addAttribute("statusList", statusList);
        model.addAttribute("platFormList", platFormList);
        model.addAttribute("categoryLevel1List", categoryLevel1List);
        model.addAttribute("pages", pages);
        model.addAttribute("queryStatus", queryStatus);
        model.addAttribute("querySoftwareName", querySoftwareName);
        model.addAttribute("queryCategoryLevel1", queryCategoryLevel1);
        model.addAttribute("queryCategoryLevel2", queryCategoryLevel2);
        model.addAttribute("queryCategoryLevel3", queryCategoryLevel3);
        model.addAttribute("queryPlatformId", queryPlatformId);

        //二级分类列表和三级分类列表回显
        if (queryCategoryLevel2 != null && !queryCategoryLevel2.equals("")) {
            categoryLevel2List= getCategoryList(queryCategoryLevel1.toString());
            model.addAttribute("categoryLevel2List", categoryLevel2List);
        }
        if (queryCategoryLevel3 != null && !queryCategoryLevel3.equals("")) {
            categoryLevel3List= getCategoryList(queryCategoryLevel2.toString());
            model.addAttribute("categoryLevel3List", categoryLevel3List);
        }
        return "backend/applist";
    }

    public List<DataDictionary> getDataDictionaryList(String typeCode) {
        List<DataDictionary> dataDictionaryList;
        dataDictionaryList = dataDictionaryService.getDataDictionaryList(typeCode);
        return dataDictionaryList;
    }

    public List<AppCategory> getCategoryList (String pid) {
        List<AppCategory> categoryLevelList;
        categoryLevelList = appCategoryService.getAppCategoryListByParentId(pid==null?null:Integer.parseInt(pid));
        return categoryLevelList;
    }

    /**
     * 跳转到APP信息审核页面
     */
    @RequestMapping(value="/check",method= RequestMethod.GET)
    public String check(@RequestParam(value="aid",required=false) String appId,
                        @RequestParam(value="vid",required=false) String versionId,
                        Model model){
        AppInfo appInfo = null;
        AppVersion appVersion = null;

        try {
            appInfo = appService.getAppInfo(Integer.parseInt(appId));
            appVersion = appVersionService.getAppVersionById(Integer.parseInt(versionId));
        }catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute(appVersion);
        model.addAttribute(appInfo);

        return "backend/appcheck";
    }


    @RequestMapping(value="/checksave",method=RequestMethod.POST)
    public String checkSave(AppInfo appInfo){
        try {
            if(appService.updateStatus(appInfo.getStatus(),appInfo.getId())){
                return "redirect:/applist";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "backend/appcheck";
    }

}
