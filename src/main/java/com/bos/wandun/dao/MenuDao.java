package com.bos.wandun.dao;

import com.bos.wandun.entity.Area;
import com.bos.wandun.entity.Menu;

import java.util.List;

public interface MenuDao extends BaseDao<Menu, Long> {
    List<Menu> findByUserId(Long id);

    List<Menu> findByParentIdsLike(Menu m);

    /**
     * 查找下级地区
     *
     * @param menu      地区
     * @param recursive 是否递归
     * @param count     数量
     * @return 下级地区
     */
    List<Menu> findChildren(Menu menu, boolean recursive, Integer count);
}
