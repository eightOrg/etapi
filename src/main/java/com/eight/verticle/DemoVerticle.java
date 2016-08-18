package com.eight.verticle;

import com.eight.service.DemoService;
import com.eight.service.impl.DemoServiceImpl;
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
public class DemoVerticle extends AbstractVerticle {
    Logger logger = LoggerFactory.getLogger(DemoVerticle.class);
    private final DemoService service;

    public DemoVerticle(final ApplicationContext context) {
        service = (DemoService) context.getBean(DemoService.class);
    }

    @Override
    public void start() throws Exception {
        super.start();

        Handler<Message<JsonObject>> msgHandler = HandleTemplet.getMsgHandler(service, logger);
        vertx.eventBus().<JsonObject>consumer(EventBusAddress.EBDemo).handler(msgHandler);
    }
}
