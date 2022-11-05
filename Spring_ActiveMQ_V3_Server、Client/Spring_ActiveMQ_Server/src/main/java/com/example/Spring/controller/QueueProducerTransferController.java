package com.example.Spring.controller;

import com.example.Spring.controller.dto.request.ClearingMarginRequest;
import com.example.Spring.controller.dto.request.SearchCashiRequest;
import com.example.Spring.controller.dto.request.SearchMgniRequest;
import com.example.Spring.controller.dto.response.MgniResponse;
import com.example.Spring.controller.dto.response.StatusResponse;
import com.example.Spring.model.entity.Mgni;
import com.example.Spring.service.TransferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import javax.jms.Queue;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
public class QueueProducerTransferController {

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Queue queue;
    private static ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = Logger.getLogger(QueueProducerTransferController.class);

    @Autowired
    private TransferService transferService;


    @JmsListener(destination = "request.queue", containerFactory = "queueConnectionFactory")
    public void receiveQueue(String msg) throws Exception {

        JSONObject jsonObject = new JSONObject(msg);

        System.out.println("Message: " + msg);

        String requestType = jsonObject.getString("requestType");
        JSONObject requestBodyJson = jsonObject.getJSONObject("request");
        String requestBody = JSONObject.valueToString(requestBodyJson);


        String temp = "";

        switch (requestType) {
            case "1": {
                logger.info("執行Mgni查詢全部");
                temp = json(transferService.getAllMgnMgni());
                jmsTemplate(queue, temp);
                //  {"requestType":"1","request":{}}
                break;
            }
            case "2": {
                logger.info("Mgni新增");
                temp = json(transferService.createClearingMargin(mapper.readValue(requestBody, ClearingMarginRequest.class)));
                jmsTemplate(queue, temp);
                //  {"requestType":"2","request":{"cmNo":"9","kacType":"1","bankNo":"999","ccy":"TWD","pvType":"1","bicaccNo":"0000000","iType":"1","pReason":"money","clearingAccountList":[{"accNo":"1","amt":10},{"accNo":"2","amt":20}],"ctName":"Joey","ctTel":"12345678"}}
                break;
            }
            case "3": {
                logger.info("Mgni更新");
                temp = json(transferService.updateClearingMargin(jsonObject.getString("id"), mapper.readValue(requestBody, ClearingMarginRequest.class)));
                jmsTemplate(queue, temp);
                //  {"requestType":"3","id":"MGI20221004222746688","request":{"cmNo":"3","kacType":"1","bankNo":"999","ccy":"TWD","pvType":"1","bicaccNo":"0000000","iType":"1","pReason":"deposit some money in the bank","clearingAccountList":[{"accNo":"1","amt":10000},{"accNo":"2","amt":200}],"ctName":"Joey","ctTel":"12345678"}}
                break;
            }
            case "4": {
                logger.info("資料刪除");
                temp = json(transferService.deleteClearingMargin(requestBodyJson.getString("id")));
                jmsTemplate(queue, temp);
                //  {"requestType":"4","request":{"id":"MGI20221021171159182"}}
                break;
            }
            case "5": {
                logger.info("Mgni動態查詢");
                temp = json(transferService.searchTargetMgni(mapper.readValue(requestBody, SearchMgniRequest.class)));
                jmsTemplate(queue, temp);
                //  {"requestType":"5","request":{"id": null,"kacType": "1","ccy": "TWD","date": "20220929"}}
                break;
            }
            case "6": {
                logger.info("Cashi動態查詢");
                temp = json(transferService.searchTargetCashi(mapper.readValue(requestBody, SearchCashiRequest.class)));
                jmsTemplate(queue, temp);
                //  {"requestType":"6","request":{"id": null,"accNo": "000000","ccy": "TWD","page":1,"size":4}}
                break;
            }
            default: {
                temp = mapper.writeValueAsString("請輸入 1~7");
            }
        }
    }

//==============================================================================================

    @GetMapping("/getAll")
    public MgniResponse getAllMgni() throws Exception {

        MgniResponse response = transferService.getAllMgnMgni();
        jmsTemplate(queue, json(response));
        return response;
    }

//==============================================================================================


    //  Mgni複雜動態查詢及分頁，排序
    @PostMapping("/search/Mgni")
    public List<Mgni> searchTargetMgni(@RequestBody SearchMgniRequest request) throws Exception {

        List<Mgni> mgniList = transferService.searchTargetMgni(request);
        jmsTemplate(queue, json(mgniList));
        return mgniList;
    }

//==============================================================================================

    private static String json(Object object) throws Exception {

        //  https://stackoverflow.com/questions/69831153/jackson-objectmapper-findandregistermodules-not-working-to-serialise-localdate
        //  可以自動搜索所有模塊，不需要我們手動註冊
        mapper.findAndRegisterModules();

        //  把一个对象转化为json字符串
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    // 接收資料後送出
    private void jmsTemplate(Queue queue, String j) {
        jmsTemplate.convertAndSend(queue, j);
    }

}
