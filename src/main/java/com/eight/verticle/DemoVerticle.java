package com.eight.verticle;

import com.eight.pojo.Demo;
import com.eight.service.DemoService;
import com.eight.trundle.Constants;
import com.eight.trundle.db.pojo.Identifiable;
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
public class DemoVerticle<T extends Identifiable> extends AbstractVerticle {
    Logger logger = LoggerFactory.getLogger(DemoVerticle.class);
    private final DemoService service;

    public DemoVerticle(final ApplicationContext context) {
        service = (DemoService) context.getBean(DemoService.class);
    }

    private Handler<Message<JsonObject>> msgHandler() {
        return msg -> {
            BaseOb result=new BaseOb();

            String method = msg.headers().get("method");
            JsonObject ob = msg.body();
            try {
                JsonObject json = (JsonObject) service.getClass().getMethod(method, JsonObject.class).invoke(service, ob);
                msg.reply(json);
            } catch (Exception e) {
                e.printStackTrace();
                result = BaseOb.getFaildOb();
                logger.error("method："+method+";错误信息："+e.getMessage());
                msg.reply(new JsonObject(Json.encode(result)));
            }
        };
    }

    @Override
    public void start() throws Exception {
        super.start();

        vertx.eventBus().<JsonObject>consumer(EventBusAddress.EBDemo).handler(msgHandler());
    }
}
