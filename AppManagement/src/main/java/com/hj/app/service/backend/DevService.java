package com.hj.app.service.backend;

import com.hj.app.domain.DevUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DevService {

    List<DevUser> getDevInfoList(@Param(value = "queryCode") String queryCode,
                                 @Param(value = "queryName") String queryName,
                                  @Param(value = "from") Integer currentPageNo,
                                  @Param(value = "pageSize") Integer pageSize);

    int getDevInfoCount(@Param(value = "queryCode") String queryCode, @Param(value = "queryName") String queryName);

    void deleteDevById(int id);

    DevUser getDevUserById(int id);

    void modifyDevUser(int id, String devCode, String devName, String devEmail, String devInfo);
}
