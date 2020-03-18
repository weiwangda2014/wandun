package com.bos.wandun.service;

import com.bos.wandun.entity.Menu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuServiceTests {

    @Autowired
    private MenuService menuService;


    @Test
    public void testQueryService() {
        List<Menu> menus = menuService.findByUserId(3L);
        for (Menu m : menus) {
            System.out.println(m.getName());
        }
    }

    /**
     * 初始化数据
     */
    @Test
    public void testAddInitService() {
        Menu menu0 = new Menu();
        menu0.setId(1L);
        menu0.setName("功能菜单");
        menuService.save(menu0);

        Menu menu1 = new Menu();
        menu1.setId(2L);
        menu1.setName("系统设置");
        menu1.setParent(menuService.find(1L));
        menuService.save(menu1);

        /**
         * 菜单
         */
        Menu m3 = new Menu();
        m3.setId(3L);
        m3.setParent(menuService.find(2L));
        m3.setName("菜单管理");
        m3.setHref("/menus/index");
        m3.setPermission("admin:menu:list");
        menuService.save(m3);

        Menu m4 = new Menu();
        m4.setId(4L);
        m4.setParent(menuService.find(3L));
        m4.setName("添加");
        m4.setHref(null);
        m4.setPermission("admin:menu:add");
        menuService.save(m4);


        Menu m5 = new Menu();
        m5.setId(5L);
        m5.setParent(menuService.find(3L));
        m5.setName("查看");
        m5.setHref(null);
        m5.setPermission("admin:menu:view");
        menuService.save(m5);

        Menu m6 = new Menu();
        m6.setId(6L);
        m6.setParent(menuService.find(3L));
        m6.setName("修改");
        m6.setHref(null);
        m6.setPermission("admin:menu:edit");
        menuService.save(m6);

        Menu m7 = new Menu();
        m7.setId(7L);
        m7.setParent(menuService.find(3L));
        m7.setName("删除");
        m7.setHref(null);
        m7.setPermission("admin:menu:del");
        menuService.save(m7);


        /**
         * 用户
         */
        Menu m1 = new Menu();
        m1.setId(8L);
        m1.setParent(menuService.find(2L));
        m1.setName("用户管理");
        m1.setHref("/users/index");
        m1.setPermission("admin:user:list");
        menuService.save(m1);

        Menu m9 = new Menu();
        m9.setId(9L);
        m9.setParent(menuService.find(8L));
        m9.setName("添加");
        m9.setHref(null);
        m9.setPermission("admin:user:add");
        menuService.save(m9);


        Menu m10 = new Menu();
        m10.setId(10L);
        m10.setParent(menuService.find(8L));
        m10.setName("查看");
        m10.setHref(null);
        m10.setPermission("admin:user:view");
        menuService.save(m10);

        Menu m11 = new Menu();
        m11.setId(11L);
        m11.setParent(menuService.find(8L));
        m11.setName("修改");
        m11.setHref(null);
        m11.setPermission("admin:user:edit");
        menuService.save(m11);

        Menu m12 = new Menu();
        m12.setId(12L);
        m12.setParent(menuService.find(8L));
        m12.setName("删除");
        m12.setHref(null);
        m12.setPermission("admin:user:del");
        menuService.save(m12);


        /**
         * 机构
         */

        Menu m13 = new Menu();
        m13.setId(13L);
        m13.setParent(menuService.find(2L));
        m13.setName("机构管理");
        m13.setHref("/offices/index");
        m13.setPermission("admin:office:index");
        menuService.save(m13);

        Menu m14 = new Menu();
        m14.setId(14L);
        m14.setParent(menuService.find(13L));
        m14.setName("添加");
        m14.setHref(null);
        m14.setPermission("admin:office:add");
        menuService.save(m14);


        Menu m15 = new Menu();
        m15.setId(15L);
        m15.setParent(menuService.find(13L));
        m15.setName("查看");
        m15.setHref(null);
        m15.setPermission("admin:office:view");
        menuService.save(m15);

        Menu m16 = new Menu();
        m16.setId(16L);
        m16.setParent(menuService.find(13L));
        m16.setName("修改");
        m16.setHref(null);
        m16.setPermission("admin:office:edit");
        menuService.save(m16);

        Menu m17 = new Menu();
        m17.setId(17L);
        m17.setParent(menuService.find(13L));
        m17.setName("删除");
        m17.setHref(null);
        m17.setPermission("admin:office:del");
        menuService.save(m17);


        /**
         *角色
         */
        Menu m18 = new Menu();
        m18.setId(18L);
        m18.setParent(menuService.find(2L));
        m18.setName("角色管理");
        m18.setHref("/roles/index");
        m18.setPermission("admin:role:list");
        menuService.save(m18);

        Menu m19 = new Menu();
        m19.setId(19L);
        m19.setParent(menuService.find(18L));
        m19.setName("添加");
        m19.setHref(null);
        m19.setPermission("admin:role:add");
        menuService.save(m19);


        Menu m20 = new Menu();
        m20.setId(20L);
        m20.setParent(menuService.find(18L));
        m20.setName("查看");
        m20.setHref(null);
        m20.setPermission("admin:role:view");
        menuService.save(m20);

        Menu m21 = new Menu();
        m21.setId(21L);
        m21.setParent(menuService.find(18L));
        m21.setName("修改");
        m21.setHref(null);
        m21.setPermission("admin:role:edit");
        menuService.save(m21);

        Menu m22 = new Menu();
        m22.setId(22L);
        m22.setParent(menuService.find(18L));
        m22.setName("删除");
        m22.setHref(null);
        m22.setPermission("admin:role:del");
        menuService.save(m22);

        Menu m23 = new Menu();
        m23.setId(23L);
        m23.setParent(menuService.find(18L));
        m23.setName("权限设置");
        m23.setHref(null);
        m23.setPermission("admin:role:auth");
        menuService.save(m23);

        Menu m24 = new Menu();
        m24.setId(24L);
        m24.setParent(menuService.find(18L));
        m24.setName("分配用户");
        m24.setHref(null);
        m24.setPermission("admin:role:assign");
        menuService.save(m24);

        /**
         * 区域
         */

        Menu m25 = new Menu();
        m25.setId(25L);
        m25.setParent(menuService.find(2L));
        m25.setName("区域管理");
        m25.setHref("/areas/index");
        m25.setPermission("admin:area:list");
        menuService.save(m25);

        Menu m26 = new Menu();
        m26.setId(26L);
        m26.setParent(menuService.find(25L));
        m26.setName("添加");
        m26.setHref(null);
        m26.setPermission("admin:area:add");
        menuService.save(m26);


        Menu m27 = new Menu();
        m27.setId(27L);
        m27.setParent(menuService.find(25L));
        m27.setName("查看");
        m27.setHref(null);
        m27.setPermission("admin:area:view");
        menuService.save(m27);

        Menu m28 = new Menu();
        m28.setId(28L);
        m28.setParent(menuService.find(25L));
        m28.setName("修改");
        m28.setHref(null);
        m28.setPermission("admin:area:edit");
        menuService.save(m28);

        Menu m29 = new Menu();
        m29.setId(29L);
        m29.setParent(menuService.find(25L));
        m29.setName("删除");
        m29.setHref(null);
        m29.setPermission("admin:area:del");
        menuService.save(m29);


        /**
         * 字典
         */

        Menu m30 = new Menu();
        m30.setId(30L);
        m30.setParent(menuService.find(2L));
        m30.setName("字典管理");
        m30.setHref("/dicts/index");
        m30.setPermission("admin:dict:list");
        menuService.save(m30);

        Menu m31 = new Menu();
        m31.setId(31L);
        m31.setParent(menuService.find(30L));
        m31.setName("添加");
        m31.setHref(null);
        m31.setPermission("admin:dict:add");
        menuService.save(m31);


        Menu m32 = new Menu();
        m32.setId(32L);
        m32.setParent(menuService.find(30L));
        m32.setName("查看");
        m32.setHref(null);
        m32.setPermission("admin:dict:view");
        menuService.save(m32);

        Menu m33 = new Menu();
        m33.setId(33L);
        m33.setParent(menuService.find(30L));
        m33.setName("修改");
        m33.setHref(null);
        m33.setPermission("admin:dict:edit");
        menuService.save(m33);

        Menu m34 = new Menu();
        m34.setId(34L);
        m34.setParent(menuService.find(30L));
        m34.setName("删除");
        m34.setHref(null);
        m34.setPermission("admin:dict:del");
        menuService.save(m34);
    }


}