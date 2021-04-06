package com.cmft.server.service;

import com.cmft.bean.UserDO;
import com.cmft.server.core.result.ReturnT;

import java.util.List;
import java.util.Map;

public interface UserService {

    public ReturnT<UserDO> findUser(String username, String password);

    public Map<String, Object> getPermission(String userId);

    public String getUrl(String code);

}
