package com.bos.wandun.service;

import com.bos.wandun.entity.Area;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaServiceTests {

    @Autowired
    private AreaService areaService;

    @Test
    public void testAddService() {

        for (long i = 1; i < 5; i++) {
            Area area1 = areaService.find(i - 1);

            Area area = new Area();
            area.setId(i);
            area.setName("中国");
            area.setCode("100000");
            area.setType("1");
            area.setParent(area1);
            areaService.save(area);
        }

    }

    @Test
    public void testUpdateService() {
        Area area2 = areaService.find(1L);
        Area area1 = areaService.find(4L);
        area1.setParent(area2);
        areaService.update(area1);
    }

    @Test
    public void testFindService() {


        Set<Area> areaList = areaService.find(1L).getChildren();

        System.out.println(areaList);
    }
}