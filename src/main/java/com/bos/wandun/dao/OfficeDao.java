package com.bos.wandun.dao;

import com.bos.wandun.entity.Office;

import java.util.List;

public interface OfficeDao extends BaseDao<Office, Long> {
    /**
     * 查找下级地区
     *
     * @param office      地区
     * @param recursive 是否递归
     * @param count     数量
     * @return 下级地区
     */
    List<Office> findChildren(Office office, boolean recursive, Integer count);
}
