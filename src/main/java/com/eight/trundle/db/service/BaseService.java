package com.eight.trundle.db.service;

import com.eight.trundle.db.pojo.Identifiable;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * Created by miaoch on 2016/8/12.
 */
public interface BaseService<T extends Identifiable> {

    /**
     * 添加对象。
     * 如果要添加的对象没有设置Id或者Id为空字符串或者是空格，则添加数据之前会调用 generateId()方法设置Id
     * @param params 要实例化的实体，不能为null
     * @return 受影响的结果数
     */
    public JsonObject insert(JsonObject params);

    /**
     * params.getValue("objList") 需要批量插入的实体对象列表
     * 批量添加对象。
     * 如果为空列表则直接返回
     * @param params 需要批量插入的实体对象列表
     */
    public JsonObject insertInBatch(JsonObject params);

    /**
     * 删除对象。
     * 实体类的参数作为查询条件
     * @param params 要删除的实体对象，不能为null
     * @return int 受影响结果数
     */
    public JsonObject delete(JsonObject params);

    /**
     * 根据Id删除对象。
     * @param params  要删除的ID(params.get("id"))，不能为null
     * @return int 受影响结果数
     */
    public JsonObject deleteById(JsonObject params);

    /**
     * params.getValue("idList")
     * 根据id，批量删除记录。
     * 如果传入的列表为null或为空列表则直接返回
     * @param params 批量删除ID列表
     */
    public JsonObject deleteByIdInBatch(JsonObject params);

    /**
     * 根据Id删除对象。（只改状态，不实际删除）
     * @param params  要删除的ID(params.getValue("id"))，不能为null
     * @return int 受影响结果数
     */
    public JsonObject deleteStateById(JsonObject params);

    /**
     * 根据id，批量删除记录。（只改状态，不实际删除）
     * 如果传入的列表为null或为空列表则直接返回
     * @param params params.getValue("idList")批量删除ID列表
     */
    public JsonObject deleteStateByIdInBatch(JsonObject params);


    /**
     * 更新对象。
     * 对象必须设置ID
     * @param params 实体的Id不能为null
     * @return int 受影响结果数
     */
    public JsonObject update(JsonObject params);

    /**
     * 根据条件查询记录数。
     * @param query 查询对象，如果为null，则查询对象总数
     * @return long 记录总数
     */
    public JsonObject selectCount(JsonObject query);

    /**
     * 根据条件查询记录数。（除了本身ID之外的）
     * @param query 查询对象，如果为null，则查询对象总数
     * @return long 记录总数
     */
    public JsonObject selectCountExtId(JsonObject query);

    /**
     * 通过Id查询一个对象。
     * @param params 主键params.get("id")，不能为null
     * @return  结果对象，如果未找到返回null
     */
    public JsonObject selectById(JsonObject params);

    /**
     * 根据条件查询所有对象列表。
     * @param query 查询参数
     * @return 结果对象列表
     */
    public JsonObject selectList(JsonObject query);

    /**
     * 查询前多少条记录列表。
     * @param obj 查询条件
     * @return
     */
    public JsonObject selectTopList(JsonObject obj);

    /**
     * 根据条件查询一个对象。
     * @param obj 查询对象，不能为null
     * @return 结果对象
     */
    public JsonObject selectOne(JsonObject obj);

    /**
     * 分页查询列表，带查询条件。
     * @return List 结果列表
     */
    public JsonObject selectPageList(JsonObject obj);

}
