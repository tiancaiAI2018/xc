package com.xuecheng.mange_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.mange_cms_client.service.CmsPageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ConsumerPostPage {
    private static final  Logger LOGGER = LoggerFactory.getLogger(ConsumerPostPage.class);
    @Autowired
    private CmsPageService cmsPageService;
    //监听队列
    @RabbitListener(queues = {"${xc.mq.rabbitmq.queue}"})
    public void postPage(String msg){
        LOGGER.info("receive cms post_page messgae is:{}",msg);
        Map map = JSON.parseObject(msg, Map.class);
        if(!map.containsKey("pageId")) ExceptionCast.cast(CommonCode.MQ_MESSAGE_INVALID);
        String pageId = (String) map.get("pageId");
        //保存文件至服务器
        try {
            cmsPageService.savePageToServerPath(pageId);
        }catch (Exception e){
            LOGGER.error("save post_page is fail the pageId is:{}",pageId);
            LOGGER.error("catch error {}",e.getMessage());
        }
    }
}