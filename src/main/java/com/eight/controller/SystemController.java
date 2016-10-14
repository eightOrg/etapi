package com.eight.controller;

import com.eight.trundle.Constants;
import com.eight.trundle.ob.BaseOb;
import com.eight.trundle.params.ParamUtil;
import com.eight.trundle.vertx.EventBusAddress;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;


/**
 * 用于服务不需要session的请求
 * 不需要继承BaseController
 * Created by miaoch on 2016/8/9.
 */
@Component
public class SystemController {
    private Logger logger = LoggerFactory.getLogger(SystemController.class);
    private String address = EventBusAddress.EBSystem;

    /**
     * 登录接口
     */
    public void login(RoutingContext routingContext){
        DeliveryOptions options = new DeliveryOptions().addHeader("method", "login");
        JsonObject ob= ParamUtil.getParamJson(routingContext.request().params());
        logger.debug("params===========" + ob !=null?ob.toString():"null");
        routingContext.vertx().eventBus().<JsonObject>send(address, ob, options, result -> {
            JsonObject object = result.result().body();
            JsonObject user = object.getJsonObject(Constants.RESULT_OB_OB);
            if (user != null) {//用户存在
                if (routingContext.session() == null) {
                    logger.debug("sesion === null");
                    routingContext.fail(403);
                    return;
                }
                String userId = user.getValue("id").toString();
                user.put("sessionId", routingContext.session().id());
                Session session = routingContext.session();
                LocalMap<String, Session> localMap = routingContext.vertx().sharedData().getLocalMap("vertx-web.sessions-pc");
                for (Session s : localMap.values()) {
                    if (s.get("userId") != null && userId.equalsIgnoreCase(s.get("userId").toString())) {
                        //app 两台设备登录
                        //todo
                        //需要推送
                        s.destroy();
                    }
                }
                logger.debug("userId======" + userId);
                session.put("userId", userId);
                session.put("user", user);
            }
            logger.debug("user===========" + object.encode());
            routingContext.response().end(object.encode());
        });
    }

    /**
     * 验证接口
     */
    public void auth(RoutingContext routingContext){
        logger.debug("session ------------------------------");
        final Session session = routingContext.session();
        if (session == null || session.get("user") == null) {
            logger.debug("登陆失效！");
            routingContext.response().setStatusCode(Constants.CODE_OK);
            BaseOb ob = new BaseOb();
            ob.setCode(Constants.CODE_LOSE);
            ob.setMsg("session过期或在其他手机上登录过！");
            routingContext.response().end(Json.encode(ob));
        }else{
            routingContext.next();
        }
    }

    /**
     * 获取验证码 getType 1: 忘记密码 2: 新用户注册
     */
    public void getCode(RoutingContext routingContext){
        DeliveryOptions options = new DeliveryOptions().addHeader("method", "getCode");
        JsonObject ob= ParamUtil.getParamJson(routingContext.request().params());
        logger.debug("params===========" + ob !=null?ob.toString():"null");
        routingContext.vertx().eventBus().<JsonObject>send(address, ob, options, result -> {
            JsonObject object = result.result().body();
            logger.debug("result===========" + object.encode());
            routingContext.response().end(object.encode());
        });
    }
    /**
     * 获取验证码 type:忘记密码 新用户注册
     */
    /*public void getCode(RoutingContext routingContext){
        DeliveryOptions options = new DeliveryOptions().addHeader("method", "getCode");
        JsonObject ob= ParamUtil.getParamJson(routingContext.request().params());
        logger.debug("params===========" + ob !=null?ob.toString():"null");
        routingContext.vertx().eventBus().<JsonObject>send(address, ob, options, result -> {
            JsonObject object = result.result().body();
            logger.debug("result===========" + object.encode());
            routingContext.response().end(object.encode());
        });
    }*/
    /**
     * 注册成功
     */
    public void register(RoutingContext routingContext){
        DeliveryOptions options = new DeliveryOptions().addHeader("method", "register");
        JsonObject ob= ParamUtil.getParamJson(routingContext.request().params());
        logger.debug("params===========" + ob !=null?ob.toString():"null");
        routingContext.vertx().eventBus().<JsonObject>send(address, ob, options, result -> {
            JsonObject object = result.result().body();
            logger.debug("result===========" + object.encode());
            routingContext.response().end(object.encode());
        });
    }

    /**
     * 注册提交验证码 并生成该用户
     */
    /*public void submit(RoutingContext routingContext){
        DeliveryOptions options = new DeliveryOptions().addHeader("method", "register");
        JsonObject ob= ParamUtil.getParamJson(routingContext.request().params());
        logger.debug("params===========" + ob !=null?ob.toString():"null");
        routingContext.vertx().eventBus().<JsonObject>send(address, ob, options, result -> {
            JsonObject object = result.result().body();
            logger.debug("result===========" + object.encode());
            routingContext.response().end(object.encode());
        });
    }*/
}
