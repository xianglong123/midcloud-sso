package com.cmft.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 上午10:54 2021/4/1
 * @version: V1.0
 * @review:
 */
public class PermissionDO {
    private String id;
    private String name;
    private String parentId;
    private String route;
    private String code;
    private String summary;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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
