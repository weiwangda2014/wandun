package com.bos.wandun.dao;

import com.bos.wandun.entity.Area;

import java.util.List;

public interface AreaDao extends BaseDao<Area, Long> {
    /**
     * 查找顶级地区
     *
     * @param count
     *            数量
     * @return 顶级地区
     */
    List<Area> findRoots(Integer count);

    /**
     * 查找上级地区
     *
     * @param area
     *            地区
     * @param recursive
     *            是否递归
     * @param count
     *            数量
     * @return 上级地区
     */
    List<Area> findParents(Area area, boolean recursive, Integer count);

    /**
     * 查找下级地区
     *
     * @param area      地区
     * @param recursive 是否递归
     * @param count     数量
     * @return 下级地区
     */
    List<Area> findChildren(Area area, boolean recursive, Integer count);
}
