package com.pkz33.cloud.service.impl;

import com.pkz33.cloud.dao.StorageDao;
import com.pkz33.cloud.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class StorageServiceImpl implements StorageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageServiceImpl.class);

    @Resource
    private StorageDao storageDao;

    @Override
    public void decrease(Long productId, Integer count) {
        LOGGER.info("********** storage: decrease count ......");
        storageDao.decrease(productId,count);
    }
}
