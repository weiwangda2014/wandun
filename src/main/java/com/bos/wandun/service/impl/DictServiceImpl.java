package com.bos.wandun.service.impl;

import com.bos.wandun.Filter;
import com.bos.wandun.Order;
import com.bos.wandun.Page;
import com.bos.wandun.Pageable;
import com.bos.wandun.dao.DictDao;
import com.bos.wandun.entity.Dict;
import com.bos.wandun.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("dictServiceImpl")
public class DictServiceImpl extends BaseServiceImpl<Dict, Long> implements DictService {
    private final DictDao dictDao;

    public DictServiceImpl(DictDao dictDao) {
        this.dictDao = dictDao;
    }

    @Transactional(readOnly = true)
    @Override
    public Dict find(Long aLong) {
        return super.find(aLong);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Dict> findAll() {
        return super.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Dict> findList(Long... longs) {
        return super.findList(longs);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Dict> findList(Integer count, List<Filter> filters, List<Order> orders) {
        return super.findList(count, filters, orders);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Dict> findList(Integer first, Integer count, List<Filter> filters, List<Order> orders) {
        return super.findList(first, count, filters, orders);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Dict> findPage(Pageable pageable) {
        return super.findPage(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public long count() {
        return super.count();
    }

    @Transactional(readOnly = true)
    @Override
    public long count(Filter... filters) {
        return super.count(filters);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean exists(Long aLong) {
        return super.exists(aLong);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean exists(Filter... filters) {
        return super.exists(filters);
    }

    @Transactional
    @Override
    public Dict save(Dict entity) {
        return super.save(entity);
    }

    @Transactional
    @Override
    public Dict update(Dict entity) {
        return super.update(entity);
    }

    @Transactional
    @Override
    public Dict update(Dict entity, String... ignoreProperties) {
        return super.update(entity, ignoreProperties);
    }

    @Transactional
    @Override
    public void delete(Long aLong) {
        super.delete(aLong);
    }

    @Transactional
    @Override
    public void delete(Long... longs) {
        super.delete(longs);
    }

    @Transactional
    @Override
    public void delete(Dict entity) {
        super.delete(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public Dict findByLabel(String label) {
        Dict dict = dictDao.findByLabel(label);
        return dict;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Dict> findByType(String type) {
        List<Filter> filters = new ArrayList<>();

        Filter filter = new Filter();

        filter.setProperty("type");
        filter.setValue(type);
        filter.setOperator(Filter.Operator.eq);
        filters.add(filter);

        return dictDao.findList(null, null, filters, null);
    }
}
