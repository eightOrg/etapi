package com.eight.trundle.db.service.impl;

import com.eight.trundle.Constants;
import com.eight.trundle.db.dao.BaseDao;
import com.eight.trundle.db.pojo.Identifiable;
import com.eight.trundle.db.service.BaseService;
import com.eight.trundle.ob.BaseOb;
import com.eight.trundle.ob.ListOb;
import com.eight.trundle.ob.OneOb;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by miao on 2016/8/12.
 */
@Service("BaseService")
public abstract class BaseServiceImpl<T extends Identifiable> implements BaseService<T> {
    /**
     * 获取Dao，在子类里通过注入的方式实现
     * @return
     */
    protected abstract BaseDao<T> getBaseDao();

    /**
     * 添加对象。
     * @param obj 要实例化的实体，不能为null
     * @return 受影响的结果数
     */
    @Override
    public JsonObject insert(T obj) {
        if (obj == null) {
            return new JsonObject(Json.encode(BaseOb.getFaildOb()));
        }
        if (!StringUtils.hasText(obj.getState())) {
            obj.setState(Constants.STATE_OK);
        }
        if (obj.getCreateTime() == null) {
            obj.setCreateTime(System.currentTimeMillis());
        }
        if (obj.getChangeTime() == null) {
            obj.setChangeTime(System.currentTimeMillis());
        }
        int count = getBaseDao().insert(obj);
        return getResultBaseOb(count > 0, count + "个元素被插入", "插入元素失败");
    }

    /**
     * 批量添加对象。
     * 如果为空列表则直接返回
     * @param objList 需要批量插入的实体对象列表
     */
    @Override
    public JsonObject insertInBatch(List<T> objList) {
        if (objList == null || objList.isEmpty()) {
            return new JsonObject(Json.encode(BaseOb.getFaildOb().setMsg("元素插入列表不能为空！")));
        }
        for (T obj : objList) {
            this.insert(obj);
        }
        return new JsonObject(Json.encode(new BaseOb()));
    }

    /**
     * 删除对象。
     * 实体类的参数作为查询条件
     * @param obj 要删除的实体对象，不能为null
     * @return int 受影响结果数
     */
    @Override
    public JsonObject delete(T obj) {
        int count = getBaseDao().delete(obj);
        return getResultBaseOb(count> 0, count + "个元素被删除", "删除元素失败，不含该元素");
    }

    /**
     * 删除所有对象。
     * @return int 受影响结果数
     */
    @Override
    public JsonObject deleteAll() {
        return this.delete(null);
    }

    /**
     * 根据Id删除对象。
     * @param id  要删除的ID，不能为null
     * @return int 受影响结果数
     */
    @Override
    public JsonObject deleteById(int id) {
        if (id <= 0) {
            return new JsonObject(Json.encode(BaseOb.getFaildOb().setMsg("元素id必须大于0！")));
        }
        int count = getBaseDao().deleteById(id);
        return getResultBaseOb(count> 0, count + "个元素被删除", "删除元素失败，不含该元素");
    }

    /**
     * 根据id，批量删除记录。
     * 如果传入的列表为null或为空列表则直接返回
     * @param idList 批量删除ID列表
     */
    @Override
    public JsonObject deleteByIdInBatch(List<Integer> idList) {
        if (idList == null || idList.isEmpty()) {
            return new JsonObject(Json.encode(BaseOb.getFaildOb().setMsg("元素删除列表为空！")));
        }
        int count = 0;
        for (int id : idList) {
            if (this.deleteById(id).getBoolean(Constants.RESULT_OB_FLAG)) {
                count++;
            }
        }
        return new JsonObject(Json.encode(new BaseOb().setMsg(count + "个元素被删除")));
    }

    /**
     * 根据Id删除对象。（只改状态，不实际删除）
     * @param id  要删除的ID，不能为null
     * @return int 受影响结果数
     */
    @Override
    public JsonObject deleteStateById(int id){
        if (id <= 0){
            return new JsonObject(Json.encode(BaseOb.getFaildOb().setMsg("元素id必须大于0！")));
        }
        int count = getBaseDao().deleteStateById(id);
        return getResultBaseOb(count> 0, count + "个元素被禁用", "禁用元素失败");
    }

    /**
     * 根据id，批量删除记录。（只改状态，不实际删除）
     * 如果传入的列表为null或为空列表则直接返回
     * @param idList 批量删除ID列表
     */
    @Override
    public JsonObject deleteStateByIdInBatch(List<Integer> idList){
        if (idList == null || idList.isEmpty()) {
            return new JsonObject(Json.encode(BaseOb.getFaildOb().setMsg("元素禁用列表为空！")));
        }
        int count = 0;
        for (int id : idList) {
            if (this.deleteStateById(id).getBoolean(Constants.RESULT_OB_FLAG)) {
                count++;
            }
        }
        return new JsonObject(Json.encode(new BaseOb().setMsg(count + "个元素被禁用")));
    }

    /**
     * 更新对象。
     * 对象必须设置ID
     * @param obj 实体的Id不能为null
     * @return int 受影响结果数
     */
    @Override
    public JsonObject update(T obj) {
        if (obj == null || obj.getId() <= 0) {
            return new JsonObject(Json.encode(BaseOb.getFaildOb().setMsg("元素为空或id错误！")));
        }
        int count = getBaseDao().update(obj);
        return getResultBaseOb(count> 0, count + "个元素被更新", "更新元素失败");
    }

    /**
     * 查询总记录数。
     * @return long 记录总数
     */
    @Override
    public JsonObject selectCount() {
        return this.selectCount(null);
    }

    /**
     * 根据条件查询记录数。
     * @param obj 查询对象，如果为null，则查询对象总数
     * @return long 记录总数
     */
    @Override
    public JsonObject selectCount(T obj) {

        long count = getBaseDao().selectCount(obj);
        return new JsonObject(Json.encode(new BaseOb().setMsg("查询结果包含" + count + "个元素")));
    }

    /**
     * 根据条件查询记录数。（除了本身ID之外的）
     * @param obj 查询对象，如果为null，则查询对象总数
     * @return long 记录总数
     */
    @Override
    public JsonObject selectCountExtId(T obj) {
        long count = getBaseDao().selectCountExtId(obj);
        return new JsonObject(Json.encode(new BaseOb().setMsg("查询结果包含" + count + "个元素")));
    }

    /**
     * 通过Id查询一个对象。
     * @param id 主键，不能为null
     * @return  结果对象，如果未找到返回null
     */
    @Override
    public JsonObject selectById(int id) {
        if(id <= 0){
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
    public JsonObject selectList(T obj) {
        List<T> result = getBaseDao().selectList(obj);
        OneOb<List<T>> obList = new OneOb<>();
        obList.setOb(result);
        obList.setMsg("查询成功，结果包含" + result.size() + "个元素");
        return new JsonObject(Json.encode(obList));
    }

    /**
     * 查询所有记录列表。
     * @return List 结果列表
     */
    @Override
    public JsonObject selectList() {
        return this.selectList(null);
    }

    /**
     * 根据条件查询一个对象。
     * 不适合从大量数据中获取一条。适合根据条件筛选的结果集比较小
     * @param obj 查询对象，不能为null
     * @return 结果对象
     */
    @Override
    public JsonObject selectOne(T obj) {
        List<T> list = (List<T>) this.selectTopList(1,obj).getValue(Constants.RESULT_OB_OB);
        if(list != null && list.size() > 0){
            return getResultOneOb(list.get(0), "查询首元素成功", "查询失败不含该元素");
        }
        return null;
    }

    /**
     * 根据条件查询多个对象。
     * @param obj 查询对象，不能为null
     * @param count 查询数量，不能小于1
     * @return 结果对象
     */
    @Override
    public JsonObject selectTopList(int count, T obj) {
        if (count < 1) {
            return new JsonObject(Json.encode(BaseOb.getFaildOb().setMsg("查询数量不能小于1！")));
        }
        List<T> result = getBaseDao().selectTopList(count, obj);
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
    public JsonObject selectPageList(T obj, PageBounds pb) {
        PageList<T> result = getBaseDao().selectPageList(obj, pb);
        ListOb<T> ob = new ListOb();
        ob.setListob(result);
        ob.setPage(result.getPaginator());
        return new io.vertx.core.json.JsonObject(Json.encode(ob));
    }

    private JsonObject getResultBaseOb(boolean isTrue, String trueMsg, String wrongMsg) {
        return isTrue?new JsonObject(Json.encode(trueMsg.isEmpty()?new BaseOb():new BaseOb().setMsg(trueMsg)))
                :new JsonObject(Json.encode(wrongMsg.isEmpty()?BaseOb.getFaildOb():BaseOb.getFaildOb().setMsg(wrongMsg)));
    }
    private JsonObject getResultOneOb(T result, String trueMsg, String wrongMsg) {
        return result == null?new JsonObject(Json.encode(trueMsg.isEmpty()?new OneOb().setOb(result):new OneOb().setOb(result).setMsg(trueMsg)))
                :new JsonObject(Json.encode(wrongMsg.isEmpty()?BaseOb.getFaildOb():BaseOb.getFaildOb().setMsg(wrongMsg)));
    }
}
