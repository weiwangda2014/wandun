package com.bos.wandun.service;

import com.bos.wandun.entity.User;
import com.bos.wandun.security.session.RedisSessionDAO;

public interface SystemService {

    void updateUserLoginInfo(User user);

    User getUserByLoginName(String loginName);

    RedisSessionDAO getRedisSessionDAO();
}
