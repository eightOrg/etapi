package com.eight.trundle.db.controller;


import com.eight.trundle.annotations.RouteMapping;
import com.eight.trundle.annotations.RouteMethod;
import com.eight.trundle.handle.HandleTemplet;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;

/**
 * Created by miaoch on 2016/8/24.
 */
public abstract class BaseController {
    public abstract Logger getLogger();
    public abstract String getAddress();

    /**
     * 添加对象。
     */
    @RouteMapping(method = RouteMethod.POST)
    public Handler<RoutingContext> insert(){
        String method = "insert";
        return HandleTemplet.getHandler(method, getAddress(), getLogger());
    }

    /**
     * 批量添加对象。
     */
    @RouteMapping(method = RouteMethod.POST)
    public Handler<RoutingContext> insertInBatch(){
        String method = "insertInBatch";
        return HandleTemplet.getHandler(method, getAddress(), getLogger());
    }

    /**
     * 删除对象。
     */
    @RouteMapping(method = RouteMethod.POST)
    public Handler<RoutingContext> delete(){
        String method = "delete";
        return HandleTemplet.getHandler(method, getAddress(), getLogger());
    }

    /**
     * 根据Id删除对象。
     */
    @RouteMapping(method = RouteMethod.POST)
    public Handler<RoutingContext> deleteById(){
        String method = "deleteById";
        return HandleTemplet.getHandler(method, getAddress(), getLogger());
    }

    /**
     * 根据id，批量删除记录。
     */
    @RouteMapping(method = RouteMethod.POST)
    public Handler<RoutingContext> deleteByIdInBatch(){
        String method = "deleteByIdInBatch";
        return HandleTemplet.getHandler(method, getAddress(), getLogger());
    }

    /**
     * 根据Id删除对象。（只改状态，不实际删除）
     */
    @RouteMapping(method = RouteMethod.POST)
    public Handler<RoutingContext> deleteStateById(){
        String method = "deleteStateById";
        return HandleTemplet.getHandler(method, getAddress(), getLogger());
    }

    /**
     * 根据id，批量删除记录。（只改状态，不实际删除）
     */
    @RouteMapping(method = RouteMethod.POST)
    public Handler<RoutingContext> deleteStateByIdInBatch(){
        String method = "deleteStateByIdInBatch";
        return HandleTemplet.getHandler(method, getAddress(), getLogger());
    }

    /**
     * 更新对象。
     */
    @RouteMapping(method = RouteMethod.POST)
    public Handler<RoutingContext> update(){
        String method = "update";
        return HandleTemplet.getHandler(method, getAddress(), getLogger());
    }

    /**
     * 根据条件查询记录数。
     */
    @RouteMapping(method = RouteMethod.GET)
    public Handler<RoutingContext> selectCount(){
        String method = "selectCount";
        return HandleTemplet.getHandler(method, getAddress(), getLogger());
    }

    /**
     * 根据条件查询记录数。（除了本身ID之外的）
     */
    @RouteMapping(method = RouteMethod.GET)
    public Handler<RoutingContext> selectCountExtId(){
        String method = "selectCountExtId";
        return HandleTemplet.getHandler(method, getAddress(), getLogger());
    }

    /**
     * 通过Id查询一个对象。
     */
    @RouteMapping(method = RouteMethod.GET)
    public Handler<RoutingContext> selectById(){
        String method = "selectById";
        return HandleTemplet.getHandler(method, getAddress(), getLogger());
    }

    /**
     * 根据条件查询所有对象列表。
     */
    @RouteMapping(method = RouteMethod.GET)
    public Handler<RoutingContext> selectList(){
        String method = "selectList";
        return HandleTemplet.getHandler(method, getAddress(), getLogger());
    }

    /**
     * 查询前多少条记录列表。
     */
    @RouteMapping(method = RouteMethod.GET)
    public Handler<RoutingContext> selectTopList(){
        String method = "selectTopList";
        return HandleTemplet.getHandler(method, getAddress(), getLogger());
    }

    /**
     * 根据条件查询一个对象。
     */
    @RouteMapping(method = RouteMethod.GET)
    public Handler<RoutingContext> selectOne(){
        String method = "selectOne";
        return HandleTemplet.getHandler(method, getAddress(), getLogger());
    }

    /**
     * 分页查询列表，带查询条件。
     */
    @RouteMapping(method = RouteMethod.GET)
    public Handler<RoutingContext> selectPageList(){
        String method = "selectPageList";
        return HandleTemplet.getHandler(method, getAddress(), getLogger());
    }

}
