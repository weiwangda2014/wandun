package com.bos.wandun.service;

import com.bos.wandun.entity.Area;
import com.bos.wandun.entity.Office;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OfficeServiceTests {

    @Autowired
    private OfficeService officeService;

    @Autowired
    private AreaService areaService;

    @Test
    public void testService() {
        Area area = areaService.find(1L);
        Office office = new Office();
        office.setId(1L);
        office.setName("中央办公厅");
        office.setAddress("中央办公厅");
        office.setEmail("wei.wangda@gmail.com");
        office.setArea(area);
        office.setParent(null);
        office.setCode("100000");
        office.setFax("0871-6522927");
        office.setMaster("111");
        office.setUseable("1");
        officeService.save(office);
    }


    @Test
    public void testUpdateService() {
        Office office = officeService.find(1L);
        officeService.update(office);
    }
}
