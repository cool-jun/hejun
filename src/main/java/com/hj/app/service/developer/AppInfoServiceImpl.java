package com.hj.app.service.developer;

import com.hj.app.dao.AppInfoMapper;
import com.hj.app.dao.AppVersionMapper;
import com.hj.app.domain.AppInfo;
import com.hj.app.domain.AppVersion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AppInfoServiceImpl implements AppInfoService {
    @Resource
    public AppInfoMapper appInfoMapper;
    @Resource
    private AppVersionMapper appVersionMapper;

    @Override
    public List<AppInfo> getAppInfoList(String querySoftwareName, Integer queryStatus, Integer queryCategoryLevel1, Integer queryCategoryLevel2, Integer queryCategoryLevel3,
                                        Integer queryPlatformId, Integer devId, Integer currentPageNo, Integer pageSize) {
        return appInfoMapper.listAllAppInfo(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3,
                queryPlatformId, devId, (currentPageNo-1)*pageSize, pageSize);
    }

    @Override
    public int getAppInfoCount(String querySoftwareName, Integer queryStatus,
                               Integer queryCategoryLevel1, Integer queryCategoryLevel2,
                               Integer queryCategoryLevel3, Integer queryPlatformId, Integer devId) {
        return appInfoMapper.getAppInfoCount(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2,
                queryCategoryLevel3, queryPlatformId, devId);
    }

    @Override
    public boolean add(AppInfo appInfo) {
        boolean flag = false;
        if (appInfoMapper.add(appInfo) > 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean modify(AppInfo appInfo) {
        boolean flag = false;
        if (appInfoMapper.modify(appInfo) > 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean deleteAppInfoById(Integer delId) {
        boolean flag = false;
        if (appInfoMapper.deleteAppInfoById(delId) > 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean appsysdeleteAppById(Integer id) {
        boolean flag = false;
        int versionCount = appVersionMapper.getVersionCountByAppId(id);
        List<AppVersion> appVersionList;
        if (versionCount > 0) {
            //1 先删版本信息
            //<1> 删除上传的apk文件
            appVersionList = appVersionMapper.getAppVersionList(id);
            for(AppVersion appVersion:appVersionList){
                if(appVersion.getApkLocPath() != null && !appVersion.getApkLocPath().equals("")){
                    File file = new File(appVersion.getApkLocPath());
                    if(file.exists()){
                        if(!file.delete()) {
                            System.out.println("fail to delete file");
                        } else {
                            System.out.println("delete file success");
                        }
                    }
                }
            }
            //<2> 删除app_version表数据
            appVersionMapper.deleteVersionByAppId(id);
        }
        //2 再删app基础信息
        //<1> 删除上传的logo图片
        AppInfo appInfo = appInfoMapper.getAppInfo(id, null);
        if(appInfo.getLogoLocPath() != null && !appInfo.getLogoLocPath().equals("")){
            File file = new File(appInfo.getLogoLocPath());
            if(file.exists()){
                if(!file.delete()) {
                    System.out.println("fail to delete file");
                } else {
                    System.out.println("delete file success");
                }
            }
        }
        //<2> 删除app_info表数据
        if(appInfoMapper.deleteAppInfoById(id) > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean appsysUpdateSaleStatusByAppId(AppInfo appInfoObj) throws Exception {
        /*
		 * 上架：
			1 更改status由【2 or 5】 to 4 ， 上架时间
			2 根据versionid 更新 publishStauts 为 2

			下架：
			更改status 由4给为5
		 */

        Integer operator = appInfoObj.getModifyBy();
        if (operator < 0 || appInfoObj.getId() < 0 ) {
            System.out.println("error");
        }

        //get appinfo by appid
        AppInfo appInfo = appInfoMapper.getAppInfo(appInfoObj.getId(), null);
        if(null == appInfo){
            return false;
        }else{
            switch (appInfo.getStatus()) {
                case 2: //当状态为审核通过时，可以进行上架操作
                    onSale(appInfo,operator,4,2);
                    break;
                case 5://当状态为下架时，可以进行上架操作
                    onSale(appInfo,operator,4,2);
                    break;
                case 4://当状态为上架时，可以进行下架操作
                    offSale(appInfo,operator,5);
                    break;

                default:
                    return false;
            }
        }
        return true;
    }

    @Override
    public AppInfo getAppInfo(Integer id, String APKName) {
        return appInfoMapper.getAppInfo(id, APKName);
    }

    @Override
    public boolean deleteAppLogo(Integer id) {
        boolean flag = false;
        if (appInfoMapper.deleteAppLogo(id) > 0) {
            flag = true;
        }
        return flag;
    }


    /**
     * on Sale
     */
    private void onSale(AppInfo appInfo,Integer operator,Integer appInfStatus,Integer versionStatus) throws Exception{
        offSale(appInfo,operator,appInfStatus);
        setSaleSwitchToAppVersion(appInfo,operator,versionStatus);
    }


    /**
     * offSale
     */
    private boolean offSale(AppInfo appInfo,Integer operator,Integer appInfStatus) throws Exception{
        AppInfo _appInfo = new AppInfo();
        _appInfo.setId(appInfo.getId());
        _appInfo.setStatus(appInfStatus);
        _appInfo.setModifyBy(operator);
        _appInfo.setOffSaleDate(new Date(System.currentTimeMillis()));
        appInfoMapper.modify(_appInfo);
        return true;
    }

    /**
     * set sale method to on or off
     */
    private boolean setSaleSwitchToAppVersion(AppInfo appInfo,Integer operator,Integer saleStatus) throws Exception{
        AppVersion appVersion = new AppVersion();
        appVersion.setId(appInfo.getVersionId());
        appVersion.setPublishStatus(saleStatus);
        appVersion.setModifyBy(operator);
        appVersion.setModifyDate(new Date(System.currentTimeMillis()));
        appVersionMapper.modify(appVersion);
        return false;
    }
}
