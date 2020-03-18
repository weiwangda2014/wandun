package com.bos.wandun.service.impl;

import com.bos.wandun.Filter;
import com.bos.wandun.Order;
import com.bos.wandun.Page;
import com.bos.wandun.Pageable;
import com.bos.wandun.dao.RoleDao;
import com.bos.wandun.entity.Role;
import com.bos.wandun.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("roleServiceImpl")
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements RoleService {

    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Role find(Long aLong) {
        return super.find(aLong);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return super.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findList(Long[] longs) {
        return super.findList(longs);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findList(Integer count, List<Filter> filters, List<Order> orders) {
        return super.findList(count, filters, orders);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findList(Integer first, Integer count, List<Filter> filters, List<Order> orders) {
        return super.findList(first, count, filters, orders);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Role> findPage(Pageable pageable) {
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
    public Role save(Role entity) {
        return super.save(entity);
    }

    @Override
    @Transactional
    public Role update(Role entity) {
        return super.update(entity);
    }

    @Override
    @Transactional
    public Role update(Role entity, String... ignoreProperties) {
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
    public void delete(Role entity) {
        super.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Role findRoleByName(String name) {

        Role role = roleDao.getByName(name);
        return role;
    }

    @Override
    @Transactional(readOnly = true)
    public Role findRoleByEnname(String enname) {
        Role role = roleDao.getByEnname(enname);
        return role;
    }
}
