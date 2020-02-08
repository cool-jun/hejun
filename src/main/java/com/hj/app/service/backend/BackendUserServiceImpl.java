package com.hj.app.service.backend;

import com.hj.app.dao.BackendUserMapper;
import com.hj.app.domain.BackendUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BackendUserServiceImpl implements BackendUserService {
    @Resource
    private BackendUserMapper backendUserMapper;

    @Override
    public BackendUser getBackendUser(String userCode) {
        return backendUserMapper.getPasswordByCode(userCode);
    }
}
