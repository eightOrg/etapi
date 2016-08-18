package com.eight.trundle.handle;

import com.eight.service.DemoService;
import com.eight.trundle.db.service.BaseService;
import com.eight.trundle.ob.BaseOb;
import com.eight.trundle.params.ParamUtil;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;

/**
 * Created by miaoch on 2016/8/11.
 */
public class HandleTemplet {

    /**
     * 控制层的handler模板
     * @param method
     * @param evetBusAddress
     * @param logger
     * @return
     */
    public static Handler<RoutingContext> getHandler (String method, String evetBusAddress, Logger logger) {
        return routingContext ->{
            long startTime = System.currentTimeMillis();
            DeliveryOptions options = new DeliveryOptions().addHeader("method", method);
            JsonObject ob= ParamUtil.getParamJson(routingContext.request().params());
            logger.debug("params==========="+ob.toString());
            routingContext.vertx().eventBus().<JsonObject>send(evetBusAddress, ob, options, result -> {
                JsonObject object = result.result().body();
                logger.debug("result==========="+object.encode());
                routingContext.response().end(object.encode());

                long time = System.currentTimeMillis() - startTime;
                String path = routingContext.request().path();
                String uri = routingContext.request().absoluteURI();
                String rMethod = routingContext.request().method().toString();
                //
            });
        };
    }

    /**
     * verticle 的handler模板
     * @param service
     * @param logger
     * @return
     */
    public static Handler<Message<JsonObject>> getMsgHandler (BaseService service, Logger logger) {
        return msg -> {
            String method = msg.headers().get("method");
            JsonObject ob = msg.body();
            //JsonObject->Identifiable
            //然后传入Identifiable
            try {
                JsonObject json = (JsonObject) service.getClass().getMethod(method, JsonObject.class).invoke(service, ob);
                msg.reply(json);
            } catch (Exception e) {
                e.printStackTrace();
                BaseOb result = BaseOb.getFaildOb();
                logger.error("method: "+method+";错误信息: "+e.getMessage());
                msg.reply(new JsonObject(Json.encode(result)));
            }
        };
    }
}
