package com.bos.wandun.service;

import com.bos.wandun.entity.Area;
import com.bos.wandun.entity.Dict;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DictServiceTests {

    @Autowired
    private DictService dictService;

    @Test
    public void testAddService() {

        Dict sys_data_scope1 = new Dict();
        sys_data_scope1.setLabel("所有数据");
        sys_data_scope1.setType("sys_data_scope");
        sys_data_scope1.setValue("1");
        dictService.save(sys_data_scope1);


        Dict sys_data_scope2 = new Dict();
        sys_data_scope2.setLabel("所在公司及以下数据");
        sys_data_scope2.setType("sys_data_scope");
        sys_data_scope2.setValue("2");
        dictService.save(sys_data_scope2);

        Dict sys_data_scope3 = new Dict();
        sys_data_scope3.setLabel("所在公司数据");
        sys_data_scope3.setType("sys_data_scope");
        sys_data_scope3.setValue("3");
        dictService.save(sys_data_scope3);

        Dict sys_data_scope4 = new Dict();
        sys_data_scope4.setLabel("所在部门及以下数据");
        sys_data_scope4.setType("sys_data_scope");
        sys_data_scope4.setValue("4");
        dictService.save(sys_data_scope4);

        Dict sys_data_scope5 = new Dict();
        sys_data_scope5.setLabel("所在部门数据");
        sys_data_scope5.setType("sys_data_scope");
        sys_data_scope5.setValue("5");
        dictService.save(sys_data_scope5);

        Dict sys_data_scope6 = new Dict();
        sys_data_scope6.setLabel("仅本人数据");
        sys_data_scope6.setType("sys_data_scope");
        sys_data_scope6.setValue("6");
        dictService.save(sys_data_scope6);

        Dict sys_data_scope7 = new Dict();
        sys_data_scope7.setLabel("按明细设置");
        sys_data_scope7.setType("sys_data_scope");
        sys_data_scope7.setValue("7");
        dictService.save(sys_data_scope7);


        Dict sys_office_type1 = new Dict();
        sys_office_type1.setLabel("公司");
        sys_office_type1.setType("sys_office_type");
        sys_office_type1.setValue("1");
        dictService.save(sys_office_type1);

        Dict sys_office_type2 = new Dict();
        sys_office_type2.setLabel("部门");
        sys_office_type2.setType("sys_office_type");
        sys_office_type2.setValue("2");
        dictService.save(sys_office_type2);

        Dict sys_office_type3 = new Dict();
        sys_office_type3.setLabel("小组");
        sys_office_type3.setType("sys_office_type");
        sys_office_type3.setValue("3");
        dictService.save(sys_office_type3);

        Dict sys_office_type4 = new Dict();
        sys_office_type4.setLabel("其它");
        sys_office_type4.setType("sys_office_type");
        sys_office_type4.setValue("4");
        dictService.save(sys_office_type4);


        Dict sys_area_type0 = new Dict();
        sys_area_type0.setLabel("国家");
        sys_area_type0.setType("sys_area_type");
        sys_area_type0.setValue("0");
        dictService.save(sys_area_type0);

        Dict sys_area_type1 = new Dict();
        sys_area_type1.setLabel("省份、直辖市");
        sys_area_type1.setType("sys_area_type");
        sys_area_type1.setValue("1");
        dictService.save(sys_area_type1);

        Dict sys_area_type2 = new Dict();
        sys_area_type2.setLabel("地市");
        sys_area_type2.setType("sys_area_type");
        sys_area_type2.setValue("2");
        dictService.save(sys_area_type2);

        Dict sys_area_type3 = new Dict();
        sys_area_type3.setLabel("区县");
        sys_area_type3.setType("sys_area_type");
        sys_area_type3.setValue("3");
        dictService.save(sys_area_type3);

        Dict sys_area_type4 = new Dict();
        sys_area_type4.setLabel("乡镇");
        sys_area_type4.setType("sys_area_type");
        sys_area_type4.setValue("4");
        dictService.save(sys_area_type4);

        Dict sys_area_type5 = new Dict();
        sys_area_type5.setLabel("村委会/社区");
        sys_area_type5.setType("sys_area_type");
        sys_area_type5.setValue("5");
        dictService.save(sys_area_type5);


        Dict yes_no = new Dict();
        yes_no.setLabel("是");
        yes_no.setType("yes_no");
        yes_no.setValue("1");
        dictService.save(yes_no);

        Dict yes_no1 = new Dict();
        yes_no1.setLabel("否");
        yes_no1.setType("yes_no");
        yes_no1.setValue("0");
        dictService.save(yes_no1);


        Dict sys_user_type1 = new Dict();
        sys_user_type1.setLabel("系统管理");
        sys_user_type1.setType("sys_user_type");
        sys_user_type1.setValue("1");
        dictService.save(sys_user_type1);

        Dict sys_user_type2 = new Dict();
        sys_user_type2.setLabel("部门经理");
        sys_user_type2.setType("sys_user_type");
        sys_user_type2.setValue("2");
        dictService.save(sys_user_type2);

        Dict sys_user_type3 = new Dict();
        sys_user_type3.setLabel("普通用户");
        sys_user_type3.setType("sys_user_type");
        sys_user_type3.setValue("3");
        dictService.save(sys_user_type3);


        Dict show_hide = new Dict();
        show_hide.setLabel("显示");
        show_hide.setType("show_hide");
        show_hide.setValue("1");
        dictService.save(show_hide);


        Dict show_hide0 = new Dict();
        show_hide0.setLabel("隐藏");
        show_hide0.setType("show_hide");
        show_hide0.setValue("0");
        dictService.save(show_hide0);


        Dict sys_office_grade0 = new Dict();
        sys_office_grade0.setLabel("顶级");
        sys_office_grade0.setType("sys_office_grade");
        sys_office_grade0.setValue("0");
        dictService.save(sys_office_grade0);

        Dict sys_office_grade1 = new Dict();
        sys_office_grade1.setLabel("一级");
        sys_office_grade1.setType("sys_office_grade");
        sys_office_grade1.setValue("1");
        dictService.save(sys_office_grade1);

        Dict sys_office_grade2 = new Dict();
        sys_office_grade2.setLabel("二级");
        sys_office_grade2.setType("sys_office_grade");
        sys_office_grade2.setValue("2");
        dictService.save(sys_office_grade2);

        Dict sys_office_grade3 = new Dict();
        sys_office_grade3.setLabel("三级");
        sys_office_grade3.setType("sys_office_grade");
        sys_office_grade3.setValue("3");
        dictService.save(sys_office_grade3);

        Dict sys_office_grade4 = new Dict();
        sys_office_grade4.setLabel("四级");
        sys_office_grade4.setType("sys_office_grade");
        sys_office_grade4.setValue("4");
        dictService.save(sys_office_grade4);


    }


    @Test
    public void testFindService() {


        List<Dict> areaList = dictService.findAll();

        System.out.println(areaList);
    }
}