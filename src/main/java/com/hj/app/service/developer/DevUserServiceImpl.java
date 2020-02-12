package com.hj.app.service.developer;

import com.hj.app.dao.DevUserMapper;
import com.hj.app.domain.DevUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DevUserServiceImpl implements DevUserService {
    @Resource
    private DevUserMapper devUserMapper;

    @Override
    public DevUser login(String devCode, String devPassword) {
        DevUser devUser = devUserMapper.getLoginUser(devCode);
        //匹配密码
        if (devUser != null) {
            if (!devUser.getDevPassword().equals(devPassword)) {
                devUser = null;
            }
        }
        return devUser;
    }
}
