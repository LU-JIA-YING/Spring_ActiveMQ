package com.example.Spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//  https://github.com/shirayner/java-knowledge-hierarchy/blob/master/doc/spring-boot/SpringBoot_02_%E6%9E%84%E5%BB%BARESTful%20API%E4%B8%8E%E5%8D%95%E5%85%83%E6%B5%8B%E8%AF%95.md
//  https://eamonyin.blog.csdn.net/article/details/112434830
//  https://blog.csdn.net/weixin_38405253/article/details/112855657
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
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build(); //?????????MockMvc??????
    }

    @Test
    @DisplayName("?????? getAllMgniJsonAndXml()")
    void getAllMgniJsonAndXml() throws Exception {

        // ??????get??????
        RequestBuilder request = get("/api/mgn/findAllMgniJsonAndXml");

        // ??????get??????
        mockMvc.perform(request)
                .andExpect(status().isOk());  // ????????????????????????????????????????????????200

        System.out.println("?????? Mgni Find All !");
    }

//    @Test
//    @DisplayName("?????? getAllMgniJsonAndXml()")
//    void getAllMgniJsonAndXml() throws Exception {
//
//        // ??????get??????
//        RequestBuilder request = get("/api/mgn/findAllMgniJsonAndXml");
//
//        // ??????get??????
//        mockMvc.perform(request)
//                .andDo(print());// .andDo(print())??????????????????????????????
//
//        System.out.println("?????? Mgni Find All !");
//    }

//    @Test
//    void getAllMgniJsonAndXml() throws Exception {
//
//        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
//                .get("/api/mgn/findAllMgniJsonAndXml")
//        );
//        // Assert (????????????)
//        // ??????????????? http ????????? response body ??? json ???????????? name ??????????????????
//        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
//        resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
//    }

//===========================================================================================
//  3A ?????????????????? Arrange??????????????????Act???????????????Assert??????????????????

    @Test
    @DisplayName("?????? searchTargetMgni()")
    void searchTargetMgni() throws Exception {

        String response ="[{\"id\":\"MGI20221013094256109\",\"time\":\"2022-10-13 09:42:56\",\"type\":\"1\",\"cmNo\":\"9\",\"kacType\":\"1\",\"bankNo\":\"999\",\"ccy\":\"TWD\",\"pvType\":\"1\",\"bicaccNo\":\"0000000\",\"amt\":30.0000,\"ctName\":\"Joey\",\"ctTel\":\"1234578\",\"status\":\"0\",\"cashiList\":[{\"id\":\"MGI20221013094256109\",\"accNo\":\"1\",\"ccy\":\"TWD\",\"amt\":10.0000},{\"id\":\"MGI20221013094256109\",\"accNo\":\"2\",\"ccy\":\"TWD\",\"amt\":20.0000}],\"iType\":\"1\",\"pReason\":\"money\",\"uTime\":\"2022-10-13 09:42:56\"}]";

        String requestBoby = "{\"id\":\"MGI20221013094256109\"}";
        ResultActions resultActions =
                // perform(request) ??????????????????????????????get(url) ??? request ?????????
                mockMvc.perform(
                                post("/api/mgn/search/Mgni/1").content(requestBoby).contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON)
                        )
                        // ??????????????????????????????
                        .andDo(print());
        // ??????????????????
        String actual = resultActions.andReturn().getResponse().getContentAsString();

        //  ????????????
        Assert.assertEquals(response, actual);
    }
}