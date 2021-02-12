package com.pkz33.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.pkz33.cloud.entities.CommonResult;
import com.pkz33.cloud.entities.Payment;
import com.pkz33.cloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class CircleBreakerController {
    public static final String SERVICE_URL = "http://nacos-payment-provider";

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("/consumer/fallback/{id}")
    // @SentinelResource(value = "fallback")
    // @SentinelResource(value = "fallback", fallback = "handlerFallback") //业务异常
    // @SentinelResource(value = "fallback", blockHandler = "blockHandler") //sentinel控制台配置
    @SentinelResource(value = "fallback", fallback = "handlerFallback", blockHandler = "blockHandler",
            exceptionsToIgnore = {IllegalArgumentException.class})
    public CommonResult<Payment> fallback(@PathVariable("id") Long id){
        CommonResult<Payment> result = restTemplate.getForObject(SERVICE_URL+"/paymentSQL/"+id,CommonResult.class,id);
        if (id == 4){
            throw new IllegalArgumentException("IllegalAtgumentException");
        }else if(result.getData() == null){
            throw new NullPointerException("NullPointerException");
        }
        return result;
    }

    public CommonResult handlerFallback(@PathVariable("id") Long id, Throwable e){
        Payment payment = new Payment(id, "null");
        return new CommonResult(444,"handlerFallback, exception："+e.getMessage(),payment);
    }

    public CommonResult blockHandler(@PathVariable("id") Long id, BlockException e){
        Payment payment = new Payment(id, "null");
        return new CommonResult(445,"blockHandler, exception："+e.getMessage(), payment);
    }

    @Resource
    private PaymentService paymentService;

    @GetMapping(value = "/consumer/openfeign/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id){
        return paymentService.paymentSQL(id);
    }
}
