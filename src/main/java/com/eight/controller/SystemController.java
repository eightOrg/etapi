package com.eight.controller;

import com.eight.trundle.Constants;
import com.eight.trundle.annotations.RouteHandler;
import com.eight.trundle.annotations.RouteMapping;
import com.eight.trundle.annotations.RouteMethod;
import com.eight.trundle.db.sqlExcute.DBHelper;
import com.eight.trundle.handle.HandleTemplet;
import com.eight.trundle.params.ParamUtil;
import com.eight.trundle.vertx.EventBusAddress;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
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

    /**
     * 登录接口
     */
    @RouteMapping(method = RouteMethod.GET)
    public Handler<RoutingContext> login(){
        return routingContext ->{
            DeliveryOptions options = new DeliveryOptions().addHeader("method", "login");
            JsonObject ob= ParamUtil.getParamJson(routingContext.request().params());
            logger.debug("params==========="+ob !=null?ob.toString():"null");
            routingContext.vertx().eventBus().<JsonObject>send(address, ob, options, result -> {
                JsonObject object = result.result().body();
                logger.debug("result==========="+object.encode());
                routingContext.response().end(object.encode());
            });
        };
    }
}
