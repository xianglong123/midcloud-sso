package com.cmft.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 上午10:54 2021/4/1
 * @version: V1.0
 * @review:
 */
@Data
public class UserDO {
    private String id;
    private String username;
    private String password;
    private Date cteTm;
    private Date uptTm;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCteTm() {
        return cteTm;
    }

    public void setCteTm(Date cteTm) {
        this.cteTm = cteTm;
    }

    public Date getUptTm() {
        return uptTm;
    }

    public void setUptTm(Date uptTm) {
        this.uptTm = uptTm;
    }
}
