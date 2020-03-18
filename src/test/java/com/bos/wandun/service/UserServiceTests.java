package com.bos.wandun.service;

import com.bos.wandun.entity.Office;
import com.bos.wandun.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest

@Slf4j
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private OfficeService officeService;
    @Autowired
    private RoleService roleService;

    public UserServiceTests() {
    }

    @Test
    public void testService() {
        User user = new User();
        Office office = officeService.find(1L);

        user.setName("王玮");
        user.setNo("111");
        user.setLoginName("wangwei1");
        user.setEmail("wei.wangda@gmail.com");
        user.setNewPassword("1");
        user.setPassword("1111111");
        user.setCompany(office);
        user.setOffice(office);
        user.setOldLoginName("111");
        user.setRoles(new HashSet<>(roleService.findAll()));
        user.setLoginFlag(0);


        userService.save(user);
    }
}
