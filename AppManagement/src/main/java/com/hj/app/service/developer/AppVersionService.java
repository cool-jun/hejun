package com.hj.app.service.developer;

import com.hj.app.domain.AppVersion;

import java.util.List;

public interface AppVersionService {
    /**
     * 根据appId查询相应的app版本列表
     */
    List<AppVersion> getAppVersionList(Integer appId);
    /**
     * 新增app版本信息，并更新app_info表的versionId字段
     */
    boolean appsysadd(AppVersion appVersion);
    /**
     * 根据id获取AppVersion
     */
    AppVersion getAppVersionById(Integer id);

    /**
     * 修改app版本信息
     */
    boolean modify(AppVersion appVersion);

    /**
     * 删除apk文件
     */
    boolean deleteApkFile(Integer id);

    /**
     * 检查app版本号是否唯一
     */
    AppVersion checkVersionNo(Integer appId, String versionNo);
}
