package com.bos.wandun.service.impl;

import com.bos.wandun.Filter;
import com.bos.wandun.Order;
import com.bos.wandun.Page;
import com.bos.wandun.Pageable;
import com.bos.wandun.dao.UserDao;
import com.bos.wandun.entity.User;
import com.bos.wandun.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("userServiceImpl")
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    public static final String ALGORITHM_NAME = "md5"; // 基础散列算法
    public static final int HASH_ITERATIONS = 2; // 自定义散列次数

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByName(String username) {
        return userDao.findByLoginName(username);
    }

    @Override
    public List<User> findUserByOfficeId(Long officeId) {
        return userDao.findUserByOfficeId(officeId);
    }

    private void encryptPassword(User user) {
        // 如果新密码为空，则不更换密码
        if (StringUtils.isNotBlank(user.getNewPassword())) {
            // 随机字符串作为salt因子，实际参与运算的salt我们还引入其它干扰因子
            user.setSalt(randomNumberGenerator.nextBytes().toHex());
            String newPassword = new SimpleHash(ALGORITHM_NAME, user.getNewPassword(),
                    ByteSource.Util.bytes(user.getCredentialsSalt()), HASH_ITERATIONS).toHex();
            user.setPassword(newPassword);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User find(Long aLong) {
        return super.find(aLong);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return super.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findList(Long[] longs) {
        return super.findList(longs);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findList(Integer count, List<Filter> filters, List<Order> orders) {
        return super.findList(count, filters, orders);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findList(Integer first, Integer count, List<Filter> filters, List<Order> orders) {
        return super.findList(first, count, filters, orders);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findPage(Pageable pageable) {
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
    public User save(User entity) {
        encryptPassword(entity);
        return super.save(entity);
    }

    @Override
    @Transactional
    public User update(User entity) {
        encryptPassword(entity);
        if (StringUtils.isBlank(entity.getNewPassword())) {
            return super.update(entity, "newPassword", "password");
        }
        return super.update(entity);
    }

    @Override
    @Transactional
    public User update(User entity, String... ignoreProperties) {
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
    public void delete(User entity) {
        super.delete(entity);
    }
}