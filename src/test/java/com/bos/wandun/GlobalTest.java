package com.bos.wandun;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GlobalTest {

    @Test
    @Transactional
    public void findService() {
        System.out.println(Global.getConfig("name"));
    }
}
