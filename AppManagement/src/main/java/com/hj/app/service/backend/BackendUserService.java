package com.hj.app.service.backend;

import com.hj.app.domain.BackendUser;

public interface BackendUserService {
    BackendUser getBackendUser(String userCode);
}
