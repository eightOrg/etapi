package com.eight.controller;

import com.eight.trundle.Constants;
import com.eight.trundle.annotations.LogMsg;
import com.eight.trundle.handle.HandleTemplet;
import com.eight.trundle.ob.BaseOb;
import com.eight.trundle.params.ParamUtil;
import com.eight.trundle.vertx.EventBusAddress;
import com.eight.trundle.annotations.RouteHandler;
import com.eight.trundle.annotations.RouteMapping;
import com.eight.trundle.annotations.RouteMethod;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by miaoch on 2016/8/9.
 */
@RouteHandler(Constants.SERVICE_DEMO)
public class DemoController {
    private Logger logger = LoggerFactory.getLogger(DemoController.class);
    private String demoAddress = EventBusAddress.EBDemo;

    @RouteMapping(method = RouteMethod.GET)
    public Handler<RoutingContext> selectPageList(){
        String method = "selectPageList";
        return HandleTemplet.getHandler(method, demoAddress, logger);
    }

}
