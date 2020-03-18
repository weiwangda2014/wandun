package com.bos.wandun.dao.impl;

import com.bos.wandun.dao.UserDao;
import com.bos.wandun.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Repository("userDaoImpl")
public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findByLoginName(String loginName) {
        if (StringUtils.isEmpty(loginName)) {
            return null;
        }
        try {
            String jpql = "select user from User user where lower(user.loginName) = lower(:loginName)";
            return entityManager.createQuery(jpql, User.class).setParameter("loginName", loginName).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void updateLoginInfo(User user) {
        String jpql = "UPDATE User SET oldLoginIp=:oldLoginIp,oldLoginDate=:oldLoginDate,loginDate=:loginDate WHERE id=:id";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("oldLoginIp", user.getLoginIp());
        query.setParameter("oldLoginDate", user.getLoginDate());
        query.setParameter("loginDate", user.getLoginDate());
        query.setParameter("id", user.getId());
        query.executeUpdate();
    }

    @Override
    public List<User> findUserByOfficeId(Long officeId) {
        if (officeId == null) {
            return null;
        }
        try {
            String jpql = "select user from User user where lower(user.office.id) = lower(:officeId)";
            return entityManager.createQuery(jpql, User.class).setParameter("officeId", officeId).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}