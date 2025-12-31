package com.probase.potzr.SmartBanking.service;

import com.probase.potzr.SmartBanking.models.core.SMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SMSKafkaService {

    @Value("${kafka.sms.create.topic}")
    private String createKafkaSMSTopic;

    @Autowired
    private KafkaTemplate<Long, SMS> kafkaTemplate;

    public void saveSMSToKafkaQueue(SMS sms)
    {
        this.kafkaTemplate.send(createKafkaSMSTopic, sms);
    }
}
