package com.pkz33.cloud.service;

import com.pkz33.cloud.domain.Order;

public interface OrderService {
    void create(Order order);

    String getIDBySnowflake();
}
