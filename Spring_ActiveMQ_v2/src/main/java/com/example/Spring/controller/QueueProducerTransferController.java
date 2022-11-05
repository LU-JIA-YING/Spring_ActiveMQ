package com.example.Spring.controller;

import com.example.Spring.controller.dto.request.ClearingMarginRequest;
import com.example.Spring.controller.dto.request.Request;
import com.example.Spring.controller.dto.response.MgniResponse;
import com.example.Spring.controller.dto.response.StatusResponse;
import com.example.Spring.service.TransferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;

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

        System.out.println("Message: " + msg);
        Request request = new Gson().fromJson(msg, Request.class);

        String temp = "";

        switch (request.getRequestType()) {
            case "1": {
                logger.info("執行Mgni查詢全部");
                temp = mapper.writeValueAsString(getAllMgni());
                //  {"requestType":"1","request":{}}
                break;
            }
            case "2": {
                logger.info("Mgni新增");
                temp = mapper.writeValueAsString(createClearingMargin(request.getRequest()));
                //  {"requestType":"2","request":{"cmNo":"9","kacType":"1","bankNo":"999","ccy":"TWD","pvType":"1","bicaccNo":"0000000","iType":"1","pReason":"money","clearingAccountList":[{"accNo":"1","amt":10},{"accNo":"2","amt":20}],"ctName":"Joey","ctTel":"12345678"}}
                break;
            }
            case "3": {
                logger.info("Mgni更新");
                temp = mapper.writeValueAsString(updateMgni(request.getRequest().getId(), request.getRequest()));
                //  {"requestType":"3","request":{"id":"MGI20221004222746688","cmNo":"3","kacType":"1","bankNo":"999","ccy":"TWD","pvType":"1","bicaccNo":"0000000","iType":"1","pReason":"deposit some money in the bank","clearingAccountList":[{"accNo":"1","amt":10000},{"accNo":"2","amt":200}],"ctName":"Joey","ctTel":"12345678"}}
                break;
            }
            case "4": {
                logger.info("資料刪除");
                temp = mapper.writeValueAsString(deleteMgni(request.getRequest().getId()));
                //  {"requestType":"4","request":{"id":"MGI20221021171159182"}}
                break;
            }
            default: {
                temp = mapper.writeValueAsString("請輸入 1~4");
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

    //  新增結算保證金and交割結算基金帳戶
    public StatusResponse createClearingMargin(ClearingMarginRequest request) throws Exception {

        StatusResponse response = transferService.createClearingMargin(request);
        jmsTemplate(queue, json(response));
        return response;
    }

//==============================================================================================

    // 更新結算保證金and交割結算基金帳戶
    public StatusResponse updateMgni(String id, ClearingMarginRequest request) throws Exception {

        StatusResponse response = transferService.updateClearingMargin(id, request);
        jmsTemplate(queue, json(response));
        return response;
    }

//==============================================================================================

    // 刪除結算保證金and交割結算基金帳戶
    public StatusResponse deleteMgni(String id) throws Exception {

        String response = transferService.deleteClearingMargin(id);
        jmsTemplate(queue, json(response));
        return new StatusResponse(response);
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
