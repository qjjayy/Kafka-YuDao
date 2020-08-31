package com.example.kafkatest.message;

import lombok.Data;

/**
 * @description:
 * @author: QiuJJ
 * @create: 2020-08-31
 **/
@Data
public class Demo03Message {

    public static final String TOPIC = "DEMO_03";

    private Integer id;
}
