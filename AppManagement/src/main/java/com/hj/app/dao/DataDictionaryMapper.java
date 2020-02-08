package com.hj.app.dao;

import com.hj.app.domain.DataDictionary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DataDictionaryMapper {
    List<DataDictionary> getDataDictionaryList(@Param("typeCode") String typeCode);
}
