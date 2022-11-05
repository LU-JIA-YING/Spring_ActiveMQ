# Spring_ActiveMQ

## ActiveMQ - Queue
- [ ] JMS
- [ ] AMQP
- [ ] Point-to-Point
- [ ] multiple connection

## ActiveMQ - Topic
- [ ] JMS
- [ ] AMQP
- [ ] Pub / Sub
- [ ] multiple connection

## Spring Cloud OpenFeign
- [ ] Rest Template
- [ ] WebService
- [ ] Remote Procedure Call (RPC)
- [ ] `@FeignClient`

## Test Cases In Java

[單元測試基礎概念](https://hackmd.io/@IDdlPCCwQoeX-9DvmEbLyw/rkE57lHXi)

- [ ] Unit Test
- [ ] 3A

## Sprint 3 實作
- [x] MQ (CRUD)
- [x] unit test

* [Spring Boot JMS ActiveMQ Producer and Consumer Example](https://javatute.com/spring-boot/spring-boot-jms-activemq-producer-and-consumer-example/)
* [詳解Springboot整合ActiveMQ（Queue和Topic兩種模式）](https://www.796t.com/article.php?id=18089)

下載 ActiveMQ : http://activemq.apache.org/components/classic/download/ (下載 zip 文件並將其解壓縮)  
執行 apache-activemq-5.17.2\bin\win64\activemq.bat的檔案  
ActiveMQ 控制台 : http://localhost:8161/admin/ 帳密admin  

* Spring_ActiveMQ => 使用Postmqn發送訊息
![image](https://user-images.githubusercontent.com/84082054/200127309-b62db102-9f38-40fd-92df-6eda7844ca8c.png)
* Spring_ActiveMQ_v2、v3 => 使用者在Scanner鍵盤輸入資料(V2跟V3差別在接收到的訊息用不一樣的方式解析:V2用Gson、V3用JSONObject)
![ActiveMQ_V2、V3](https://user-images.githubusercontent.com/84082054/200127318-e2153724-00ba-421c-81cb-0ea31331218b.jpg)

* 1:查詢Cashi全部資料/ 2:用Id查詢Mgni/ 3:用Id查詢Cashi/ 4:Mgni動態查詢/ 5:Mgni新增/ 6:Mgni更新/ 7:Mgni刪除
* 新增、修改、刪除時，Mgni跟Cashi會連動
