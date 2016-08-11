package com.eight.trundle.ztree;

import java.io.Serializable;

public class ZtreeNode implements Serializable{
	//设置该节点为文件夹图标
	public static final String FOLDER_NODE = "folderNode";
	//触发AJAX获取页面
	public static final String PAGE_BTN_FUNCTION = "return getPageBtn(this);";
	//指定返回页面显示的区域ID
	public static final String DIV_RIGHT_CONTENT = "divrightcontent";

	private String id;
    private String pId;
    private String name;
    private boolean open;
    private String url;
    private String target;
    private boolean nocheck;
    private String iconSkin;
    private String param;
    private String click;
    
    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPId() {
		return pId;
	}
	public void setPId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public boolean isNocheck() {
		return nocheck;
	}
	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}
	public String getIconSkin() {
		return iconSkin;
	}
	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getClick() {
		return click;
	}
	public void setClick(String click) {
		this.click = click;
	}
    
	
	public int hashCode() {
		int result;
		result = (name == null ? 0 : name.hashCode());
		result = 37 * result + (id == null ? 0 : id.hashCode());
		return result;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ZtreeNode)) {
			return false;
		}
		final ZtreeNode other = (ZtreeNode) o;
		if (this.name.equals(other.getName()) && this.id.equals(other.getId())) {
			return true;
		} else {
			return false;
		}
	}
    

}
