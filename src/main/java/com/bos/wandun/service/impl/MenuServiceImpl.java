package com.bos.wandun.service.impl;

import com.bos.wandun.Filter;
import com.bos.wandun.Order;
import com.bos.wandun.Page;
import com.bos.wandun.Pageable;
import com.bos.wandun.dao.MenuDao;
import com.bos.wandun.entity.Area;
import com.bos.wandun.entity.Menu;
import com.bos.wandun.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu, Long> implements MenuService {

    private final MenuDao menuDao;

    public MenuServiceImpl(MenuDao menuDao) {
        this.menuDao = menuDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Menu> findByUserId(Long id) {
        return menuDao.findByUserId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Menu find(Long aLong) {
        return super.find(aLong);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return super.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Menu> findList(Long[] longs) {
        return super.findList(longs);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Menu> findList(Integer count, List<Filter> filters, List<Order> orders) {
        return super.findList(count, filters, orders);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Menu> findList(Integer first, Integer count, List<Filter> filters, List<Order> orders) {
        return super.findList(first, count, filters, orders);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Menu> findPage(Pageable pageable) {
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
    public Menu save(Menu entity) {

        return super.save(entity);
    }

    @Override
    @Transactional
    public Menu update(Menu entity) {

        return super.update(entity);
    }

    @Override
    @Transactional
    public Menu update(Menu entity, String... ignoreProperties) {
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
    public void delete(Menu entity) {
        super.delete(entity);
    }


}
