package com.hj.app.service.developer;

import com.hj.app.domain.DataDictionary;

import java.util.List;

public interface DataDictionaryService {
    List<DataDictionary> getDataDictionaryList(String typeCode);
}
