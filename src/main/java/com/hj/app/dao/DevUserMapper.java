package com.hj.app.dao;

import com.hj.app.domain.DevUser;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface DevUserMapper {
    /**
     * 根据devCode获取用户记录
     * @param devCode
     * @return
     */
    DevUser getLoginUser(@Param("devCode") String devCode);
    int getDevInfoCount(@Param(value = "devCode") String devCode, @Param(value = "devName") String devName);
    List<DevUser> getDevInfoList(@Param(value = "devCode") String devCode,
                                 @Param(value = "devName") String devName,
                                 @Param(value = "from") Integer currentPageNo,
                                 @Param(value = "pageSize") Integer pageSize);

    void deleteDevById(int id);
    DevUser getDevUserById(int id);
    void modifyDevUser(@Param("id") int id, @Param("devCode") String devCode, @Param("devName") String devName,
                       @Param("devEmail") String devEmail, @Param("devInfo") String devInfo);
    //    String checkUserName(String devName);
}
