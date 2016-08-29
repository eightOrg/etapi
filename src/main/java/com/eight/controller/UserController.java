package com.eight.controller;

import com.eight.trundle.Constants;
import com.eight.trundle.annotations.RouteHandler;
import com.eight.trundle.db.controller.BaseController;
import com.eight.trundle.vertx.EventBusAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by miaoch on 2016/8/9.
 */
@RouteHandler(Constants.SERVICE_USER)
public class UserController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private String address = EventBusAddress.EBUser;
    @Override
    public Logger getLogger() {
        return logger;
    }
    @Override
    public String getAddress() {
        return address;
    }
}
