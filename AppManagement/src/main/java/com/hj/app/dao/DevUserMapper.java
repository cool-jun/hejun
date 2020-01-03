package com.hj.app.dao;

import com.hj.app.domain.DevUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("devUser")
@Mapper
public interface DevUserMapper {

    DevUser getLoginUser(@Param("devCode") String devCode);
//    String checkUserName(String devName);
}
