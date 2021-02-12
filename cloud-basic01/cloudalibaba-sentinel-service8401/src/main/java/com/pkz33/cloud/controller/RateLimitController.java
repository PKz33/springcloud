package com.pkz33.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.pkz33.cloud.entities.CommonResult;
import com.pkz33.cloud.entities.Payment;
import com.pkz33.cloud.myhandler.CustomerBlockHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {
    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handleException")
    public CommonResult byResource(){
        return new CommonResult(200,"按资源名称限流测试成功", new Payment(2021L, "serial001"));
    }

    public CommonResult handleException(BlockException e){
        return new CommonResult(444, e.getClass().getCanonicalName()+" 服务不可用");
    }

    @GetMapping("/rateLimit/byUrl")
    @SentinelResource(value = "byUrl")
    public CommonResult byUrl(){
        return new CommonResult(200, "按url限流测试成功", new Payment(2021L, "serial002"));
    }

    @GetMapping("/rateLimit/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler",
            blockHandlerClass = CustomerBlockHandler.class,
            blockHandler = "handlerException02")
    public CommonResult customerBlockHandler(){
        return new CommonResult(200, "customer block handler", new Payment(2021L, "serial003"));
    }
}
