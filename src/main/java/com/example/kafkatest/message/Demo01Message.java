package com.example.kafkatest.message;

import lombok.Data;

/**
 * @description:
 * @author: QiuJJ
 * @create: 2020-08-29
 **/
@Data
public class Demo01Message {

    public static final String TOPIC = "DEMO_01";

    private Integer id;
}
