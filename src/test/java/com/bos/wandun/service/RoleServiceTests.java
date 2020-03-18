package com.bos.wandun.service;

import com.bos.wandun.dao.RoleDao;
import com.bos.wandun.entity.Menu;
import com.bos.wandun.entity.Office;
import com.bos.wandun.entity.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleServiceTests {
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private OfficeService officeService;

    @Test
    public void testAddService() {

        Role role = new Role();
        Office office = officeService.find(1L);
        role.setName("管理员");
        role.setEnname("Administrator");
        role.setDataScope("3");
        role.setOffice(office);
        role.setMenus(new HashSet<>(menuService.findAll()));
        role.setOffices(new HashSet<>(officeService.findAll()));
        roleService.save(role);
    }

    @Test
    public void testUpdateService() {

        /**Role**/
        Role role = roleService.find(5L);
        Office office = officeService.find(7L);

        role.setName("管理员12222");
        role.setEnname("Administrator111111");
        role.setDataScope("2");
        role.setOffice(office);
        role.setMenus(new HashSet<>(menuService.findList(new Long[]{2L, 3L})));
        role.setOffices(new HashSet<>(officeService.findList(new Long[]{1L})));
        roleService.update(role);
    }

    @Test
    public void testFindService() {


        Set<Menu> areaList = roleService.find(1L).getMenus();

        System.out.println(areaList);
    }
}