package com.eight.controller;

import com.eight.trundle.controller.ControllerTemplet;
import com.eight.trundle.vertx.EventBusAddress;
import com.eight.trundle.annotations.RouteHandler;
import com.eight.trundle.annotations.RouteMapping;
import com.eight.trundle.annotations.RouteMethod;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by miaoch on 2016/8/9.
 */
@RouteHandler("/demo")
public class DemoController {
    private Logger logger = LoggerFactory.getLogger(DemoController.class);
    private String demoAddress = EventBusAddress.EBDemo;

    @RouteMapping(method = RouteMethod.GET)
    public Handler<RoutingContext> demoMethod(){
        String method = "demoMethod";
        return ControllerTemplet.getHandle(method, demoAddress, logger);
    }
}
