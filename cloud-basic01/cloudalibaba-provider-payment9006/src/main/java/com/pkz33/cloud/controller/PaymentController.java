package com.pkz33.cloud.controller;

import com.pkz33.cloud.entities.CommonResult;
import com.pkz33.cloud.entities.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;

    public static HashMap<Long, Payment> hashMap = new HashMap<>();
    static {
        hashMap.put(1L,new Payment(1L,"serial0001"));
        hashMap.put(2L,new Payment(2L,"serial0002"));
        hashMap.put(3L,new Payment(3L,"serial0003"));
    }

    @GetMapping(value = "/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id){
        Payment payment = hashMap.get(id);
        CommonResult<Payment> result = new CommonResult<>(200,"server port："+serverPort,payment);
        return result;
    }
}
