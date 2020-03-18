package com.bos.wandun.dao;

import com.bos.wandun.entity.User;

import java.util.List;

/**
 * Dao - 管理员
 *
 * @author Hello King
 * @version 4.0
 */
public interface UserDao extends BaseDao<User, Long> {

    User findByLoginName(String loginName);


    void updateLoginInfo(User user);

    List<User> findUserByOfficeId(Long officeId);
}