package com.hj.app.service.developer;

import com.hj.app.dao.AppCategoryMapper;
import com.hj.app.domain.AppCategory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AppCategoryServiceImpl implements AppCategoryService {
    @Resource
    private AppCategoryMapper appCategoryMapper;

    @Override
    public List<AppCategory> getAppCategoryListByParentId(Integer parentId) {
        return appCategoryMapper.getAppCategoryListByParentId(parentId);
    }
}
