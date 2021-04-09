package com.cmft.server.controller;

import com.cmft.bean.UserDO;
import com.cmft.core.login.SsoTokenLoginHelper;
import com.cmft.core.store.SsoLoginStore;
import com.cmft.core.store.SsoSessionIdHelper;
import com.cmft.core.user.XxlSsoUser;
import com.cmft.server.core.model.UserDTO;
import com.cmft.server.core.result.ReturnT;
import com.cmft.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * sso server (for app)
 *
 * @author xuxueli 2018-04-08 21:02:54
 */
@Controller
@RequestMapping("/app")
public class AppController {

    @Autowired
    private UserService userService;


    /**
     * Login
     *
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public ReturnT<String> login(UserDTO userDTO) {
        // valid login
        ReturnT<UserDO> result = userService.findUser(userDTO.getUsername(), userDTO.getPassword());
        if (result.getCode() != ReturnT.SUCCESS_CODE) {
            return new ReturnT<String>(result.getCode(), result.getMsg());
        }

        // 1、make xxl-sso user
        XxlSsoUser xxlUser = new XxlSsoUser();
        xxlUser.setUserid(String.valueOf(result.getData().getId()));
        xxlUser.setUsername(result.getData().getUsername());
        xxlUser.setPassword(result.getData().getPassword());
        xxlUser.setVersion(UUID.randomUUID().toString().replaceAll("-", ""));
        xxlUser.setExpireMinute(SsoLoginStore.getRedisExpireMinute());
        xxlUser.setExpireFreshTime(System.currentTimeMillis());


        // 2、generate sessionId + storeKey
        String sessionId = SsoSessionIdHelper.makeSessionId(xxlUser);

        // 3、login, store storeKey  2021033114570103_db7bb0f4f2e845f491568721065413b0
        SsoTokenLoginHelper.login(sessionId, xxlUser);

        // 4、return sessionId
        return new ReturnT<String>(sessionId);
    }


    /**
     * Logout
     *
     * @param sessionId
     * @return
     */
    @RequestMapping("/logout")
    @ResponseBody
    public ReturnT<String> logout(String sessionId) {
        // logout, remove storeKey
        SsoTokenLoginHelper.logout(sessionId);
        return ReturnT.SUCCESS;
    }

    /**
     * logincheck
     *
     * @param sessionId
     * @return
     */
    @RequestMapping("/logincheck")
    @ResponseBody
    public ReturnT<Map> logincheck(String sessionId) {

        // logout
        XxlSsoUser xxlUser = SsoTokenLoginHelper.loginCheck(sessionId);
        if (xxlUser == null) {
            return new ReturnT<Map>(ReturnT.FAIL_CODE, "sso not login.");
        }
        String userid = xxlUser.getUserid();
        Map<String, Object> permission = userService.getPermission(userid);
        permission.put("name", xxlUser.getUsername());
        permission.put("id", xxlUser.getUserid());
        return new ReturnT<Map>(permission);
    }


    @ResponseBody
    @RequestMapping("/getPermission")
    public ReturnT<Map> getPermission(String sessionId) {
        XxlSsoUser xxlUser = SsoTokenLoginHelper.loginCheck(sessionId);
        if (xxlUser == null) {
            return new ReturnT<Map>(ReturnT.FAIL_CODE, "sso not login.");
        }
        // 获取当前权限
        String userid = xxlUser.getUserid();
        Map<String, Object> permission = userService.getPermission(userid);
        permission.put("name", xxlUser.getUsername());
        permission.put("id", xxlUser.getUserid());
        return new ReturnT<Map>(permission);
    }

    /**
     * 通过权限code获取跨域地址
     * @param code
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUrl")
    public ReturnT<String> getUrl(String code) {
        String url = userService.getUrl(code);
        return new ReturnT<String>(url);
    }

    @ResponseBody
    @RequestMapping("/getInfoBySessionId")
    public ReturnT<Map> getInfoBySessionId(String sessionId) {
        XxlSsoUser xxlUser = SsoTokenLoginHelper.loginCheck(sessionId);
        if (xxlUser == null) {
            return new ReturnT<>(ReturnT.FAIL_CODE, "sso not login.");
        }
        Map<String, String> map = new HashMap<>();
        map.put("username", xxlUser.getUsername());
        map.put("password", xxlUser.getPassword());
        return new ReturnT<>(map);
    }


}
