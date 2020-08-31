package com.example.kafkatest.message;

import lombok.Data;

/**
 * @description:
 * @author: QiuJJ
 * @create: 2020-08-31
 **/
@Data
public class Demo02Message {

    public static final String TOPIC = "DEMO_02";

    private Integer id;
}
