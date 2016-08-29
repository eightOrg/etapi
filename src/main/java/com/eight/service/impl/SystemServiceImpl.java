package com.eight.service.impl;

import com.eight.dao.SystemDao;
import com.eight.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 不需要session的一系列请求
 * 不需要继承BaseService
 * Created by miaoch on 2016/8/9.
 */
@Service("SystemService")
public class SystemServiceImpl implements SystemService {
    Logger logger = LoggerFactory.getLogger(SystemServiceImpl.class);

    @Autowired
    private SystemDao systemDao;

}