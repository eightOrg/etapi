package com.eight.trundle.session;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.SessionStore;

/**
 * Created by Administrator on 2015/10/3.
 */
public class SessionHandlerImplPc implements SessionHandler {

    private static final Logger log = LoggerFactory.getLogger(SessionHandlerImplPc.class);

    private final SessionStore sessionStore;
    private String sessionCookieName;
    private long sessionTimeout;
    private boolean nagHttps;


    public SessionHandlerImplPc(String sessionCookieName, long sessionTimeout, boolean nagHttps, SessionStore sessionStore) {
        this.sessionCookieName = sessionCookieName;
        this.sessionTimeout = sessionTimeout;
        this.nagHttps = nagHttps;
        this.sessionStore = sessionStore;
    }


    public static SessionHandler createPc(SessionStore sessionStore,String name) {
        return new SessionHandlerImplPc(name, DEFAULT_SESSION_TIMEOUT, DEFAULT_NAG_HTTPS, sessionStore);
    }


    @Override
    public SessionHandler setSessionTimeout(long timeout) {
        this.sessionTimeout = timeout;
        return this;
    }

    @Override
    public SessionHandler setNagHttps(boolean nag) {
        this.nagHttps = nag;
        return this;
    }

    @Override
    public SessionHandler setSessionCookieName(String sessionCookieName) {
        this.sessionCookieName = sessionCookieName;
        return this;
    }
    @Override
    public SessionHandler setCookieHttpOnlyFlag(boolean httpOnly) {
        return this;
    }
    @Override
    public SessionHandler setCookieSecureFlag(boolean secure) {
        return this;
    }
    @Override
    public void handle(RoutingContext context) {
        context.response().ended();

        if (nagHttps) {
            String uri = context.request().absoluteURI();
            if (!uri.startsWith("https:")) {
                log.warn("Using session cookies without https could make you susceptible to session hijacking: " + uri);
            }
        }

        String sessionID = context.request().headers().get("session");
        String dev = context.request().headers().get("Dev");

        if(!"pc".equals(dev)){
            context.next();
            return;
        }

        if (sessionID != null) {
            sessionStore.get(sessionID, res -> {
                if (res.succeeded()) {
                    Session session = res.result();
                    if (session != null) {
                        context.setSession(session);
                        session.setAccessed();
                        addStoreSessionHandler(context);
                    } else {
                        createNewSession(context);
                    }
                } else {
                    context.fail(res.cause());
                }
                context.next();
            });
        } else {
            createNewSession(context);
            context.next();
        }
    }

    private void addStoreSessionHandler(RoutingContext context) {
        context.addHeadersEndHandler(v -> {
            Session session = context.session();
            if (!session.isDestroyed()) {
                // Store the session
                session.setAccessed();
                sessionStore.put(session, res -> {
                    if (res.failed()) {
                        log.error("Failed to store session", res.cause());
                    }
                });
            } else {
                sessionStore.delete(session.id(), res -> {
                    if (res.failed()) {
                        log.error("Failed to delete session", res.cause());
                    }
                });
            }
        });
    }

    private void createNewSession(RoutingContext context) {
        Session session = sessionStore.createSession(sessionTimeout);
        context.setSession(session);
//        Cookie cookie = Cookie.cookie(sessionCookieName, session.id());
//        cookie.setPath("/");
//        // Don't set max age - it's a session cookie
//        context.addCookie(cookie);
        addStoreSessionHandler(context);
    }




}

