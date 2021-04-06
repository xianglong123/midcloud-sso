package com.cmft.bean;

import java.util.Date;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 下午8:13 2021/4/2
 * @version: V1.0
 * @review:
 */
public class RoleDO {
    private String id;
    private String name;
    private Date cteTm;
    private Date uptTm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
