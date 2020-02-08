package com.hj.app.dao;

import com.hj.app.domain.BackendUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BackendUserMapper {

    BackendUser getPasswordByCode(String userCode);

}
