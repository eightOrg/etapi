package com.eight.verticle;

import com.eight.service.UserService;
import com.eight.trundle.handle.HandleTemplet;
import com.eight.trundle.vertx.EventBusAddress;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * Created by miaoch on 2016/8/10.
 */
public class UserVerticle extends AbstractVerticle {
    Logger logger = LoggerFactory.getLogger(UserVerticle.class);
    private final UserService service;

    public UserVerticle(final ApplicationContext context) {
        service = (UserService) context.getBean(UserService.class);
    }

    @Override
    public void start() throws Exception {
        super.start();

        Handler<Message<JsonObject>> msgHandler = HandleTemplet.getMsgHandler(service, logger);
        vertx.eventBus().<JsonObject>consumer(EventBusAddress.EBUser).handler(msgHandler);
    }
}
