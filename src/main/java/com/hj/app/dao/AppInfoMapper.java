package com.hj.app.dao;

import com.hj.app.domain.AppInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("appInfo")
@Mapper
public interface AppInfoMapper {
    List<AppInfo> listAllAppInfo(@Param(value = "softwareName") String querySoftwareName,
                                 @Param(value = "status") Integer queryStatus,
                                 @Param(value = "categoryLevel1") Integer queryCategoryLevel1,
                                 @Param(value = "categoryLevel2") Integer queryCategoryLevel2,
                                 @Param(value = "categoryLevel3") Integer queryCategoryLevel3,
                                 @Param(value = "platformId") Integer queryPlatformId,
                                 @Param(value = "devId") Integer devId,
                                 @Param(value = "from") Integer currentPageNo,
                                 @Param(value = "pageSize") Integer pageSize);

    int getAppInfoCount(@Param(value = "softwareName") String querySoftwareName,
                        @Param(value = "status") Integer queryStatus,
                        @Param(value = "categoryLevel1") Integer queryCategoryLevel1,
                        @Param(value = "categoryLevel2") Integer queryCategoryLevel2,
                        @Param(value = "categoryLevel3") Integer queryCategoryLevel3,
                        @Param(value = "platformId") Integer queryPlatformId,
                        @Param(value = "devId") Integer devId);

    int updateVersionId(@Param(value = "versionId") Integer versionId, @Param(value = "id") Integer appId);

    int updateSaleStatusByAppId(@Param(value = "id") Integer appId);

    int updateStatus(@Param(value = "status") Integer status, @Param(value = "id") Integer id);

    AppInfo getAppInfo(@Param(value = "id") Integer id, @Param(value = "APKName") String APKName);

    int deleteAppLogo(@Param(value = "id") Integer id);

    int add(AppInfo appInfo);

    int modify(AppInfo appInfo);

    int deleteAppInfoById(@Param(value = "id") Integer delId);

//    int countApps(@Param("params") Map<String,Object> params);
//    List<AppInfo> selectByPage(@Param("params") Map<String, Object> params);
}
