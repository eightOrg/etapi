package com.eight.controller;

import com.eight.trundle.Constants;
import com.eight.trundle.annotations.RouteHandler;
import com.eight.trundle.annotations.RouteMapping;
import com.eight.trundle.annotations.RouteMethod;
import com.eight.trundle.db.controller.BaseController;
import com.eight.trundle.handle.HandleTemplet;
import com.eight.trundle.vertx.EventBusAddress;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
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

    /**
     * 添加好友。
     */
    @RouteMapping(method = RouteMethod.POST)
    public Handler<RoutingContext> addFriend(){
        String method = "addFriend";
        return HandleTemplet.getHandler(method, getAddress(), getLogger());
    }
}
