package com.demo.verticle;


import com.demo.untils.Constants;
import com.demo.untils.annotations.RouteHandler;
import com.demo.untils.annotations.RouteMapping;
import com.demo.untils.annotations.RouteMethod;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.sstore.SessionStore;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import static io.vertx.core.http.HttpHeaders.CONTENT_TYPE;

public class HttpServerVerticle extends AbstractVerticle {
    private static final Logger log = LoggerFactory.getLogger(HttpServerVerticle.class);
    private static final Reflections reflections = new Reflections("com.demo.controller");
    SessionStore store;

    protected Router router;
    Logger logger = LoggerFactory.getLogger(HttpServerVerticle.class);
    HttpServer server;

    @Override
    public void start(Future<Void> future) throws Exception {

        logger.info("==============web start==============");
        super.start();
        store = LocalSessionStore.create(vertx);
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
        router.route().handler(BodyHandler.create());
        router.route().handler(CookieHandler.create());
        registerHandlers();
        return router;
    }

    private void registerHandlers() {
        log.debug("Register available request handlers...");

        Set<Class<?>> handlers = reflections.getTypesAnnotatedWith(RouteHandler.class);
        for (Class<?> handler : handlers) {
            try {
                registerNewHandler(handler);
            } catch (Exception e) {
                log.error("Error register {}", handler);
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
                log.debug("Register New Handler -> {}:{}", routeMethod, url);
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
