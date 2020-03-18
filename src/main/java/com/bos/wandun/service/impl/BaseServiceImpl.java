package com.bos.wandun.service.impl;

import com.bos.wandun.Filter;
import com.bos.wandun.Order;
import com.bos.wandun.Page;
import com.bos.wandun.Pageable;
import com.bos.wandun.dao.BaseDao;
import com.bos.wandun.entity.BaseEntity;
import com.bos.wandun.service.BaseService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * Service - 基类
 *
 * @author BOS Team
 * @version 4.0
 */
@Transactional
public abstract class BaseServiceImpl<T extends BaseEntity<ID, T>, ID extends Serializable> implements BaseService<T, ID> {

    /**
     * 更新忽略属性
     */
    private static final String[] UPDATE_IGNORE_PROPERTIES = new String[]{BaseEntity.CREATE_DATE_PROPERTY_NAME, BaseEntity.MODIFY_DATE_PROPERTY_NAME, BaseEntity.VERSION_PROPERTY_NAME, BaseEntity.REMARKS_PROPERTY_NAME};

    /**
     * BaseDao
     */
    @Autowired
    private BaseDao<T, ID> baseDao;

    @Autowired
    protected void setBaseDao(BaseDao<T, ID> baseDao) {
        this.baseDao = baseDao;
    }

    @Transactional(readOnly = true)
    public T find(ID id) {
        return baseDao.find(id);
    }

    @Transactional(readOnly = true)
    public List<T> findAll() {
        return findList(null, null, null, null);
    }

    @Transactional(readOnly = true)
    public List<T> findList(ID... ids) {
        List<T> result = new ArrayList<T>();
        if (ids != null) {
            for (ID id : ids) {
                T entity = find(id);
                if (entity != null) {
                    result.add(entity);
                }
            }
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<T> findList(Integer count, List<Filter> filters, List<Order> orders) {
        return findList(null, count, filters, orders);
    }

    @Transactional(readOnly = true)
    public List<T> findList(Integer first, Integer count, List<Filter> filters, List<Order> orders) {
        return baseDao.findList(first, count, filters, orders);
    }

    @Transactional(readOnly = true)
    public Page<T> findPage(Pageable pageable) {
        return baseDao.findPage(pageable);
    }

    @Transactional(readOnly = true)
    public long count() {
        return count(new Filter[]{});
    }

    @Transactional(readOnly = true)
    public long count(Filter... filters) {
        return baseDao.count(filters);
    }

    @Transactional(readOnly = true)
    public boolean exists(ID id) {
        return baseDao.find(id) != null;
    }

    @Transactional(readOnly = true)
    public boolean exists(Filter... filters) {
        return baseDao.count(filters) > 0;
    }

    @Transactional
    public T save(T entity) {
        Assert.notNull(entity);
        //  Assert.isTrue(entity.isNew());

        baseDao.persist(entity);
        return entity;
    }

    @Transactional
    public T update(T entity) {
        Assert.notNull(entity);
        Assert.isTrue(!entity.isNew());
        boolean isManaged = !baseDao.isManaged(entity);
        if (isManaged) {
            T persistant = baseDao.find(baseDao.getIdentifier(entity));
            if (persistant != null) {
                copyProperties(entity, persistant, UPDATE_IGNORE_PROPERTIES);
            }
            return persistant;
        }
        return entity;
    }

    @Transactional
    public T update(T entity, String... ignoreProperties) {
        Assert.notNull(entity);
        Assert.isTrue(!entity.isNew());
        Assert.isTrue(!baseDao.isManaged(entity));

        T persistant = baseDao.find(baseDao.getIdentifier(entity));
        if (persistant != null) {
            copyProperties(entity, persistant, (String[]) ArrayUtils.addAll(ignoreProperties, UPDATE_IGNORE_PROPERTIES));
        }
        return update(persistant);
    }

    @Transactional
    public void delete(ID id) {
        delete(baseDao.find(id));
    }

    @Transactional
    public void delete(ID... ids) {
        if (ids != null) {
            for (ID id : ids) {
                delete(baseDao.find(id));
            }
        }
    }

    @Transactional
    public void delete(T entity) {
        if (entity != null) {
            baseDao.remove(baseDao.isManaged(entity) ? entity : baseDao.merge(entity));
        }
    }

    /**
     * 拷贝对象属性
     *
     * @param source           源
     * @param target           目标
     * @param ignoreProperties 忽略属性
     */
    protected void copyProperties(T source, T target, String... ignoreProperties) {
        Assert.notNull(source);
        Assert.notNull(target);

        PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(target);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String propertyName = propertyDescriptor.getName();
            Method readMethod = propertyDescriptor.getReadMethod();
            Method writeMethod = propertyDescriptor.getWriteMethod();

            System.out.println("错误点" + propertyName);
            if (ArrayUtils.contains(ignoreProperties, propertyName) || readMethod == null || writeMethod == null || !baseDao.isLoaded(source, propertyName)) {
                continue;
            }
            try {
                Object sourceValue = readMethod.invoke(source);
                writeMethod.invoke(target, sourceValue);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e.getMessage(), e);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e.getMessage(), e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }

}