package com.eight.controller;

import com.eight.trundle.Constants;
import com.eight.trundle.annotations.RouteHandler;
import com.eight.trundle.vertx.EventBusAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 用于服务不需要session的请求
 * 不需要继承BaseController
 * Created by miaoch on 2016/8/9.
 */
@RouteHandler(Constants.SERVICE_SYSTEM)
public class SystemController {
    private Logger logger = LoggerFactory.getLogger(SystemController.class);
    private String address = EventBusAddress.EBSystem;

}
