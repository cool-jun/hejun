package com.hj.app.dao;

import com.hj.app.domain.AppCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppCategoryMapper {
    List<AppCategory> getAppCategoryListByParentId(@Param("parentId") Integer parentId);
}
