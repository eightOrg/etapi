package com.eight;

import com.eight.verticle.DemoVerticle;
import com.eight.verticle.HttpServerVerticle;
import com.eight.verticle.SystemVerticle;
import com.eight.verticle.UserVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by miaoch on 2016/8/10.
 */
public class EightRunner {
    private static Logger logger = LoggerFactory.getLogger(EightRunner.class);

    public static void main(String args[]){
        ApplicationContext context = new ClassPathXmlApplicationContext("DatabaseSource.xml");

        logger.debug("=======================Runner  Deployment======================");

        final Vertx vertx = Vertx.vertx();
        //暂时不启用DemoVerticle
        //vertx.deployVerticle(new DemoVerticle(context), new DeploymentOptions());
        vertx.deployVerticle(new SystemVerticle(context), new DeploymentOptions());
        vertx.deployVerticle(new UserVerticle(context), new DeploymentOptions());
        vertx.deployVerticle(new HttpServerVerticle());
    }
}
