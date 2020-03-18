package com.bos.wandun.service.impl;

import com.bos.wandun.Filter;
import com.bos.wandun.Order;
import com.bos.wandun.Page;
import com.bos.wandun.Pageable;
import com.bos.wandun.dao.OfficeDao;
import com.bos.wandun.entity.Area;
import com.bos.wandun.entity.Office;
import com.bos.wandun.service.OfficeService;
import com.bos.wandun.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OfficeServiceImpl extends BaseServiceImpl<Office, Long> implements OfficeService {
    private final OfficeDao officeDao;

    public OfficeServiceImpl(OfficeDao officeDao) {
        this.officeDao = officeDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Office find(Long aLong) {
        return super.find(aLong);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Office> findAll() {
        return super.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Office> findList(Long[] longs) {
        return super.findList(longs);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Office> findList(Integer count, List<Filter> filters, List<Order> orders) {
        return super.findList(count, filters, orders);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Office> findList(Integer first, Integer count, List<Filter> filters, List<Order> orders) {
        return super.findList(first, count, filters, orders);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Office> findPage(Pageable pageable) {
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
    public Office save(Office entity) {
        return super.save(entity);
    }

    @Override
    @Transactional
    public Office update(Office entity) {
        return super.update(entity);
    }

    @Override
    @Transactional
    public Office update(Office entity, String... ignoreProperties) {
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
    public void delete(Office entity) {
        super.delete(entity);
    }

    @Override
    public List<Office> findByisAll(Boolean isAll) {
        if (isAll != null && isAll) {
            return super.findAll();
        }
        return UserUtils.findCurrentOffice();

    }

}
