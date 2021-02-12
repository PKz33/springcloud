package com.pkz33.cloud.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.pkz33.cloud.entities.CommonResult;
import com.pkz33.cloud.entities.Payment;

public class CustomerBlockHandler {
    public static CommonResult handlerException01(BlockException e){
        return new CommonResult(4444,"global handler exception 01");
    }

    public static CommonResult handlerException02(BlockException e){
        return new CommonResult(4444,"global handler exception 02");
    }
}
