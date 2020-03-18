package com.bos.wandun.service.impl;

import com.bos.wandun.Filter;
import com.bos.wandun.Order;
import com.bos.wandun.Page;
import com.bos.wandun.Pageable;
import com.bos.wandun.dao.AreaDao;
import com.bos.wandun.entity.Area;
import com.bos.wandun.entity.NodeTree;
import com.bos.wandun.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("areaServiceImpl")
public class AreaServiceImpl extends BaseServiceImpl<Area, Long> implements AreaService {

    private final AreaDao areaDao;

    public AreaServiceImpl(AreaDao areaDao) {
        this.areaDao = areaDao;
    }

    @Transactional(readOnly = true)
    public List<Area> findRoots() {
        return areaDao.findRoots(null);
    }

    @Transactional(readOnly = true)
    public List<Area> findRoots(Integer count) {
        return areaDao.findRoots(count);
    }

    @Transactional(readOnly = true)
    public List<Area> findParents(Area area, boolean recursive, Integer count) {
        return areaDao.findParents(area, recursive, count);
    }

    @Transactional(readOnly = true)
    public List<Area> findChildren(Area area, boolean recursive, Integer count) {
        return areaDao.findChildren(area, recursive, count);
    }


    @Override
    @Transactional(readOnly = true)
    public Area find(Long aLong) {
        return super.find(aLong);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Area> findAll() {
        return super.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Area> findList(Long[] longs) {
        return super.findList(longs);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Area> findList(Integer count, List<Filter> filters, List<Order> orders) {
        return super.findList(count, filters, orders);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Area> findList(Integer first, Integer count, List<Filter> filters, List<Order> orders) {
        return super.findList(first, count, filters, orders);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Area> findPage(Pageable pageable) {
        return super.findPage(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return super.count();
    }

    @Override
    @Transactional(readOnly = true)
    public long count(Filter... filters) {
        return super.count(filters);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(Long aLong) {
        return super.exists(aLong);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(Filter... filters) {
        return super.exists(filters);
    }

    @Override
    @Transactional

    public Area save(Area entity) {
        return super.save(entity);
    }

    @Override
    @Transactional

    public Area update(Area entity) {
        return super.update(entity);
    }

    @Override
    @Transactional

    public Area update(Area entity, String... ignoreProperties) {
        return super.update(entity, ignoreProperties);
    }

    @Override
    @Transactional

    public void delete(Long aLong) {
        super.delete(aLong);
    }

    @Override
    @Transactional

    public void delete(Long[] longs) {
        super.delete(longs);
    }

    @Override
    @Transactional

    public void delete(Area entity) {
        super.delete(entity);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Area> findChildren(Long id) {
        Area area = areaDao.find(id);
        return areaDao.findChildren(area, false, null);
    }
}
