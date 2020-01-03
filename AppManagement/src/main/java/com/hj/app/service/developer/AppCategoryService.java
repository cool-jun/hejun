package com.hj.app.service.developer;

import com.hj.app.domain.AppCategory;

import java.util.List;

public interface AppCategoryService {
    List<AppCategory> getAppCategoryListByParentId(Integer parentId);
}
