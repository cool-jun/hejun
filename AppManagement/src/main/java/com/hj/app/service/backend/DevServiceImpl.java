package com.hj.app.service.backend;

import com.hj.app.dao.DevUserMapper;
import com.hj.app.domain.DevUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DevServiceImpl implements DevService {
    @Resource
    private DevUserMapper devUserMapper;

    @Override
    public List<DevUser> getDevInfoList(String queryCode, String queryName, Integer currentPageNo, Integer pageSize) {
        return devUserMapper.getDevInfoList(queryCode, queryName, (currentPageNo-1)*pageSize, pageSize);
    }

    @Override
    public int getDevInfoCount(String queryCode, String queryName) {
        return devUserMapper.getDevInfoCount(queryCode, queryName);
    }

    @Override
    public void deleteDevById(int id) {
        devUserMapper.deleteDevById(id);
    }

    @Override
    public DevUser getDevUserById(int id) {
        return devUserMapper.getDevUserById(id);
    }

    @Override
    public void modifyDevUser(int id, String devCode, String devName, String devEmail, String devInfo) {
        devUserMapper.modifyDevUser(id, devCode, devName, devEmail, devInfo);
    }
}
