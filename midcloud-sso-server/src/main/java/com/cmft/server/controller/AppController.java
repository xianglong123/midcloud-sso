package com.cmft.server.controller;

import com.alibaba.fastjson.JSON;
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
        xxlUser.setVersion(UUID.randomUUID().toString().replaceAll("-", ""));
        xxlUser.setExpireMinute(SsoLoginStore.getRedisExpireMinute());
        xxlUser.setExpireFreshTime(System.currentTimeMillis());


        // 2、generate sessionId + storeKey
        String sessionId = SsoSessionIdHelper.makeSessionId(xxlUser);

        // 3、login, store storeKey
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
        String res = "{\n" +
                "  \"name\": \"客户端\",\n" +
                "  \"resources\": [\n" +
                "    {\n" +
                "      \"id\": \"cba62955646c4a489e2ca1442c786270\",\n" +
                "      \"name\": \"商品-查询\",\n" +
                "      \"summary\": null,\n" +
                "      \"url\": \"/medications\",\n" +
                "      \"method\": \"GET\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"4028811a5e1820d9015e1824acf20000\",\n" +
                "      \"name\": \"获取权限\",\n" +
                "      \"summary\": null,\n" +
                "      \"url\": \"/signin\",\n" +
                "      \"method\": \"GET\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"id\": \"2c9180895e172348015e188b362c0027\",\n" +
                "  \"menus\": [\n" +
                "    {\n" +
                "      \"id\": \"2c9180895e172348015e1857eb460025\",\n" +
                "      \"name\": \"商品管理\",\n" +
                "      \"parent_id\": null,\n" +
                "      \"route\": \"goods\",\n" +
                "      \"code\": \"ABC\",\n" +
                "      \"summary\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"2c9180895e172348015e18586a980026\",\n" +
                "      \"name\": \"商品信息\",\n" +
                "      \"parent_id\": \"2c9180895e172348015e1857eb460025\",\n" +
                "      \"route\": \"list\",\n" +
                "      \"code\": \"DEF\",\n" +
                "      \"summary\": null\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        Map parse = (Map)JSON.parse(res);
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


}
