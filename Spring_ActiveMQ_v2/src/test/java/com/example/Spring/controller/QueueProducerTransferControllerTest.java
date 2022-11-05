package com.example.Spring.controller;

import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//  https://github.com/shirayner/java-knowledge-hierarchy/blob/master/doc/spring-boot/SpringBoot_02_%E6%9E%84%E5%BB%BARESTful%20API%E4%B8%8E%E5%8D%95%E5%85%83%E6%B5%8B%E8%AF%95.md
//  https://eamonyin.blog.csdn.net/article/details/112434830
//  https://blog.csdn.net/weixin_38405253/article/details/112855657
//  測試先把WriteProducerController的@Component註解掉 因不註解需要手動輸入資料造成無法做RestfulController測試
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class QueueProducerTransferControllerTest {

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
//        mockMvc = MockMvcBuilders.standaloneSetup(new QueueProducerTransferController()).build();
//        mockMvc = MockMvcBuilders.standaloneSetup(wac).build();
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build(); //初始化MockMvc对象
    }

    @Test
    @DisplayName("測試  getAllMgni()")
    public void getAllMgni() throws Exception {

        // 构造get请求
        RequestBuilder request = get("/getAll");

        // 执行get请求
        mockMvc.perform(request)
                .andExpect(status().isOk());  // 對請求結果進行期望，回覆的狀態為200

        System.out.println("測試 Mgni Find All !");
    }

//    @Test
//    @DisplayName("測試 getAllMgniJsonAndXml()")
//    void getAllMgniJsonAndXml() throws Exception {
//
//        // 构造get请求
//        RequestBuilder request = get("/api/mgn/findAllMgniJsonAndXml");
//
//        // 执行get请求
//        mockMvc.perform(request)
//                .andDo(print());// .andDo(print())輸出整個回應結果訊息
//
//        System.out.println("測試 Mgni Find All !");
//    }

//    @Test
//    void getAllMgniJsonAndXml() throws Exception {
//
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
//                .get("/api/mgn/findAllMgniJsonAndXml")
//        );
//        // Assert (驗證結果)
//        // 驗證回傳的 http 狀態和 response body 的 json 格式中的 name 欄位是否正確
//        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
//        resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
//    }

}