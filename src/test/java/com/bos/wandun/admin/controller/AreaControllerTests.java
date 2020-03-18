package com.bos.wandun.admin.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaControllerTests {

    // 用于模拟网络请求的测试
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
       // mockMvc = MockMvcBuilders.standaloneSetup(new AreaController()).build();
    }


    /**
     * 模拟网络的请求及打印
     */
    @Test
    public void addArea() throws Exception {

        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "6");
        params.add("hello", "world");
        String responseString = mockMvc.perform(
                MockMvcRequestBuilders.get("/hello")
                        // 设置参数
                        .params(params)
                        // 设置headers
                        // 代表发送端发送的数据格式
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        // 代表客户端希望接受的数据类型格式
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                // 把请求及返回内容打印出来
                .andDo(print())
                // 添加表达式测试，测试状态是否正常
                .andExpect(status().isOk())
                // 判断返回文本是否包含
                .andExpect(content().string(containsString("hello")))
                // 判断返回文本是否相等
                //.andExpect(content().string(equalTo("hello")))
                // 返回值是json时判断的内容
                .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value("独孤九剑"))
                // 将response的content返回
                .andReturn().getResponse().getContentAsString();



        System.out.println("result : "+responseString);

    }
}
