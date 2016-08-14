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
    public Handler<RoutingContext> demoMethod(){
        String method = "demoMethod";
        return HandleTemplet.getHandler(method, demoAddress, logger);
    }

    //@RouteMapping(method = RouteMethod.GET)
    public void blockingMethod(RoutingContext routingContext) {
        DeliveryOptions options = new DeliveryOptions().addHeader("method", "blockingMethod");
        JsonObject ob = new JsonObject();
        ob.put("token", routingContext.request().getParam("token"));
        //String token = routingContext.request().getHeader("token");
        //if (token != null) {
            //ob.put("token", token);
        //}
        routingContext.vertx().eventBus().<JsonObject>send(demoAddress, ob, options, result -> {
            JsonObject object = result.result().body();
            if (object.getInteger("code").equals(Constants.CODE_OK)) {
                routingContext.next();
            } else {
                routingContext.response().end(object.encode());
            }
        });
    }

    //@RouteMapping(method = RouteMethod.GET)
    public void login(RoutingContext routingContext){
        DeliveryOptions options = new DeliveryOptions().addHeader("method", "login");
        JsonObject ob= ParamUtil.getParamJson(routingContext.request().params());
        routingContext.vertx().eventBus().<JsonObject>send(demoAddress, ob, options, result -> {
            JsonObject object = result.result().body();
            logger.debug("result==========="+object.encode());
            if (object.getInteger("code").equals(Constants.CODE_OK)) {
                routingContext.response().putHeader("sessionId", "123");
            }
            routingContext.response().end(object.encode());
        });
    }
}
