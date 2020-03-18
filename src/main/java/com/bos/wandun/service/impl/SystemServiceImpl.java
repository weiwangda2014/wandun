package com.bos.wandun.service.impl;

import com.bos.wandun.dao.UserDao;
import com.bos.wandun.entity.User;
import com.bos.wandun.security.session.RedisSessionDAO;
import com.bos.wandun.service.SystemService;
import com.bos.wandun.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
@Service("systemServiceImpl")
public class SystemServiceImpl implements SystemService {
    private final UserDao userDao;
    private final RedisSessionDAO redisSessionDAO;

    public SystemServiceImpl(UserDao userDao, RedisSessionDAO redisSessionDAO) {
        this.userDao = userDao;
        this.redisSessionDAO = redisSessionDAO;
    }

    @Override
    @Transactional
    public void updateUserLoginInfo(User user) {
        // 保存上次登录信息
        user.setOldLoginIp(user.getLoginIp());
        user.setOldLoginDate(user.getLoginDate());
        // 更新本次登录信息
        user.setLoginIp(UserUtils.getSession().getHost());
        user.setLoginDate(new Date());
        userDao.updateLoginInfo(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByLoginName(String loginName) {
        return userDao.findByLoginName(loginName);
    }

    @Override
    public RedisSessionDAO getRedisSessionDAO() {
        return redisSessionDAO;
    }
}
