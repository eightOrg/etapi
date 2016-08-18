package com.eight.trundle.db.pojo;

import java.io.Serializable;

public interface Identifiable extends Serializable {
	/**
	 * 获取主键
	 * @return
	 */
	public int getId();

	/**
	 * 设置ID属性
	 * @param id
	 */
	public void setId(int id);
	
	/**
	 * 获取状态
	 * @return
	 */
	public String getState();

	/**
	 * 设置state属性
	 * @param
	 */
	public void setState(String state);
	
	/**
	 * 获取创建时间
	 * @return
	 */
	public Long getCreateTime();

	/**
	 * 设置createTime属性
	 * @param
	 */
	public void setCreateTime(Long createTime);

	/**
	 * 获取修改时间
	 * @return
	 */
	public Long getChangeTime();

	/**
	 * 设置changeTime属性
	 * @param
	 */
	public void setChangeTime(Long changeTime);
	
}
