package com.eight.trundle.db.dao;

/**
 * Created by miaoch on 2016/4/5.
 */


import com.eight.trundle.db.pojo.Identifiable;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

public interface BaseDao<T extends Identifiable> {
    /**
     * 添加对象。
     * @param obj 要实例化的实体，不能为null
     * @return 插入数据的主键id
     */
    public int insert(Map obj);

    /**
     * 删除对象。
     * 查询条件
     * @param obj 要删除的实体对象，不能为null
     * @return int 受影响结果数
     */
    public int delete(Map obj);

    /**
     * 根据ID删除对象。
     * @param id 要删除的实体对象的ID，不能为null
     * @return int 受影响结果数
     */
    public int deleteById(int id);

    /**
     * 根据ID删除对象。(只改状态，不实际删除)
     * @param id 要删除的实体对象的ID，不能为null
     * @return int 受影响结果数
     */
    public int deleteStateById(int id);

    /**
     * 更新对象。
     * 对象必须设置ID
     * @param obj 实体的Id不能为null
     * @return int 受影响结果数
     */
    public int update(Map obj);

    /**
     * 查询记录数
     * @param query 查询对象，如果为null，则查询对象总数
     * @return long 记录总数
     */
    public Long selectCount(Map query);

    /**
     * 通过Id查询一个对象
     * @param id 主键，不能为null
     * @return  结果对象，如果未找到返回null
     */
    public T selectById(int id);

    /**
     * 查询对象列表
     * @param obj 查询参数
     * @return 结果对象列表
     */
    public List<T> selectList(Map obj);

    /**
     * 查询前多少条数据 obj.get("limit")
     * @param obj 查询参数
     * @return
     */
    public List<T> selectTopList(Map obj);

    /**
     * 查询第一条数据
     * @param obj 查询参数
     * @return 结果对象
     */
    public T selectOne(Map obj);

    /**
     * 分页查询
     * @param obj 分页对象
     * @param pb 分页信息
     * @return 结果对象列表
     */
    public PageList<T> selectPageList(Map obj, PageBounds pb);

}
