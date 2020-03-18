package com.bos.wandun.service;

import com.bos.wandun.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Fant.J.
 */

public interface UserService extends BaseService<User, Long> {
    User findUserByName(String username);

    List<User> findUserByOfficeId(Long officeId);
}