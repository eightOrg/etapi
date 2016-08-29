package com.eight.verticle;

import com.eight.service.SystemService;
import com.eight.trundle.ob.BaseOb;
import com.eight.trundle.vertx.EventBusAddress;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * Created by miaoch on 2016/8/10.
 */
public class SystemVerticle extends AbstractVerticle {
    Logger logger = LoggerFactory.getLogger(SystemVerticle.class);
    private final SystemService service;

    public SystemVerticle(final ApplicationContext context) {
        service = (SystemService) context.getBean(SystemService.class);
    }
    private Handler<Message<JsonObject>> msgHandler() {
        return msg -> {
            String method = msg.headers().get("method");
            JsonObject ob = msg.body();
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

    @Override
    public void start() throws Exception {
        super.start();
        vertx.eventBus().<JsonObject>consumer(EventBusAddress.EBSystem).handler(msgHandler());
    }
}
