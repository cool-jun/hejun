package com.hj.app.service.backend;

import com.hj.app.domain.AppInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppService {
    /**
     * 根据条件查询出待审核的APP列表并分页显示
     */
    List<AppInfo> getAppInfoList(@Param(value = "softwareName") String querySoftwareName,
                                 @Param(value = "categoryLevel1") Integer queryCategoryLevel1,
                                 @Param(value = "categoryLevel2") Integer queryCategoryLevel2,
                                 @Param(value = "categoryLevel3") Integer queryCategoryLevel3,
                                 @Param(value = "platformId") Integer queryPlatformId,
                                 @Param(value = "from") Integer currentPageNo,
                                 @Param(value = "pageSize") Integer pageSize);
    /**
     * 查询出待审核（status=1）的APP数量
     */
    int getAppInfoCount(@Param(value = "softwareName") String querySoftwareName,
                        @Param(value = "categoryLevel1") Integer queryCategoryLevel1,
                        @Param(value = "categoryLevel2") Integer queryCategoryLevel2,
                        @Param(value = "categoryLevel3") Integer queryCategoryLevel3,
                        @Param(value = "platformId") Integer queryPlatformId);

    /**
     * 根据id获取app详细信息
     */
    AppInfo getAppInfo(@Param(value = "id") Integer id);

    /**
     * 根据id更新app的status
     */
    boolean updateStatus(@Param(value = "status") Integer status, @Param(value = "id") Integer id);

}
