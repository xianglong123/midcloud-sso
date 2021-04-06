package com.cmft.server.service.impl;

import com.cmft.bean.PermissionDO;
import com.cmft.bean.UserDO;
import com.cmft.dao.PermissionMapper;
import com.cmft.dao.RoleMapper;
import com.cmft.dao.SystemMapper;
import com.cmft.dao.UserMapper;
import com.cmft.server.core.result.ReturnT;
import com.cmft.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private SystemMapper systemMapper;

    @Override
    public ReturnT<UserDO> findUser(String username, String password) {

        if (username==null || username.trim().length()==0) {
            return new ReturnT<UserDO>(ReturnT.FAIL_CODE, "Please input username.");
        }
        if (password==null || password.trim().length()==0) {
            return new ReturnT<UserDO>(ReturnT.FAIL_CODE, "Please input password.");
        }

        UserDO user = userMapper.queryByName(username);
        if (password.equals(user.getPassword())) {
            return new ReturnT<UserDO>(user);
        }

        return new ReturnT<UserDO>(ReturnT.FAIL_CODE, "username or password is invalid.");
    }

    @Override
    public Map<String, Object> getPermission(String userId) {
        Map<String, Object> map = new HashMap<>();
        List<PermissionDO> permissionDOS = permissionMapper.queryPermissionByUserId(userId);
        map.put("menus", permissionDOS);
        return map;
    }

    @Override
    public String getUrl(String code) {
        // 系统地址
        String url = systemMapper.queryUrlByCode(code);
        // todo 为空异常处理【待做】
        // 跳转路由
        String route = permissionMapper.queryUrlByCode(code);
        return url + route;
    }
}
