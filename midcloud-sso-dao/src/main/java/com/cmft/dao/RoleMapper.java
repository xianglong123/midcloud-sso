package com.cmft.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 上午11:42 2021/3/14
 * @version: V1.0
 * @review:
 */
@Mapper
public interface RoleMapper {

    List<String> queryRoleIdByUserId(String userId);


}
