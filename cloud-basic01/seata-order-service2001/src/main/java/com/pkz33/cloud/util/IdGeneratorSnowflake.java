package com.pkz33.cloud.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class IdGeneratorSnowflake {
    private long workerId = 0L;
    private long datacenterId = 1L;
    private Snowflake snowflake = IdUtil.createSnowflake(workerId,datacenterId);

    @PostConstruct
    public void init(){
        try {
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
            log.info("current workerId: {}",workerId);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("fail to get workerId",e);
            workerId = NetUtil.getLocalhostStr().hashCode();
        }
    }

    public synchronized long snowflakeId(){
        return snowflake.nextId();
    }

    public synchronized long snowflakeId(long workerId, long datacenterId){
        Snowflake snowflake = IdUtil.createSnowflake(workerId,datacenterId);
        return snowflake.nextId();
    }

    public static void main(String[] args) {
        IdGeneratorSnowflake idGeneratorSnowflake = new IdGeneratorSnowflake();
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        for (int i=1;i<=20;i++){
            threadPool.submit(()->{
                System.out.println("******"+idGeneratorSnowflake.snowflakeId());
            });
        }
        threadPool.shutdown();
    }
}
