package com.eight.trundle.db.service.impl;

import com.eight.trundle.Constants;
import com.eight.trundle.db.dao.BaseDao;
import com.eight.trundle.db.pojo.Identifiable;
import com.eight.trundle.db.service.BaseService;
import com.eight.trundle.ob.BaseOb;
import com.eight.trundle.ob.ListOb;
import com.eight.trundle.ob.OneOb;
import com.eight.trundle.params.MapUtil;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by miao on 2016/8/12.
 */
@Service("BaseService")
public abstract class BaseServiceImpl<T extends Identifiable> implements BaseService {
    /**
     * 获取Dao，在子类里通过注入的方式实现
     * @return
     */
    protected abstract BaseDao<T> getBaseDao();

    /**
     * 添加对象。
     * @param params 要实例化的实体，不能为null
     * @return 受影响的结果数
     */
    @Override
    public JsonObject insert(JsonObject params) {
        Map obj = params.getMap();
        if (obj == null) {
            return new JsonObject(Json.encode(BaseOb.getFaildOb().setMsg("插入元素不能为空!")));
        }
        if (MapUtil.isNotEmpty(obj, Constants.POJO_STATE)) {
            obj.put(Constants.POJO_STATE, Constants.STATE_OK);
        }
        if (obj.containsKey(Constants.POJO_CREATETIME)) {
            obj.put(Constants.POJO_CREATETIME, System.currentTimeMillis());
        }
        if (obj.containsKey(Constants.POJO_CHANGETIME)) {
            obj.put(Constants.POJO_CHANGETIME, System.currentTimeMillis());
        }
        int count = getBaseDao().insert(obj);
        return getResultBaseOb(count > 0, count + "个元素被插入", "插入元素失败");
    }

    /**
     * params.getValue("objList") 需要批量插入的实体对象列表
     * 批量添加对象。
     * 如果为空列表则直接返回
     * @param params
     */
    @Override
    public JsonObject insertInBatch(JsonObject params) {
        if (MapUtil.isEmpty(params, Constants.PARAMS_OBJLIST)) {
            return new JsonObject(Json.encode(BaseOb.getFaildOb().setMsg("元素插入列表不能为空！")));
        }
        List<JsonObject> objList = (List<JsonObject>) params.getValue(Constants.PARAMS_OBJLIST);
        int count = 0;
        for (JsonObject obj : objList) {
            if (this.insert(obj).getBoolean(Constants.RESULT_OB_FLAG)) {
                count++;
            }
        }
        return new JsonObject(Json.encode(new BaseOb().setMsg(count + "个元素被插入")));
    }

    /**
     * 删除对象。
     * 实体类的参数作为查询条件
     * @param params 要删除的实体对象，不能为null
     * @return int 受影响结果数
     */
    @Override
    public JsonObject delete(JsonObject params) {
        int count = getBaseDao().delete(params.getMap());
        return getResultBaseOb(count> 0, count + "个元素被删除", "删除元素失败，不含该元素");
    }

    /**
     * 根据Id删除对象。
     * @param params  要删除的ID(params.getString("id"))，不能为null
     * @return int 受影响结果数
     */
    @Override
    public JsonObject deleteById(JsonObject params) {
        if (MapUtil.isEmpty(params, Constants.POJO_ID)) {
            return new JsonObject(Json.encode(BaseOb.getFaildOb().setMsg("元素不能为空！")));
        }
        int id = (Integer) params.getValue(Constants.POJO_ID);
        if (id <= 0) {
            return new JsonObject(Json.encode(BaseOb.getFaildOb().setMsg("元素id必须大于0！")));
        }
        int count = getBaseDao().deleteById(id);
        return getResultBaseOb(count> 0, count + "个元素被删除", "删除元素失败，不含该元素");
    }

    /**
     * 根据id，批量删除记录。
     * 如果传入的列表为null或为空列表则直接返回
     * @param params 批量删除ID列表
     */
    @Override
    public JsonObject deleteByIdInBatch(JsonObject params) {
        if (MapUtil.isEmpty(params, Constants.PARAMS_IDLIST)) {
            return new JsonObject(Json.encode(BaseOb.getFaildOb().setMsg("元素删除列表为空！")));
        }
        int count = 0;
        List<JsonObject> idList = (List<JsonObject>) params.getValue(Constants.PARAMS_IDLIST);
        for (JsonObject id : idList) {
            if (this.deleteById(id).getBoolean(Constants.RESULT_OB_FLAG)) {
                count++;
            }
        }
        return new JsonObject(Json.encode(new BaseOb().setMsg(count + "个元素被删除")));
    }

    /**
     * 根据Id删除对象。（只改状态，不实际删除）
     * @param params  要删除的ID(params.getValue("id"))，不能为null
     * @return int 受影响结果数
     */
    @Override
    public JsonObject deleteStateById(JsonObject params){
        if (MapUtil.isEmpty(params, Constants.POJO_ID)) {
            return new JsonObject(Json.encode(BaseOb.getFaildOb().setMsg("元素不能为空！")));
        }
        int id = (Integer) params.getValue(Constants.POJO_ID);
        if (id <= 0){
            return new JsonObject(Json.encode(BaseOb.getFaildOb().setMsg("元素id必须大于0！")));
        }
        int count = getBaseDao().deleteStateById(id);
        return getResultBaseOb(count> 0, count + "个元素被禁用", "禁用元素失败");
    }

    /**
     * 根据id，批量删除记录。（只改状态，不实际删除）
     * 如果传入的列表为null或为空列表则直接返回
     * @param params params.getValue("idList")批量删除ID列表
     */
    @Override
    public JsonObject deleteStateByIdInBatch(JsonObject params){
        if (MapUtil.isEmpty(params, Constants.PARAMS_IDLIST)) {
            return new JsonObject(Json.encode(BaseOb.getFaildOb().setMsg("元素禁用列表为空！")));
        }
        List<JsonObject> idList = (List<JsonObject>) params.getValue(Constants.PARAMS_IDLIST);
        int count = 0;
        for (JsonObject id : idList) {
            if (this.deleteStateById(id).getBoolean(Constants.RESULT_OB_FLAG)) {
                count++;
            }
        }
        return new JsonObject(Json.encode(new BaseOb().setMsg(count + "个元素被禁用")));
    }

    /**
     * 更新对象。
     * 对象必须设置ID
     * @param params 实体的Id(params.getValue("id"))不能为null
     * @return int 受影响结果数
     */
    @Override
    public JsonObject update(JsonObject params) {
        if (params == null || MapUtil.isEmpty(params, Constants.POJO_ID)) {
            return new JsonObject(Json.encode(BaseOb.getFaildOb().setMsg("元素为空或id为空！")));
        }
        int id = (Integer) params.getValue(Constants.POJO_ID);
        if (id <= 0) {
            return new JsonObject(Json.encode(BaseOb.getFaildOb().setMsg("元素id必须大于0！")));
        }
        int count = getBaseDao().update(params.getMap());
        return getResultBaseOb(count> 0, count + "个元素被更新", "更新元素失败");
    }

    /**
     * 根据条件查询记录数。
     * @param query 查询对象，如果为null，则查询对象总数
     * @return long 记录总数
     */
    @Override
    public JsonObject selectCount(JsonObject query) {

        long count = getBaseDao().selectCount(query.getMap());
        return new JsonObject(Json.encode(new BaseOb().setMsg("查询结果包含" + count + "个元素")));
    }

    /**
     * 根据条件查询记录数。（除了本身ID之外的）
     * @param query 查询对象，如果为null，则查询对象总数
     * @return long 记录总数
     */
    @Override
    public JsonObject selectCountExtId(JsonObject query) {
        long count = getBaseDao().selectCountExtId(query.getMap());
        return new JsonObject(Json.encode(new BaseOb().setMsg("查询结果包含" + count + "个元素")));
    }

    /**
     * 通过Id查询一个对象。
     * @param params 主键params.get("id")，不能为null
     * @return  结果对象，如果未找到返回null
     */
    @Override
    public JsonObject selectById(JsonObject params) {
        if (params == null || MapUtil.isEmpty(params, Constants.POJO_ID)) {
            return new JsonObject(Json.encode(BaseOb.getFaildOb().setMsg("元素为空或id为空！")));
        }
        int id = (Integer) params.getValue(Constants.POJO_ID);
        if (id <= 0) {
            return new JsonObject(Json.encode(BaseOb.getFaildOb().setMsg("元素id必须大于0！")));
        }
        return getResultOneOb(getBaseDao().selectById(id), "元素查找成功", "元素查找失败");
    }

    /**
     * 根据条件查询所有对象列表。
     * @param obj 查询参数
     * @return 结果对象列表
     */
    @Override
    public JsonObject selectList(JsonObject obj) {
        List<T> result = getBaseDao().selectList(obj.getMap());
        OneOb<List<T>> obList = new OneOb<>();
        obList.setOb(result);
        obList.setMsg("查询成功，结果包含" + result.size() + "个元素");
        return new JsonObject(Json.encode(obList));
    }

    /**
     * 根据条件查询一个对象。
     * 不适合从大量数据中获取一条。适合根据条件筛选的结果集比较小
     * @param obj 查询对象，不能为null
     * @return 结果对象
     */
    @Override
    public JsonObject selectOne(JsonObject obj) {
        obj.put(Constants.PARAMS_LIMIT, 1);
        List<T> list = (List<T>) this.selectTopList(obj).getValue(Constants.RESULT_OB_OB);
        if(list != null && list.size() > 0){
            return getResultOneOb(list.get(0), "查询首元素成功", "查询失败不含该元素");
        }
        return new JsonObject(Json.encode(BaseOb.getFaildOb().setMsg("查询失败不含该元素")));
    }

    /**
     * 根据条件查询多个对象。
     *params.get("limit")
     * @param params 查询对象，不能为null
     * @return 结果对象
     */
    @Override
    public JsonObject selectTopList(JsonObject params) {
        if (MapUtil.isEmpty(params, Constants.PARAMS_LIMIT)) {
            return new JsonObject(Json.encode(BaseOb.getFaildOb().setMsg("查询数量参数不正确！")));
        }
        int count = (Integer) params.getValue(Constants.PARAMS_LIMIT);
        if (count < 1) {
            return new JsonObject(Json.encode(BaseOb.getFaildOb().setMsg("查询数量不能小于1！")));
        }
        List<T> result = getBaseDao().selectTopList(params.getMap());
        OneOb<List<T>> obList = new OneOb<>();
        obList.setOb(result);
        obList.setMsg("查询成功，结果包含" + result.size() + "个元素");
        return new JsonObject(Json.encode(obList));
    }

    /**
     * 分页查询列表，带查询条件。
     * @return List 结果列表
     */
    @Override
    public JsonObject selectPageList(JsonObject obj) {
        int page = obj.containsKey(Constants.PARAMS_PAGE)?(Integer) obj.getValue(Constants.PARAMS_PAGE):
                Constants.DEFAULT_PAGE;
        int limit = obj.containsKey(Constants.PARAMS_LIMIT)?(Integer) obj.getValue(Constants.PARAMS_LIMIT):
                Constants.DEFAULT_LIMIT;
        PageBounds pb = new PageBounds(page, limit);
        PageList<T> result = getBaseDao().selectPageList(obj.getMap(), pb);
        ListOb<T> ob = new ListOb();
        ob.setListob(result);
        ob.setPage(result.getPaginator());
        return new JsonObject(Json.encode(ob));
    }

    private JsonObject getResultBaseOb(boolean isTrue, String trueMsg, String wrongMsg) {
        return isTrue?new JsonObject(Json.encode(trueMsg.isEmpty()?new BaseOb():new BaseOb().setMsg(trueMsg)))
                :new JsonObject(Json.encode(wrongMsg.isEmpty()?BaseOb.getFaildOb():BaseOb.getFaildOb().setMsg(wrongMsg)));
    }
    private JsonObject getResultOneOb(T result, String trueMsg, String wrongMsg) {
        return result != null?new JsonObject(Json.encode(trueMsg.isEmpty()?new OneOb().setOb(result):new OneOb().setOb(result).setMsg(trueMsg)))
                :new JsonObject(Json.encode(wrongMsg.isEmpty()?BaseOb.getFaildOb():BaseOb.getFaildOb().setMsg(wrongMsg)));
    }
}
