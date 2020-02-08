package com.hj.app.dao;

import com.hj.app.domain.AppVersion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppVersionMapper {
    List<AppVersion> getAppVersionList(@Param("appId") Integer appId);

    int add(AppVersion appVersion);

    int getVersionCountByAppId(@Param("appId") Integer appId);

    int deleteVersionByAppId(@Param("appId") Integer appId);

    AppVersion getAppVersionById(@Param("id") Integer id);

    int modify(AppVersion appVersion);

    int deleteApkFile(@Param("id") Integer id);

    AppVersion checkVersionNo(@Param("appId") Integer appId, @Param("versionNo") String versionNo);
}
