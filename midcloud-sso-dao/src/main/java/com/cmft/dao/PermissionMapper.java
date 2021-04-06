package com.cmft.dao;

import com.cmft.bean.PermissionDO;
import com.cmft.bean.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 上午11:42 2021/3/14
 * @version: V1.0
 * @review:
 */
@Mapper
public interface PermissionMapper {

    List<PermissionDO> queryPermissionByUserId(String userId);

    String queryUrlByCode(String code);

}
