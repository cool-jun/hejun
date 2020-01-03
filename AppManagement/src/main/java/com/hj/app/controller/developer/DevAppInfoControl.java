package com.hj.app.controller.developer;

import com.alibaba.fastjson.JSONArray;
import com.hj.app.domain.*;
import com.hj.app.service.developer.AppCategoryService;
import com.hj.app.service.developer.AppInfoService;
import com.hj.app.service.developer.AppVersionService;
import com.hj.app.service.developer.DataDictionaryService;
import com.hj.app.utils.Constants;
import com.hj.app.utils.PageSupport;
import com.mysql.jdbc.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class DevAppInfoControl {

    @Resource
    private AppInfoService appInfoService;
    @Resource
    private AppCategoryService appCategoryService;
    @Resource
    private AppVersionService appVersionService;
    @Resource
    private DataDictionaryService dataDictionaryService;

    @RequestMapping("showAllAppInfo")
    public String getAllAppInfo(Model model, HttpSession session,
                                @RequestParam(value = "querySoftwareName", required = false) String querySoftwareName,
                                @RequestParam(value = "queryStatus", required = false) String _queryStatus,
                                @RequestParam(value = "queryPlatformId", required = false) String _queryPlatformId,
                                @RequestParam(value = "queryCategoryLevel1", required = false) String _queryCategoryLevel1,
                                @RequestParam(value = "queryCategoryLevel2", required = false) String _queryCategoryLevel2,
                                @RequestParam(value = "queryCategoryLevel3", required = false) String _queryCategoryLevel3,
                                @RequestParam(value = "pageIndex", required = false) String pageIndex) {
        Integer devId = ((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId();
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
        totalCount = appInfoService.getAppInfoCount(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2,
                queryCategoryLevel3, queryPlatformId, devId);

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
        appInfoList = appInfoService.getAppInfoList(querySoftwareName, queryStatus, queryCategoryLevel1,
                queryCategoryLevel2, queryCategoryLevel3, queryPlatformId, devId, currentPageNo, pageSize);
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
        return "developer/appinfolist";
    }

    public List<DataDictionary> getDataDictionaryList(String typeCode) {
        List<DataDictionary> dataDictionaryList;
        dataDictionaryList = dataDictionaryService.getDataDictionaryList(typeCode);
        return dataDictionaryList;
    }

    /**
     * 根据typeCode查询出相应的数据字典列表
     */
    @RequestMapping("/datadictionarylist.json")
    @ResponseBody
    public List<DataDictionary> getDataDicList(@RequestParam String tcode) {
        tcode = "APP_PLATFORM";
        System.out.println(getDataDictionaryList(tcode));
        return this.getDataDictionaryList(tcode);
    }

    /**
     * 根据parentId查询出相应的分类级别列表
     */
    @RequestMapping("/categorylevellist.json")
    @ResponseBody
    public List<AppCategory> getAppCategoryList(@RequestParam String pid) {
        if (pid.equals("")) pid = null;
        return getCategoryList(pid);
    }

    public List<AppCategory> getCategoryList (String pid) {
        List<AppCategory> categoryLevelList;
        categoryLevelList = appCategoryService.getAppCategoryListByParentId(pid==null?null:Integer.parseInt(pid));
        return categoryLevelList;
    }

    /**
     * 增加app信息（跳转到新增appinfo页面）
     */
    @RequestMapping("/appinfoadd")
    public String add(@ModelAttribute("appInfo") AppInfo appInfo) {
        return "developer/appinfoadd";
    }

    /**
     * 保存新增appInfo（主表）的数据
     */
    @RequestMapping("/appinfoaddsave")
    public String addSave(AppInfo appInfo, HttpSession session, HttpServletRequest request,
                          @RequestParam(value="a_logoPicPath", required = false) MultipartFile attach){

        String logoPicPath =  null;
        String logoLocPath =  null;
        if(!attach.isEmpty()){
            String path = request.getSession().getServletContext().getRealPath("statics"+java.io.File.separator+"uploadfiles");
            String oldFileName = attach.getOriginalFilename();//原文件名
            String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
            int fileSize = 500000;
            if(attach.getSize() > fileSize){//上传大小不得超过 50k
                request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_4);
                return "developer/appinfoadd";
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png")
                    ||prefix.equalsIgnoreCase("jepg") || prefix.equalsIgnoreCase("pneg")){//上传图片格式
                String fileName = appInfo.getAPKName() + ".jpg";//上传LOGO图片命名:apk名称.apk
                File targetFile = new File(path,fileName);
                if(!targetFile.exists()){
                    targetFile.mkdirs();
                }
                try {
                    attach.transferTo(targetFile);
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_2);
                    return "developer/appinfoadd";
                }
                logoPicPath = request.getContextPath()+"/statics/uploadfiles/"+fileName;
                logoLocPath = path+File.separator+fileName;
            }else{
                request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_3);
                return "developer/appinfoadd";
            }
        }
        appInfo.setCreatedBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
        appInfo.setCreationDate(new Date());
        appInfo.setLogoPicPath(logoPicPath);
        appInfo.setLogoLocPath(logoLocPath);
        appInfo.setDevId(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
        appInfo.setStatus(1);

        if (appInfoService.add(appInfo)) {
            return "redirect:/showAllAppInfo";
        }
        return "developer/appinfoadd";
    }

    /**
     * 增加appversion信息（跳转到新增app版本页面）
     */
    @RequestMapping("/appversionadd")
    public String addVersion(@RequestParam(value="id") String appId,
                             @RequestParam(value="error", required = false) String fileUploadError,
                             AppVersion appVersion, Model model) {

        if (null != fileUploadError && fileUploadError.equals("error1")) {
            fileUploadError = Constants.FILEUPLOAD_ERROR_1;
        } else if (null != fileUploadError && fileUploadError.equals("error2")) {
            fileUploadError	= Constants.FILEUPLOAD_ERROR_2;
        } else if (null != fileUploadError && fileUploadError.equals("error3")) {
            fileUploadError = Constants.FILEUPLOAD_ERROR_3;
        }

        appVersion.setAppId(Integer.parseInt(appId));
        List<AppVersion> appVersionList;

        appVersionList = appVersionService.getAppVersionList(Integer.parseInt(appId));
        //根据appId找app名字
        appVersion.setAppName((appInfoService.getAppInfo(Integer.parseInt(appId),null)).getSoftwareName());

        model.addAttribute("appVersionList", appVersionList);
        model.addAttribute(appVersion);
        model.addAttribute("fileUploadError", fileUploadError);
        return "developer/appversionadd";
    }
    /**
     * 保存新增appversion数据（子表）-上传该版本的apk包
     */
    @RequestMapping("/addversionsave")
    public String addVersionSave(AppVersion appVersion, HttpSession session, HttpServletRequest request,
                                 @RequestParam(value = "a_downloadLink", required = false) MultipartFile attach) {
        String downloadLink =  null;
        String apkLocPath = null;
        String apkFileName = null;
        if (!attach.isEmpty()) {
            String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
            String oldFileName = attach.getOriginalFilename();//原文件名
            String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
            if (prefix.equalsIgnoreCase("apk")){//apk文件命名：apk名称+版本号+.apk
                String apkName;
                apkName = appInfoService.getAppInfo(appVersion.getAppId(),null).getAPKName();

                if (apkName == null || "".equals(apkName)) {
                    return "redirect:/appversionadd?id="+appVersion.getAppId()+"&error=error1";
                }
                apkFileName = apkName + "-" +appVersion.getVersionNo() + ".apk";
                File targetFile = new File(path, apkFileName);
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }

                try {
                    attach.transferTo(targetFile);
                } catch (Exception e) {
                    return "redirect:appversionadd?id="+appVersion.getAppId() +"&error=error2";
                }

                downloadLink = request.getContextPath()+"/statics/uploadfiles/"+apkFileName;
                apkLocPath = path+File.separator+apkFileName;
            } else {
                return "redirect:/appversionadd?id="+appVersion.getAppId()
                        +"&error=error3";
            }
        }
        appVersion.setCreatedBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
        appVersion.setCreationDate(new Date());

        appVersion.setDownloadLink(downloadLink);
        appVersion.setApkLocPath(apkLocPath);
        appVersion.setApkFileName(apkFileName);

        try {
            if(appVersionService.appsysadd(appVersion)){
                return "redirect:/showAllAppInfo";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/appversionadd?id="+appVersion.getAppId();
    }

    @RequestMapping("/{appid}/sale")
    @ResponseBody
    public Object sale(@PathVariable String appid, HttpSession session){
        HashMap<String, Object> resultMap = new HashMap<>();
        Integer appIdInteger;
        try{
            appIdInteger = Integer.parseInt(appid);
        }catch(Exception e){
            appIdInteger = 0;
        }
        resultMap.put("errorCode", "0");
        resultMap.put("appId", appid);
        if(appIdInteger>0){
            try {
                DevUser devUser = (DevUser)session.getAttribute(Constants.DEV_USER_SESSION);
                AppInfo appInfo = new AppInfo();
                appInfo.setId(appIdInteger);
                appInfo.setModifyBy(devUser.getId());
                if(appInfoService.appsysUpdateSaleStatusByAppId(appInfo)){
                    resultMap.put("resultMsg", "success");
                }else{
                    resultMap.put("resultMsg", "success");
                }
            } catch (Exception e) {
                resultMap.put("errorCode", "exception000001");
            }
        }else{
            resultMap.put("errorCode", "param000001");
        }
        return resultMap;
    }

    /**
     * 判断APKName是否唯一
     */
    @RequestMapping("/apkexist.json")
    @ResponseBody
    public Object apkNameIsExit(@RequestParam String APKName){
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(StringUtils.isNullOrEmpty(APKName)){
            resultMap.put("APKName", "empty");
        }else{
            AppInfo appInfo = null;
            try {
                appInfo = appInfoService.getAppInfo(null, APKName);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(null != appInfo)
                resultMap.put("APKName", "exist");
            else
                resultMap.put("APKName", "noexist");
        }
        return JSONArray.toJSONString(resultMap);
    }

    /**
     * 查看app信息，包括app基本信息和版本信息列表（跳转到查看页面）
     */
    //@PathVariable是spring3.0的一个新功能：接收请求路径中占位符的值
    @RequestMapping("/appview/{id}")
    public String view(@PathVariable String id, Model model) {
        AppInfo appInfo;
        List<AppVersion> appVersionList;

        appInfo = appInfoService.getAppInfo(Integer.parseInt(id),null);
        appVersionList = appVersionService.getAppVersionList(Integer.parseInt(id));

        model.addAttribute("appVersionList", appVersionList);
        model.addAttribute(appInfo);

        return "developer/appinfoview";
    }

    /**
     * 修改app信息，包括：修改app基本信息（appInfo）和修改版本信息（appVersion）
     * 分为两步实现：
     * 1 修改app基本信息（appInfo）
     * 2 修改版本信息（appVersion）
     */

    /**
     * 修改appInfo信息（跳转到修改appInfo页面）

     */
    @RequestMapping("/appinfomodify")
    public String modifyAppInfo(@RequestParam("id") String id,
                                @RequestParam(value = "error", required = false) String fileUploadError,
                                Model model) {
        AppInfo appInfo;

        if (null != fileUploadError && fileUploadError.equals("error1")) {
            fileUploadError = Constants.FILEUPLOAD_ERROR_1;
        }else if (null != fileUploadError && fileUploadError.equals("error2")) {
            fileUploadError	= Constants.FILEUPLOAD_ERROR_2;
        }else if (null != fileUploadError && fileUploadError.equals("error3")) {
            fileUploadError = Constants.FILEUPLOAD_ERROR_3;
        }else if (null != fileUploadError && fileUploadError.equals("error4")) {
            fileUploadError = Constants.FILEUPLOAD_ERROR_4;
        }

        appInfo = appInfoService.getAppInfo(Integer.parseInt(id),null);

        model.addAttribute(appInfo);
        model.addAttribute("fileUploadError", fileUploadError);

        return "developer/appinfomodify";
    }

    /**
     * 修改最新的appVersion信息（跳转到修改appVersion页面）

     */
    @RequestMapping("/appversionmodify")
    public String modifyAppVersion(@RequestParam("vid") String versionId,
                                   @RequestParam("aid") String appId,
                                   @RequestParam(value="error",required= false)String fileUploadError,
                                   Model model){
        AppVersion appVersion = null;
        List<AppVersion> appVersionList = null;
        if(null != fileUploadError && fileUploadError.equals("error1")){
            fileUploadError = Constants.FILEUPLOAD_ERROR_1;
        }else if(null != fileUploadError && fileUploadError.equals("error2")){
            fileUploadError	= Constants.FILEUPLOAD_ERROR_2;
        }else if(null != fileUploadError && fileUploadError.equals("error3")){
            fileUploadError = Constants.FILEUPLOAD_ERROR_3;
        }
        try {
            appVersion = appVersionService.getAppVersionById(Integer.parseInt(versionId));
            appVersionList = appVersionService.getAppVersionList(Integer.parseInt(appId));
        }catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.addAttribute(appVersion);
        model.addAttribute("appVersionList",appVersionList);
        model.addAttribute("fileUploadError",fileUploadError);
        return "developer/appversionmodify";
    }

    /**
     * 保存修改后的appVersion
     */
    @RequestMapping("/appversionmodifysave")
    public String modifyAppVersionSave(AppVersion appVersion, HttpSession session, HttpServletRequest request,
                                       @RequestParam(value="attach", required = false) MultipartFile attach) {

        String downloadLink =  null;
        String apkLocPath = null;
        String apkFileName = null;
        if(!attach.isEmpty()){
            String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");

            String oldFileName = attach.getOriginalFilename();//原文件名
            String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
            if(prefix.equalsIgnoreCase("apk")){//apk文件命名：apk名称+版本号+.apk
                String apkName = null;
                try {
                    apkName = appInfoService.getAppInfo(appVersion.getAppId(),null).getAPKName();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                if(apkName == null || "".equals(apkName)){
                    return "redirect:/appversionmodify?vid="+appVersion.getId()
                            +"&aid="+appVersion.getAppId()
                            +"&error=error1";
                }
                apkFileName = apkName + "-" +appVersion.getVersionNo() + ".apk";
                File targetfile = new File(path, apkFileName);
                if(!targetfile.exists()){
                    targetfile.mkdirs();
                }
                try {
                    attach.transferTo(targetfile);
                } catch (Exception e) {
                    e.printStackTrace();
                    return "redirect:/appversionmodify?vid="+appVersion.getId()
                            +"&aid="+appVersion.getAppId()
                            +"&error=error2";
                }
                downloadLink = request.getContextPath()+"/statics/uploadfiles/"+apkFileName;
                apkLocPath = path+File.separator+apkFileName;
            }else{
                return "redirect:/appversionmodify?vid="+appVersion.getId()
                        +"&aid="+appVersion.getAppId()
                        +"&error=error3";
            }
        }
        appVersion.setModifyBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
        appVersion.setModifyDate(new Date());
        appVersion.setDownloadLink(downloadLink);
        appVersion.setApkLocPath(apkLocPath);
        appVersion.setApkFileName(apkFileName);
        try {
            if(appVersionService.modify(appVersion)){
                return "redirect:/showAllAppInfo";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "developer/appversionmodify";
    }

    /**
     * 修改操作时，删除文件（logo图片/apk文件），并更新数据库（app_info/app_version）
     */
    @RequestMapping("/delfile")
    @ResponseBody
    public Object delFile(@RequestParam(value="flag",required=false) String flag,
                          @RequestParam(value="id",required=false) String id){
        HashMap<String, String> resultMap = new HashMap<>();
        String fileLocPath;
        if(flag == null || flag.equals("") ||
                id == null || id.equals("")){
            resultMap.put("result", "failed");
        }else if(flag.equals("logo")) {//删除logo图片（操作app_info）
            try {
                fileLocPath = (appInfoService.getAppInfo(Integer.parseInt(id), null)).getLogoLocPath();
                File file = new File(fileLocPath);
                if(file.exists())
                    if(file.delete()){//删除服务器存储的物理文件
                        if(appInfoService.deleteAppLogo(Integer.parseInt(id))){//更新表
                            resultMap.put("result", "success");
                        }
                    }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(flag.equals("apk")){//删除apk文件（操作app_version）
            try {
                fileLocPath = (appVersionService.getAppVersionById(Integer.parseInt(id))).getApkLocPath();
                File file = new File(fileLocPath);
                if(file.exists())
                    if(file.delete()){//删除服务器存储的物理文件
                        if(appVersionService.deleteApkFile(Integer.parseInt(id))){//更新表
                            resultMap.put("result", "success");
                        }
                    }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return JSONArray.toJSONString(resultMap);
    }

    /**
     * 保存修改后的appInfo
     */
    @RequestMapping("/appinfomodifysave")
    public String modifySave(AppInfo appInfo, HttpSession session, HttpServletRequest request,
                             @RequestParam(value = "attach", required = false) MultipartFile attach) {
        String logoPicPath =  null;
        String logoLocPath =  null;
        String APKName = appInfo.getAPKName();
        if (!attach.isEmpty()) {
            String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
            String oldFileName = attach.getOriginalFilename();//原文件名
            String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
            int fileSize = 500000;
            if (attach.getSize() > fileSize) {//上传大小不得超过 50k
                return "redirect:/appinfomodify?id="+appInfo.getId()
                        +"&error=error4";
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png")
                    ||prefix.equalsIgnoreCase("jepg") || prefix.equalsIgnoreCase("pneg")){//上传图片格式
                String fileName = APKName + ".jpg";//上传LOGO图片命名:apk名称.apk
                File targetFile = new File(path,fileName);
                if(!targetFile.exists()){
                    targetFile.mkdirs();
                }
                try {
                    attach.transferTo(targetFile);
                } catch (Exception e) {
                    e.printStackTrace();
                    return "redirect:/appinfomodify?id="+appInfo.getId()
                            +"&error=error2";
                }
                logoPicPath = request.getContextPath()+"/statics/uploadfiles/"+fileName;
                logoLocPath = path+File.separator+fileName;
            }else{
                return "redirect:/appinfomodify?id="+appInfo.getId()
                        +"&error=error3";
            }
        }

        appInfo.setModifyBy(((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
        appInfo.setModifyDate(new Date());
        appInfo.setLogoLocPath(logoLocPath);
        appInfo.setLogoPicPath(logoPicPath);

        try {
            if(appInfoService.modify(appInfo)){
                return "redirect:/showAllAppInfo";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "developer/appinfomodify";
    }

    //删除app
    @RequestMapping(value="/delapp.json")
    @ResponseBody
    public Object delApp(@RequestParam String id){
        HashMap<String, String> resultMap = new HashMap<>();
        if (StringUtils.isNullOrEmpty(id)) {
            resultMap.put("delResult", "notexist");
        }else{
            try {
                if(appInfoService.appsysdeleteAppById(Integer.parseInt(id)))
                    resultMap.put("delResult", "true");
                else
                    resultMap.put("delResult", "false");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return JSONArray.toJSONString(resultMap);
    }



//    @RequestMapping("/showAppInfo")
//    public String listAllAppInfo(HttpSession session) {
//        return appInfoService.listAllAppInfo(session);
//    }
//
//    @RequestMapping("/selectAppsByPage")
//    public String selectAppsByPage(Integer pageNow, String softwareName, String interfacelanguage, Model model) {
//
//        //map对象存储传入的值
//        Map<String, Object> params = new HashMap<>();
//
//        if (pageNow != null) {
//            params.put("pageNow", pageNow);
//        }
//
//        if (softwareName != null && softwareName.length() > 0) {
//            params.put("softwareName", "%"+softwareName+"%");
//        }
//
//        if (interfaceLanguage != null && !interfaceLanguage.equals("-1")) {
//            params.put("interfaceLanguage", interfaceLanguage);
//        }
//
//        PageInfo<AppInfo> pageInfo = appInfoService.allAppsByPage(params);
//
//        model.addAttribute("pageInfo", pageInfo);
//
//        return "appsList";
//    }
//
//    @RequestMapping("/toAppInfoAdd")
//    public String toAppInfoAdd() {
//        return "developer/appinfoadd";
//    }
//
//    @RequestMapping("/appInfoAddSave")
//    public String appInfoAddSave() {
//        return "";
//    }

}
