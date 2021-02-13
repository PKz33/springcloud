package com.pkz33.cloud.service.impl;

import com.pkz33.cloud.dao.OrderDao;
import com.pkz33.cloud.domain.Order;
import com.pkz33.cloud.service.AccountService;
import com.pkz33.cloud.service.OrderService;
import com.pkz33.cloud.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;

    @Resource
    private StorageService storageService;

    @Resource
    private AccountService accountService;

    @GlobalTransactional(name = "pkz33-create-order",rollbackFor = Exception.class)
    @Override
    public void create(Order order) {
        log.info("********** order: create ......");
        orderDao.create(order);

        log.info("********** storage: decrease count ......");
        storageService.decrease(order.getProductId(),order.getCount());

        log.info("********** account: decrease money ......");
        accountService.decrease(order.getUserId(),order.getMoney());

        log.info("********** order: update status ......");
        orderDao.update(order.getUserId(),0);

        log.info("********** complete ......");
    }
}
