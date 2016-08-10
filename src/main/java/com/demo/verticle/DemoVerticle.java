package com.demo.verticle;

import com.demo.service.UserService;
import com.demo.untils.EventBusAddress;
import com.demo.untils.ob.BaseOb;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * Created by miaoch on 2016/8/10.
 */
public class DemoVerticle extends AbstractVerticle {
    Logger logger = LoggerFactory.getLogger(DemoVerticle.class);
    private final UserService service;

    public DemoVerticle(final ApplicationContext context) {
        service = (UserService) context.getBean(UserService.class);
    }

    private Handler<Message<JsonObject>> msgHandler() {
        return msg -> {
            BaseOb result=new BaseOb();

            String method = msg.headers().get("method");
            JsonObject job = msg.body();
            try {
                JsonObject json = (JsonObject) service.getClass().getMethod(method, JsonObject.class).invoke(service, job);
                msg.reply(json);
            } catch (Exception e) {
                e.printStackTrace();
                result.setMsg("数据处理失败！");
                result.setFlag(false);
                result.setCode(500);
                logger.error("method："+method+";错误信息："+e.getMessage());
                msg.reply(new JsonObject(Json.encode(result)));
            }
        };
    }

    @Override
    public void start() throws Exception {
        super.start();

        vertx.eventBus().<JsonObject>consumer(EventBusAddress.EBUser).handler(msgHandler());
    }
}
