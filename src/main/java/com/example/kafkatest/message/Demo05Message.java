package com.example.kafkatest.message;

import lombok.Data;

/**
 * @description:
 * @author: QiuJJ
 * @create: 2020-08-31
 **/
@Data
public class Demo05Message {

    public static final String TOPIC = "DEMO_05";

    private Integer id;
}
