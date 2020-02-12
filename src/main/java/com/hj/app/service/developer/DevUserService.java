package com.hj.app.service.developer;

import com.hj.app.domain.DevUser;

public interface DevUserService {
    /**
     * 登录验证
     * @param devCode
     * @param devPassword
     * @return
     */
    DevUser login(String devCode, String devPassword);
}
