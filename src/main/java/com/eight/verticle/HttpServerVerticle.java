package com.eight.verticle;


import com.eight.controller.SystemController;
import com.eight.trundle.Constants;
import com.eight.trundle.annotations.RouteHandler;
import com.eight.trundle.annotations.RouteMapping;
import com.eight.trundle.annotations.RouteMethod;
import com.eight.trundle.session.SessionHandlerImplPc;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.*;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.sstore.SessionStore;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import static io.vertx.core.http.HttpHeaders.*;

public class HttpServerVerticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(HttpServerVerticle.class);
    private static final Reflections reflections = new Reflections(Constants.ROUTE_REFLECTIONS);
    private SystemController authApi;

    protected Router router;
    HttpServer server;
    public HttpServerVerticle(final ApplicationContext context) {
        authApi = (SystemController) context.getBean(SystemController.class);
    }


    @Override
    public void start(Future<Void> future) throws Exception {

        logger.info("==============web start==============");
        super.start();
        server = vertx.createHttpServer(createOptions());
        server.requestHandler(createRouter()::accept);
        server.listen(result -> {
            if (result.succeeded()) {
                future.complete();
            } else {
                future.fail(result.cause());
            }
        });
    }

    @Override
    public void stop(Future<Void> future) {
        if (server == null) {
            future.complete();
            return;
        }
        server.close(result -> {
            if (result.failed()) {
                future.fail(result.cause());
            } else {
                future.complete();
            }
        });
    }

    private HttpServerOptions createOptions() {
        HttpServerOptions options = new HttpServerOptions();
        options.setPort(Constants.PORT);
        return options;
    }

    private Router createRouter() {
        logger.info("-------createRouter--------");

        router = Router.router(vertx);
        router.route().handler(ctx -> {
            logger.debug("--------path:" + ctx.request().path()
                            + "--------uri:" + ctx.request().absoluteURI()
                            + "-------method:" + ctx.request().method()
            );
            ctx.request().headers().add(CONTENT_TYPE, "charset=utf-8");
            ctx.response().headers().add(CONTENT_TYPE, "application/json; charset=utf-8");
            ctx.response().headers().add(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            ctx.response().headers().add(ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, OPTIONS, PUT, DELETE, HEAD");
            ctx.response().headers().add(ACCESS_CONTROL_ALLOW_HEADERS, "X-PINGOTHER, Origin, Content-Type, Accept, X-Requested-With, session, Dev, Version");
            ctx.response().headers().add(ACCESS_CONTROL_MAX_AGE, "1728000");
            ctx.next();
        });

        /*允许跨域请求*/
        Set<HttpMethod> method = new HashSet<HttpMethod>();
        method.add(HttpMethod.GET);
        method.add(HttpMethod.POST);
        method.add(HttpMethod.OPTIONS);
        method.add(HttpMethod.PUT);
        method.add(HttpMethod.DELETE);
        method.add(HttpMethod.HEAD);
        router.route().handler(CorsHandler.create("*").allowedMethods(method));
        router.route().handler(CookieHandler.create());
        SessionStore sessionStorePc = LocalSessionStore.create(vertx, "vertx-web.sessions-pc");
        SessionStore sessionStoreApp = LocalSessionStore.create(vertx, "vertx-web.sessions-app", 15552000000l);
        SessionHandler sessionHandlerPc = SessionHandlerImplPc.createPc(sessionStorePc, "vertx-web.sessions-pc");
        SessionHandler sessionHandlerApp = SessionHandlerImplPc.createPc(sessionStorePc, "vertx-web.sessions-app");
        sessionHandlerPc.setSessionTimeout(30*60*1000);
        sessionHandlerApp.setNagHttps(false);
        router.route().handler(sessionHandlerPc);
        router.route().handler(sessionHandlerApp);
        router.route().handler(BodyHandler.create());
        //此处填写不需要验证和手动注册的接口
        router.post("/login").handler(authApi::login);
        router.get("/getCode").handler(authApi::getCode);
        router.get("/checkCode").handler(authApi::checkCode);
        router.post("/updateOrInsertUser").handler(authApi::updateOrInsertUser);
        //拦截/user下的请求
        //router.get("/user/*").blockingHandler(authApi::auth);
        //router.post("/user/*").blockingHandler(authApi::auth);
        try {
            //httpRouter.registerRoute(router);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Manually Register Handler Fail，Error details："+e.getMessage());
        }
        registerHandlers();
        return router;
    }

    private void registerHandlers() {
        logger.debug("Register available request handlers...");

        Set<Class<?>> handlers = reflections.getTypesAnnotatedWith(RouteHandler.class);
        for (Class<?> handler : handlers) {
            try {
                registerNewHandler(handler);
            } catch (Exception e) {
                logger.error("Error register {}", handler);
            }
        }
    }

    private void registerNewHandler(Class<?> handler) throws Exception {
        String root = "";
        if (handler.isAnnotationPresent(RouteHandler.class)) {
            RouteHandler routeHandler = handler.getAnnotation(RouteHandler.class);
            root = routeHandler.value();
        }
        Object instance = handler.newInstance();
        Method[] methods = handler.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(RouteMapping.class)) {
                RouteMapping mapping = method.getAnnotation(RouteMapping.class);
                RouteMethod routeMethod = mapping.method();
                String url = root + "/" + method.getName() + mapping.value();
                Handler<RoutingContext> methodHandler = (Handler<RoutingContext>) method.invoke(instance);
                logger.debug("Register New Handler -> {}:{}", routeMethod, url);
                switch (routeMethod) {
                    case POST:
                        router.post(url).handler(methodHandler);
                        break;
                    case PUT:
                        router.put(url).handler(methodHandler);
                        break;
                    case DELETE:
                        router.delete(url).handler(methodHandler);
                        break;
                    case GET:
                    default:
                        router.get(url).handler(methodHandler);
                        break;
                }
            }
        }
    }
}
