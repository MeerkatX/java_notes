package com.nuc.meerkatx.springbootstudy.queue;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName: KafkaConsumer
 * @Auther: MeerkatX
 * @Date: 2020-10-21 22:04
 * @Description:
 */
@Component
public class KafkaConsumer {
    /**
     * 监听demo主题，有消息就读取
     * @param message
     */
    @KafkaListener(topics = {"demo"})
    public void receiveMessage(String message) {
        System.out.println(message);
    }
}
