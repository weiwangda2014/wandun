package com.bos.wandun.service.impl;

import com.bos.wandun.Filter;
import com.bos.wandun.Order;
import com.bos.wandun.Page;
import com.bos.wandun.Pageable;
import com.bos.wandun.dao.LogDao;
import com.bos.wandun.entity.Log;
import com.bos.wandun.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LogServiceImpl extends BaseServiceImpl<Log, Long> implements LogService {
    private final LogDao logDao;

    public LogServiceImpl(LogDao logDao) {
        this.logDao = logDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Log find(Long aLong) {
        return super.find(aLong);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Log> findAll() {
        return super.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Log> findList(Long[] longs) {
        return super.findList(longs);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Log> findList(Integer count, List<Filter> filters, List<Order> orders) {
        return super.findList(count, filters, orders);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Log> findList(Integer first, Integer count, List<Filter> filters, List<Order> orders) {
        return super.findList(first, count, filters, orders);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Log> findPage(Pageable pageable) {
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
    public Log save(Log entity) {
        return super.save(entity);
    }

    @Override
    @Transactional
    public Log update(Log entity) {
        return super.update(entity);
    }

    @Override
    @Transactional
    public Log update(Log entity, String... ignoreProperties) {
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
    public void delete(Log entity) {
        super.delete(entity);
    }

    @Override
    public void clear() {
        logDao.removeAll();
    }
}
