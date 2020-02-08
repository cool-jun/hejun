package com.hj.app.service.developer;

import com.hj.app.dao.DataDictionaryMapper;
import com.hj.app.domain.DataDictionary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataDictionaryServiceImpl implements DataDictionaryService {
    @Resource
    private DataDictionaryMapper dataDictionaryMapper;

    @Override
    public List<DataDictionary> getDataDictionaryList(String typeCode) {
        return dataDictionaryMapper.getDataDictionaryList(typeCode);
    }
}
