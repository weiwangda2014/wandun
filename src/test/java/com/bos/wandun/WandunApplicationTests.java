package com.bos.wandun;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootTest
@EnableTransactionManagement
@ComponentScan("com.bos.wandun.entity")//启动类默认只扫描注解的类的同包以及子包下的类，若不再同一个包下，需要指明扫描
class WandunApplicationTests {

    @Test
    void contextLoads() {
    }


}
