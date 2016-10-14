/*
import com.eight.trundle.db.controller.BaseController;
import com.eight.trundle.vertx.EventBusAddress;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import io.vertx.core.shareddata.LocalMap;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Map;

@Component
public class AuthenticationContrl extends BaseController {

    private Logger logger = LoggerFactory.getLogger(AuthenticationContrl.class);

    public void login(RoutingContext context) {
        DeliveryOptions options = new DeliveryOptions().addHeader("method", "login");
        JsonObject ob = new JsonObject();
        String userid=context.request().getParam("username");
        ob.put("userid", userid);
        ob.put("password", context.request().getParam("password"));
        ob.put("Dev", context.request().getHeader("Dev"));
        ob.put("orgType",context.request().getParam("orgType"));//机构类型，暂时是允许不传
        ob.put("pushcid","");
        if(context.request().params().contains("pushcid")){
            ob.put("pushcid",context.request().getParam("pushcid"));
        }
        //ob.put("session", context.session().id());
        context.vertx().eventBus().<JsonObject>send(EventBusAddress.EBSystem, ob, options, result -> {
            if (result.failed()) {//是否失败
                context.fail(result.cause());
                return;
            }
            JsonObject user = result.result().body();
            if (user == null) {//用户是否存在
                context.fail(403);
                return;
            }
            JsonObject juser = user.getJsonObject("ob");
            if (juser != null) {
                if (context.session() == null) {
                    logger.debug("sesion ===null");
                    context.fail(403);
                    return;
                }
                juser.put("session", context.session().id());
                Session session = context.session();
                if (!"pc".equals(context.request().getHeader("Dev"))) {
                    LocalMap<String, Session> localMap = context.vertx().sharedData().getLocalMap("vertx-web.sessions-app");
                    for (Session se : localMap.values()) {
                        logger.debug("-------------------:" + se.get("userid") + "=====================" + userid);
                        if (se.get("userid")!=null&&userid.toLowerCase().equals(se.get("userid").toString().toLowerCase())) {
                            //app 两台设备登录
                            //todo
                            //需要推送
                            se.destroy();
                        }
                    }
                }

                logger.debug("userid======" + juser.getString("userName"));

                session.put("userid", juser.getString("userName"));
                session.put("userIdentity", juser.getValue("uid"));
                session.put(Constant.SESSION_USER_ALIAS, juser);

            }
            logger.debug(user.encode());
            context.response().end(user.encode());
        });
    }

    public void doAuth(RoutingContext ctx) {
        DeliveryOptions options = new DeliveryOptions().addHeader("method", "authToThree");
        JsonObjectSub param = ParamUtil.getRequestParams(ctx.request().params());
        if (param.containsKey("account")&&param.containsKey("dingcode")){
            //第一次
            if (param.containsKey("account")){
                param.put("userid",param.getValue("account"));
            }
        }else{
            if(!param.containsKey("dingcode")){
                OneOb result = new OneOb();
                result.setCode(500);
                result.setMsg("授权失败！");
                ctx.response().end(Json.encode(result));
                return;
            }
        }

        if (ctx.request().getHeader("Dev")!=null){
            param.put("Dev",ctx.request().getHeader("Dev"));
        }
        Session session = ctx.session();
        final long starttime=System.currentTimeMillis();
        ctx.vertx().eventBus().<JsonObject>send(EventBusAddress.EBAuth, param, options, result -> {
            if (result.failed()) {
                ctx.fail(result.cause());
                return;
            }
            JsonObject user = result.result().body();
            if (user == null) {
                ctx.fail(403);
                return;
            }
            long endtime=System.currentTimeMillis();
            //#{url},#{orgtype},#{desc},#{loginfo},#{reqtime},#{resptime},#{fadetime}
            //logger.debug("endtime:{},starttime:{}",endtime,starttime,(endtime-starttime),(endtime-starttime)/1000);
            sendLogNSyncProcess(ctx,new JsonObject(){{
                put("url",ctx.request().absoluteURI());
                put("orgtype",param.getValue("orgtype",0));
                put("userid",user.getJsonObject(Constant.SESSION_USER_ALIAS).getValue("uid"));
                put("desc","钉钉授权登录接口");
                put("platform",param.getValue("Dev"));
                put("param",Json.encode(param));
                put("loginfo","钉钉授权登录接口");
                put("reqtime", DateUtils.formatString(starttime));
                put("resptime", DateUtils.formatString(endtime));
                put("fadetime", Double.valueOf(endtime-starttime)/1000);
            }});
            if (user.getJsonObject(Constant.SESSION_USER_ALIAS)!=null) {
                session.put("account", user.getJsonObject(Constant.SESSION_USER_ALIAS).getValue("userid"));
                session.put("userIdentity",user.getJsonObject(Constant.SESSION_USER_ALIAS).getValue("uid"));
                session.put(Constant.SESSION_USER_ALIAS, user.getJsonObject(Constant.SESSION_USER_ALIAS));
               // JsonObject res=new JsonObject();
                //user.put("session", session.id());
               // user.put("dingcode",param.getValue("dingcode"));
                user.getJsonObject(Constant.SESSION_USER_ALIAS).put("session",session.id());
                user.getJsonObject(Constant.SESSION_USER_ALIAS).put("dingcode",param.getValue("dingcode"));
                user.put("ob",user.getJsonObject(Constant.SESSION_USER_ALIAS));
                user.remove(Constant.SESSION_USER_ALIAS);
                ctx.response().end(user.encode());
            }else{
                ctx.response().end(user.encode());
            }

        });
    }

   */
/* public void sendDingVCode(RoutingContext ctx) {
        DeliveryOptions options = new DeliveryOptions().addHeader("method", "sendDingVCode");
        JsonObjectSub param = ParamUtil.getRequestParams(ctx.request().params());
        ctx.vertx().eventBus().<JsonObject>send(EventBusAddress.EBAuth, param, options, result -> {
            if (result.failed()) {
                ctx.fail(result.cause());
                return;
            }
            JsonObject res = result.result().body();
            if (res == null) {
                ctx.fail(403);
                return;
            }
            ctx.response().end(res.encode());
        });
    }*//*


    public void doLogin(RoutingContext ctx) {
        DeliveryOptions options = new DeliveryOptions().addHeader("method", "login");
        JsonObjectSub param = ParamUtil.getRequestParams(ctx.request().params());
        if (param.containsKey("account")){
            param.put("userid",param.getValue("account"));
        }
        if (ctx.request().getHeader("Dev")!=null){
            param.put("Dev",ctx.request().getHeader("Dev"));
        }
        param.put("pushcid","");

        if(ctx.request().params().contains("pushcid")){
            param.put("pushcid",ctx.request().getParam("pushcid"));
        }
        if("pc".equals(param.getValue("Dev")+"")){
            options = new DeliveryOptions().addHeader("method", "loginPC");
        }else{
            options = new DeliveryOptions().addHeader("method", "loginAPP");
        }
        String account=param.getValue("account").toString();
        Session session = ctx.session();
        if(session!=null&&(session.get(Constant.SESSION_USER_ALIAS)!=null||"".equals(session.get(Constant.SESSION_USER_ALIAS)))){
            logger.debug("已经登录！");
            OneOb result=new OneOb();
            result.setCode(204);
            result.setMsg("已经登录过！");
            ctx.response().end(Json.encode(result));
            return;
        }
        final long starttime=System.currentTimeMillis();
        //user.getJsonObject(Constant.SESSION_USER_ALIAS).put("session",session.id());
        ctx.vertx().eventBus().<JsonObject>send(EventBusAddress.EBAuth, param, options, result -> {
            if (result.failed()) {//是否失败
                long endtime=System.currentTimeMillis();
                sendLogNSyncProcess(ctx,new JsonObject(){{
                    put("url",ctx.request().absoluteURI());
                    put("orgtype",param.getValue("orgtype",0));
                    put("userid",0);
                    put("desc","登录接口失败");
                    put("platform",param.getValue("Dev"));
                    put("param",Json.encode(param));
                    put("loginfo","登录接口失败");
                    put("reqtime", DateUtils.formatString(starttime));
                    put("resptime", DateUtils.formatString(endtime));
                    put("fadetime", Double.valueOf(endtime-starttime)/1000);
                }});
                ctx.fail(result.cause());
                return;
            }
            JsonObject user = result.result().body();
            if (user == null) {
                ctx.fail(403);
                return;
            }else{
                if(user.containsKey(Constant.SESSION_USER_ALIAS)){
                    if (ctx.session()== null) {
                        logger.debug("sesion is null");
                        ctx.fail(403);
                        return;
                    }else{
                        if (!"pc".equals(ctx.request().getHeader("Dev"))) {
                            LocalMap<String, Session> localMap = ctx.vertx().sharedData().getLocalMap("vertx-web.sessions-app");
                            for (Session se : localMap.values()) {
                                logger.debug("-------------------:" + se.get("account") + "=====================" + account);
                                if (se.get("account")!=null&&account.toLowerCase().equals(se.get("account").toString().toLowerCase())) {
                                    se.destroy();
                                }
                            }
                        }
                        session.put("account", account);
                        session.put("userIdentity",user.getJsonObject(Constant.SESSION_USER_ALIAS).getValue("uid"));
                        session.put(Constant.SESSION_USER_ALIAS, user.getJsonObject(Constant.SESSION_USER_ALIAS));
                    }
                }else{
                    ctx.fail(403);
                    return;
                }
            }
            long endtime=System.currentTimeMillis();
            //#{url},#{orgtype},#{desc},#{loginfo},#{reqtime},#{resptime},#{fadetime}
            //logger.debug("endtime:{},starttime:{}",endtime,starttime,(endtime-starttime),(endtime-starttime)/1000);
            sendLogNSyncProcess(ctx,new JsonObject(){{
                put("url",ctx.request().absoluteURI());
                put("orgtype",param.getValue("orgtype",0));
                put("userid",user.getJsonObject(Constant.SESSION_USER_ALIAS).getValue("uid"));
                put("desc","登录接口");
                put("platform",param.getValue("Dev"));
                put("param",Json.encode(param));
                put("loginfo","登录接口");
                put("reqtime", DateUtils.formatString(starttime));
                put("resptime", DateUtils.formatString(endtime));
                put("fadetime", Double.valueOf(endtime-starttime)/1000);
            }});
            user.getJsonObject(Constant.SESSION_USER_ALIAS).put("session",session.id());
            user.put("ob",user.getJsonObject(Constant.SESSION_USER_ALIAS));
            user.remove(Constant.SESSION_USER_ALIAS);
            logger.debug(user.encode());
            ctx.response().end(user.encode());
        });
    }

    public void logout(RoutingContext context) {
        DeliveryOptions options = new DeliveryOptions().addHeader("method", "loginOut");

        JsonObject ob = new JsonObject();
        JsonObject user = context.session().get(Constant.SESSION_USER_ALIAS);//获得登录对象
        if(user==null){
            context.session().destroy();
            context.response().setStatusCode(200);
            context.response().end(Json.encode(new OneOb()));
            return;
        }
        ob.put("uid", user.getValue("uid"));
        ob.put("Dev", context.request().getHeader("Dev"));
        Object userid=user.getValue("userid",0);

        try{
            context.session().destroy();
            //modify push cid
            final long starttime=System.currentTimeMillis();
            context.vertx().eventBus().<JsonObject>send(EventBusAddress.EBAuth, ob, options, result -> {
                if (result.failed()) {//是否失败
                    context.fail(result.cause());
                    return;
                }
                if (!"pc".equals(context.request().getHeader("Dev"))) {
                    LocalMap<String, Session> localMap = context.vertx().sharedData().getLocalMap("vertx-web.sessions-app");
                    for (Session se : localMap.values()) {
                        logger.debug("-------------------:" + se.get("userid") + "=====================" + user.getValue("uid").toString());
                        if (se.get("userIdentity")!=null&&user.getValue("uid").toString().toLowerCase().equals(se.get("userIdentity").toString().toLowerCase())) {
                            se.destroy();
                        }
                    }
                }
                long endtime=System.currentTimeMillis();
                //#{url},#{orgtype},#{desc},#{loginfo},#{reqtime},#{resptime},#{fadetime}
                //logger.debug("endtime:{},starttime:{}",endtime,starttime,(endtime-starttime),(endtime-starttime)/1000);
                sendLogNSyncProcess(context,new JsonObject(){{
                    put("url",context.request().absoluteURI());
                    put("orgtype",ob.getValue("orgtype",0));
                    put("userid",userid);
                    put("desc","登录接口");
                    put("platform",ob.getValue("Dev"));
                    put("param",Json.encode(ob));
                    put("loginfo","登录接口");
                    put("reqtime", DateUtils.formatString(starttime));
                    put("resptime", DateUtils.formatString(endtime));
                    put("fadetime", Double.valueOf(endtime-starttime)/1000);
                }});
                context.response().setStatusCode(200);
                context.response().end(Json.encode(new OneOb()));
            });
        }catch (Exception e){
            context.fail(403);
            return;
        }


    }

    public void auth(RoutingContext ctx) {
        logger.debug("session ------------------------------");
        JsonObject user =null;
        final Session session = ctx.session();
        String path = ctx.request().path();
        if(path.startsWith("/api/register/")||"/api/login".equals(path)){
            ctx.next();
            return;
        }
        if (session == null||session.get("user")==null) {
            logger.debug("登陆失效！");
            String dev=ctx.request().headers().get("Dev");
            if(dev.equalsIgnoreCase("pc")){
                ctx.fail(403);
            }else {
                ctx.response().setStatusCode(200);
                OneOb ob = new OneOb();
                ob.setCode(240);
                ob.setMsg("session过期或在其他手机上登录过！");
                ctx.response().end(Json.encode(ob));
            }
            return;
        }else{
            user = session.get("user");
        }
        JsonArray auth = user.getJsonArray("auth");
        if(path.contains("system/getMenuPrivilege")){//获取权限菜单
            ctx.next();
            return;
        }
        logger.debug("---path------" + path);
       */
/* if (auth!=null&&auth.contains(path)) {
            logger.debug("---auth------ok");
            ctx.next();
            return;
        } else {*//*

            if(user!=null){
                ctx.next();
                return;
            }
            logger.debug("Fail path {}",path);
            ctx.fail(403);
            return;
       // }
    }

    public void checkLogin(RoutingContext ctx) {
        String dev=ctx.request().headers().get("Dev");
        if("PC".equalsIgnoreCase(dev)){
            final Session session = ctx.session();
            if (session == null) {
                logger.debug("session is null");
                ctx.fail(403);
                return;
            }
            JsonObject user = session.get("user");
            if (user == null) {
                logger.debug("登陆失效！");
                ctx.fail(403);
                return;
            }
            ctx.response().setStatusCode(200);
            ctx.response().end(Json.encode(new OneOb()));
        }
    }

    //提交验证码
    public void submitVerifyCode(RoutingContext context) {
        JsonObject user =null;
       */
/* final Session session = context.session();
        if (session == null||session.get("user")==null) {
            logger.debug("登陆失效！");
            String dev=context.request().headers().get("Dev");
            if(dev.equalsIgnoreCase("pc")){
                context.fail(403);
            }else {
                context.response().setStatusCode(200);
                OneOb ob = new OneOb();
                ob.setCode(240);
                ob.setMsg("session过期或在其他手机上登录过！");
                context.response().end(Json.encode(ob));
            }
            return;
        }else{
            user = session.get("user");
        }*//*

        DeliveryOptions options = new DeliveryOptions().addHeader("method", "submitVerifyCode");
        JsonObject ob = new JsonObject();
        String code=context.request().getParam("code");
        String number=context.request().getParam("number");
        ob.put("code", code);
        ob.put("number", number);
        context.vertx().eventBus().<JsonObject>send(EventBusAddress.EBAuth, ob, options, result -> {
            if (result.failed()) {//是否失败
                context.fail(result.cause());
                return;
            }
            JsonObject callback = result.result().body();
            context.response().end(callback.encodePrettily());
        });


    }

    //获取验证码
    public void getVerifyCode(RoutingContext context) {
        DeliveryOptions options = new DeliveryOptions().addHeader("method", "getVerifyCode");
        JsonObject ob = new JsonObject();
        String number=context.request().getParam("number");
        ob.put("number", number);
        */
/*ob.put("code",str);*//*

        context.vertx().eventBus().<JsonObject>send(EventBusAddress.EBAuth, ob, options, result -> {
            if (result.failed()) {//是否失败
                context.fail(result.cause());
                return;
            }
            JsonObject user = result.result().body();
            context.response().end(user.encodePrettily());
        });
    }

    //提交密码
    public void submitPassword(RoutingContext context) {
        DeliveryOptions options = new DeliveryOptions().addHeader("method", "submitPassword");
        JsonObject ob = new JsonObject();
        String number=context.request().getParam("number");
        String password=context.request().getParam("password");
        String username=context.request().getParam("username");
        ob.put("number", number);
        ob.put("password",password);
        ob.put("username",username);
        context.vertx().eventBus().<JsonObject>send(EventBusAddress.EBAuth, ob, options, result -> {
            if (result.failed()) {//是否失败
                context.fail(result.cause());
                return;
            }
            JsonObject user = result.result().body();
            context.response().end(user.encodePrettily());
        });
    }



    public void loginJWT(RoutingContext context) {
        JsonObject result = new JsonObject();
        result.put("userid", "111");
        result.put("password", "zhou");
        result.put("Dev", "pc");
        result.put("orgType",1);
        result.put("pushcid","");
        String access_token=context.request().getParam("access_token");
        try {
            Base64 decoder = new Base64(true);
            byte[] secret = decoder.decodeBase64("rayeye.cn");//密钥
            Map<String,Object> decodedPayload =new JWTVerifier(secret, "audience").verify("my-token");
            // Get custom fields from decoded Payload
            //new JWTVerifier(secret, "audience").verifySignature(access_token.split("\\."), "HmacSHA256");
            System.out.println(decodedPayload.get("name"));
        } catch (SignatureException signatureException) {
            logger.debug("无效的签证!{}",signatureException.getMessage());
        } catch (IllegalStateException illegalStateException) {
           logger.debug("无效的口令!{}",illegalStateException.getMessage());
        } catch (JWTVerifyException e) {
            e.printStackTrace();
            logger.debug("没有算法，或者不知此此算法!{}",e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            logger.debug("密钥无效!{}",e.getMessage());
        }catch (Exception e){
            logger.debug("异常!{}",e.getMessage());
        }
    }

}


*/
