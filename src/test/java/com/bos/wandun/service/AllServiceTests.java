package com.bos.wandun.service;

import com.bos.wandun.config.PasswordHelper;
import com.bos.wandun.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AllServiceTests {

    @Autowired
    private AreaService areaService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordHelper passwordHelper;
    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void testAddService() {
        /**Area**/
        Area area = new Area();
        area.setId(1L);
        area.setCode("11");
        area.setType("1");

        areaService.save(area);

        /**Office**/
        area = areaService.find(1L);

        Office office = new Office();
        office.setId(1L);
        office.setName("222");
        office.setAddress("22222");
        office.setEmail("wei.wangda@gmail.com");
        office.setArea(area);
        officeService.save(office);

        /**Menu**/


        Menu menu = new Menu();
        menu.setId(1L);
        menu.setName("系统设置");
        menuService.save(menu);

        Menu m1 = new Menu();
        m1.setId(2L);
        m1.setParent(menuService.find(1L));
        m1.setName("角色管理");
        m1.setHref("/sys/role/");
        m1.setPermission("sys:role:list");
        menuService.save(m1);

        Menu m5 = new Menu();
        m5.setId(3L);
        m5.setParent(menuService.find(2L));
        m5.setName("添加");
        m5.setHref(null);
        m5.setPermission("sys:role:add");
        menuService.save(m5);


        Menu m2 = new Menu();
        m2.setId(4L);
        m2.setParent(menuService.find(2L));
        m2.setName("查看");
        m2.setHref(null);
        m2.setPermission("sys:role:view");
        menuService.save(m2);

        Menu m3 = new Menu();
        m3.setId(5L);
        m3.setParent(menuService.find(2L));
        m3.setName("修改");
        m3.setHref(null);
        m3.setPermission("sys:role:edit");
        menuService.save(m3);

        Menu m4 = new Menu();
        m4.setId(6L);
        m4.setParent(menuService.find(2L));
        m4.setName("删除");
        m4.setHref(null);
        m4.setPermission("sys:role:del");
        menuService.save(m4);

        Menu m6 = new Menu();
        m6.setId(7L);
        m6.setParent(menuService.find(2L));
        m6.setName("权限设置");
        m6.setHref(null);
        m6.setPermission("sys:role:auth");
        menuService.save(m6);

        Menu m7 = new Menu();
        m7.setId(8L);
        m7.setParent(menuService.find(2L));
        m7.setName("分配用户");
        m7.setHref(null);
        m7.setPermission("sys:role:assign");
        menuService.save(m7);



        List menuList = Arrays.asList(menuService.findAll());
        Set menuSets = new HashSet(menuList);

        /**Role**/
        Role role = new Role();
        office = officeService.find(1L);
        role.setId(1L);
        role.setName("管理员");
        role.setOffice(office);
        role.setMenus(menuSets);


        List officeList = Arrays.asList(officeService.findAll());
        Set officeSets = new HashSet(officeList);

        role.setOffices(officeSets);
        roleService.save(role);


        /****User****/
        for (int i = 0; i < 500; i++) {

            User user = new User();
            office = officeService.findAll().get(0);
            user.setName("王玮" + i);
            user.setNo("" + i);
            user.setLoginName("wangwei" + i);
            user.setEmail("wei.wangda@gmail.com");
            user.setPassword("" + i);
            user.setCompany(office);
            user.setOffice(office);
            List staffsList = Arrays.asList(roleService.findAll());
            Set result = new HashSet(staffsList);
            user.setRoles(result);

            userService.save(user);
        }
    }


    @Test
    public void testFindService() {


        Set<Area> areaList = areaService.find(1L).getChildren();


        System.out.println(areaList);
    }
}