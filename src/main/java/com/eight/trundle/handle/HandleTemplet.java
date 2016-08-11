package com.eight.trundle.handle;

import com.eight.trundle.params.ParamUtil;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;

/**
 * Created by miaoch on 2016/8/11.
 */
public class HandleTemplet {

    /**
     * 控制层模板
     * @param method
     * @param evetBusAddress
     * @param logger
     * @return
     */
    public static Handler<RoutingContext> getHandle (String method, String evetBusAddress, Logger logger) {
        return new Handler<RoutingContext>() {

            @Override
            public void handle(RoutingContext routingContext) {
                DeliveryOptions options = new DeliveryOptions().addHeader("method", method);
                JsonObject ob= ParamUtil.getParamJson(routingContext.request().params());
                logger.debug("params==========="+ob.toString());
                routingContext.vertx().eventBus().<JsonObject>send(evetBusAddress, ob, options, result -> {
                    JsonObject object = result.result().body();
                    logger.debug("result==========="+object.encode());
                    routingContext.response().end(object.encode());
                });
            }
        };
    }
}
