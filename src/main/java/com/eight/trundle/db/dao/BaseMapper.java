package com.eight.trundle.db.dao;

/**
 * Created by miaoch on 2016/4/5.
 */


import com.eight.trundle.db.pojo.Identifiable;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

import java.util.List;

public interface BaseMapper<T extends Identifiable> {
    /**
     * 添加对象。
     * @param obj 要实例化的实体，不能为null
     * @return 受影响的结果数
     */
    public int insert(T obj);

    /**
     * 删除对象。
     * 查询条件
     * @param entity 要删除的实体对象，不能为null
     * @return int 受影响结果数
     */
    public int delete(T obj);

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
     * @param entity 实体的Id不能为null
     * @return int 受影响结果数
     */
    public int update(T obj);

    /**
     * 查询记录数
     * @param query 查询对象，如果为null，则查询对象总数
     * @return long 记录总数
     */
    public Long selectCount(T query);

    /**
     * 查询记录数（除了本身ID之外的）
     * @param query 查询对象，如果为null，则查询对象总数
     * @return long 记录总数
     */
    public Long selectCountExtId(T query);

    /**
     * 通过Id查询一个对象，如果id为null这会抛出IllegalArgumentException异常
     * @param id 主键，不能为null
     * @return  结果对象，如果未找到返回null
     */
    public <V extends T> V selectById(int id);

    /**
     * 查询对象列表
     * @param query 查询参数
     * @return 结果对象列表
     */
    public <V extends T> List<V> selectList(T obj);

    /**
     * 分页查询
     * @param page 分页对象
     * @return 结果对象列表
     */
    public <V extends T> List<V> selectPageList(T obj, PageBounds pb);

}
