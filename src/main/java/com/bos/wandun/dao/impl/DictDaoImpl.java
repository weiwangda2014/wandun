package com.bos.wandun.dao.impl;

import com.bos.wandun.dao.DictDao;
import com.bos.wandun.entity.Dict;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;


@Repository("dictDaoImpl")
public class DictDaoImpl extends BaseDaoImpl<Dict, Long> implements DictDao {

    @Override
    public Dict findByLabel(String label) {
        if (StringUtils.isEmpty(label)) {
            return null;
        }
        try {
            String jpql = "select dict from Dict dict where lower(dict.label) = lower(:label)";
            return entityManager.createQuery(jpql, Dict.class).setParameter("label", label).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
