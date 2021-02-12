package com.pkz33.cloud.service;

import com.pkz33.cloud.entities.CommonResult;
import com.pkz33.cloud.entities.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentService {
    @Override
    public CommonResult<Payment> paymentSQL(Long id) {
        return new CommonResult<>(444,"PaymentFallbackService, 服务降级 ",new Payment(id,"errorSerial"));
    }
}
