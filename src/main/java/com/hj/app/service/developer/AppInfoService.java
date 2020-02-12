package com.hj.app.service.developer;

import com.hj.app.domain.AppInfo;

import java.util.List;

public interface AppInfoService {

    List<AppInfo> getAppInfoList(String querySoftwareName, Integer queryStatus, Integer queryCategoryLevel1,
                                 Integer queryCategoryLevel2, Integer queryCategoryLevel3, Integer queryPlatformId,
                                 Integer devId, Integer currentPageNo, Integer pageSize);

    int getAppInfoCount(String querySoftwareName, Integer queryStatus,
                        Integer queryCategoryLevel1, Integer queryCategoryLevel2,
                        Integer queryCategoryLevel3, Integer queryPlatformId, Integer devId);

    /**
     * 新增app信息
     * @param appInfo
     * @return
     */
    boolean add(AppInfo appInfo);

    /**
     * 修改app信息
     * @param appInfo
     * @return
     */
    boolean modify(AppInfo appInfo);

    /**
     * 根据appId删除app信息
     * @param delId
     * @return
     */
    boolean deleteAppInfoById(Integer delId);

    /**
     * 根据id、apkName查找appInfo
     */
    AppInfo getAppInfo(Integer id, String APKName);

    /**
     */
    boolean deleteAppLogo(Integer id);

    /**
     * 通过appId删除app应用(app_info、app_version)
     */
    boolean appsysdeleteAppById(Integer id);


    boolean appsysUpdateSaleStatusByAppId(AppInfo appInfo) throws Exception;
}
