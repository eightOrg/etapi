/*
import com.rayeye.common.annotations.RequestMapping;
import com.rayeye.common.annotations.RouteHandler;
import com.rayeye.common.annotations.RouteMapping;
import com.rayeye.common.annotations.RouteMethod;
import com.rayeye.common.utils.PropertiesUtil;
import com.rayeye.vertx.api.iot.contrl.global.NoticeContrl;
import com.rayeye.vertx.api.iot.contrl.global.SystemController;
import com.rayeye.vertx.api.iot.contrl.outweb.FireInfoContrl;
import com.rayeye.vertx.api.iot.contrl.global.AuthenticationContrl;

import com.rayeye.vertx.api.iot.utils.SessionHandlerImplApp;
import com.rayeye.vertx.api.iot.utils.SessionHandlerImplPc;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
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
    private static final Logger log = LoggerFactory.getLogger(HttpServerVerticle.class);
    private static final Reflections reflections = new Reflections("com.rayeye.vertx.api.iot.contrl");

    protected Router router;
    HttpServer server;
    private HttpRouter httpRouter;

    public HttpServerVerticle(final ApplicationContext context) {
        httpRouter=new HttpRouter(context);
    }

    @Override
    public void start(Future<Void> future) throws Exception {
        log.info("==============web start==============");
        super.start();
        server = vertx.createHttpServer();
        server = vertx.createHttpServer(createOptions());
        server.requestHandler(createRouter()::accept);
        server.listen(result2 -> {
            if (result2.succeeded()) {
                future.complete();
            } else {
                future.fail(result2.cause());
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
        options.setPort(PropertiesUtil.getApiport());
        return options;
    }

    private Router createRouter() {
        log.info("-------createRouter--------");

        router = Router.router(vertx);
        router.route().handler(ctx -> {
            log.debug("Receives the request info;===>path:{}, uri:{}, method:{}, version:{}, Dev:{}",ctx.request().path(),ctx.request().absoluteURI(),ctx.request().method(), ctx.request().headers().get("version"),ctx.request().headers().get("Dev"));
            String origin = ctx.request().headers().get("Origin");
            String token = ctx.request().headers().get("session");

            log.debug("sesion=====" + token);
            log.debug("origin=====" + origin);

            ctx.request().headers().add(CONTENT_TYPE, "charset=utf-8");
            ctx.response().headers().add(CONTENT_TYPE, "application/json; charset=utf-8");
            //  context.response().headers().add(ACCESS_CONTROL_ALLOW_ORIGIN, origin);
            ctx.response().headers().add(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            ctx.response().headers().add(ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, OPTIONS, PUT, DELETE, HEAD");
            ctx.response().headers().add(ACCESS_CONTROL_ALLOW_HEADERS, "X-PINGOTHER, Origin,Content-Type, Accept, X-Requested-With, session,Dev,Version");
            ctx.response().headers().add(ACCESS_CONTROL_MAX_AGE, "1728000");
            ctx.next();
        });


        Set<HttpMethod> method = new HashSet<HttpMethod>();
        method.add(HttpMethod.GET);
        method.add(HttpMethod.POST);
        method.add(HttpMethod.OPTIONS);
        method.add(HttpMethod.PUT);
        method.add(HttpMethod.DELETE);
        method.add(HttpMethod.HEAD);

        router.route().handler(CorsHandler.create("*").allowedMethods(method));
       // router.route().handler(BodyHandler.create());
        router.route().handler(CookieHandler.create());

        SessionStore sessionStoreApp = LocalSessionStore.create(vertx, "vertx-web.sessions-app",15552000000l);
        SessionStore sessionStorePc = LocalSessionStore.create(vertx, "vertx-web.sessions-pc");
        //SessionStore sessionStoreVer = LocalSessionStore.create(vertx, "sessions-verifyCode");

        SessionHandler sessionHandlerApp = SessionHandlerImplApp.createApp(sessionStoreApp, "vertx-web.sessions-app");
        SessionHandler sessionHandlerPc = SessionHandlerImplPc.createPc(sessionStorePc, "vertx-web.sessions-pc");
        //SessionHandler sessionHandlerVer = SessionHandlerImplPc.createPc(sessionStoreVer, "sessions-verifyCode");

        sessionHandlerPc.setSessionTimeout(30*60*1000);
        //sessionHandlerVer.setSessionTimeout(5*60*1000);
        sessionHandlerApp.setNagHttps(false);
        router.route().handler(sessionHandlerApp);
        router.route().handler(sessionHandlerPc);
        //router.route().handler(sessionHandlerVer);



        router.route().handler(BodyHandler.create());

        try {
            httpRouter.registerRoute(router);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Manually Register Handler Fail，Error details："+e.getMessage());
        }
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
                String routeUrl="";
                if(mapping.value().startsWith("/:")){
                    routeUrl=(method.getName()+mapping.value());
                }else{
                    routeUrl=(mapping.value().endsWith(method.getName())?mapping.value():(mapping.isCover()?mapping.value():mapping.value()+method.getName()));
                    if(routeUrl.startsWith("/")){
                        routeUrl=routeUrl.substring(1);
                    }
                }
                String url = root + "/" +routeUrl;
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
                    case GET: // fall through
                    default:
                        router.get(url).handler(methodHandler);
                        break;
                }
            }
        }
    }


    private void redirectTo(RoutingContext context, String url) {
        HttpServerResponse response = context.response();
        response.setStatusCode(303);
       // response.headers().set(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.headers().add("Location", url);
        response.end();
    }


}
*/
