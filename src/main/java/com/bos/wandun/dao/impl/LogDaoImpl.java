package com.bos.wandun.dao.impl;

import com.bos.wandun.dao.AreaDao;
import com.bos.wandun.dao.LogDao;
import com.bos.wandun.entity.Area;
import com.bos.wandun.entity.Log;
import org.springframework.stereotype.Repository;


@Repository("logDaoImpl")
public class LogDaoImpl extends BaseDaoImpl<Log, Long> implements LogDao {
    @Override
    public void removeAll() {
        String jpql = "delete from Log log";
        entityManager.createQuery(jpql).executeUpdate();
    }
}
