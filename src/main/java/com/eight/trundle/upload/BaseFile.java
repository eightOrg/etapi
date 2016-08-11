package com.eight.trundle.upload;

public class BaseFile {

	private String newPath;
	private String type;
    private String oldName;
    private String extension;
    private int width;
    private int height;
    private long lengthK;
    
    
	public String getNewPath() {
		return newPath;
	}
	public void setNewPath(String newPath) {
		this.newPath = newPath;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOldName() {
		return oldName;
	}
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public long getLengthK() {
		return lengthK;
	}
	public void setLengthK(long lengthK) {
		this.lengthK = lengthK;
	}
    
    

}
