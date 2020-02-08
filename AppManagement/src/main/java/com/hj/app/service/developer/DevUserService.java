package com.hj.app.service.developer;

import com.hj.app.domain.DevUser;

public interface DevUserService {
    DevUser login(String devCode, String devPassword);
}
