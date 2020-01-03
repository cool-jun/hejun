package com.hj.app.service.developer;

import com.hj.app.dao.AppInfoMapper;
import com.hj.app.dao.AppVersionMapper;
import com.hj.app.domain.AppVersion;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AppVersionServiceImpl implements AppVersionService {
    @Resource
    private AppVersionMapper appVersionMapper;
    @Resource
    private AppInfoMapper appInfoMapper;

    @Override
    public List<AppVersion> getAppVersionList(Integer appId) {
        return appVersionMapper.getAppVersionList(appId);
    }

    @Override
    public boolean appsysadd(AppVersion appVersion) {
        boolean flag = false;
        Integer versionId = null;
        if (appVersionMapper.add(appVersion) > 0) {
            versionId = appVersion.getId();
            flag = true;
        }
        if (appInfoMapper.updateVersionId(versionId, appVersion.getAppId()) > 0 && flag) {
            flag = true;
        }
        return flag;
    }

    @Override
    public AppVersion getAppVersionById(Integer id) {
        return appVersionMapper.getAppVersionById(id);
    }

    @Override
    public boolean modify(AppVersion appVersion) {
        boolean flag = false;
        if (appVersionMapper.modify(appVersion) > 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean deleteApkFile(Integer id) {
        boolean flag = false;
        if (appVersionMapper.deleteApkFile(id) > 0) {
            flag = true;
        }
        return flag;
    }
}
