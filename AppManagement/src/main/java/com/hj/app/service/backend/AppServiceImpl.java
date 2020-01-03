package com.hj.app.service.backend;

import com.hj.app.dao.AppInfoMapper;
import com.hj.app.domain.AppInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AppServiceImpl implements AppService {
    @Resource
    private AppInfoMapper appInfoMapper;


    @Override
    public List<AppInfo> getAppInfoList(String querySoftwareName, Integer queryCategoryLevel1, Integer queryCategoryLevel2, Integer queryCategoryLevel3, Integer queryPlatformId, Integer currentPageNo, Integer pageSize) {
        return appInfoMapper.listAllAppInfo(querySoftwareName, 1, queryCategoryLevel1, queryCategoryLevel2,
                queryCategoryLevel3, queryPlatformId, null, (currentPageNo-1)*pageSize, pageSize);
    }

    @Override
    public int getAppInfoCount(String querySoftwareName, Integer queryCategoryLevel1, Integer queryCategoryLevel2, Integer queryCategoryLevel3, Integer queryPlatformId) {
        return appInfoMapper.getAppInfoCount(querySoftwareName, 1, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3,
                queryPlatformId, null);
    }

    @Override
    public AppInfo getAppInfo(Integer id) {
        return appInfoMapper.getAppInfo(id, null);
    }

    @Override
    public boolean updateStatus(Integer status, Integer id) {
        boolean flag = false;
        if (appInfoMapper.updateStatus(status, id) > 0 ) {
            flag = true;
        }
        return flag;
    }
}
