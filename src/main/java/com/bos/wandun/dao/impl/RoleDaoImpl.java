package com.bos.wandun.dao.impl;

import com.bos.wandun.dao.RoleDao;
import com.bos.wandun.entity.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;


@Repository("roleDaoImpl")
public class RoleDaoImpl extends BaseDaoImpl<Role, Long> implements RoleDao {
    @Override
    public Role getByEnname(String enname) {
        if (StringUtils.isEmpty(enname)) {
            return null;
        }
        try {
            String jpql = "select role from Role role where lower(role.enname) = lower(:enname)";
            return entityManager.createQuery(jpql, Role.class).setParameter("enname", enname).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Role getByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        try {
            String jpql = "select role from Role role where lower(role.name) = lower(:name)";
            return entityManager.createQuery(jpql, Role.class).setParameter("name", name).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
