package com.demo.controller;
import com.demo.untils.EventBusAddress;
import com.demo.untils.ParamUtil;
import com.demo.untils.annotations.RouteMethod;
import com.demo.untils.annotations.RouteHandler;
import com.demo.untils.annotations.RouteMapping;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Created by miaoch on 2016/8/9.
 */
@RouteHandler("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @RouteMapping(method = RouteMethod.GET)
    public Handler<RoutingContext> getUserById(){
        return routingContext -> {
            DeliveryOptions options = new DeliveryOptions().addHeader("method", "getUserById");
            JsonObject ob = new JsonObject();
            ob= ParamUtil.getParamJson(routingContext.request().params());
            //ob.put("id",routingContext.request().getParam("id"));
            logger.debug("params==========="+ob.toString());
            routingContext.vertx().eventBus().<JsonObject>send(EventBusAddress.EBUser, ob, options, result -> {
                if (result.succeeded()) {
                    logger.debug("result==========="+result.toString());
                    JsonObject object = result.result().body();
                    routingContext.response().end(object.encode());
                } else {
                    logger.debug("faild==========="+result.cause().toString());
                    routingContext.response().setStatusCode(500).write(result.cause().toString()).end();
                }
            });
        };
    }

    @RouteMapping(method = RouteMethod.GET)
    public Handler<RoutingContext> getUserList(){
        return routingContext -> {
            DeliveryOptions options = new DeliveryOptions().addHeader("method", "getUserList");
            JsonObject ob = new JsonObject();
            ob= ParamUtil.getParamJson(routingContext.request().params());
            logger.debug("params==========="+ob.toString());
            routingContext.vertx().eventBus().<JsonObject>send(EventBusAddress.EBUser, ob, options, result -> {
                if (result.succeeded()) {
                    logger.debug("result==========="+result.toString());
                    JsonObject object = result.result().body();
                    routingContext.response().end(object.encode());
                } else {
                    logger.debug("faild==========="+result.cause().toString());
                    routingContext.response().setStatusCode(500).write(result.cause().toString()).end();
                }
            });
        };
    }
}
