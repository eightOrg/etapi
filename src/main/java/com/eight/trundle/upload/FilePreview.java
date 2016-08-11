package com.eight.trundle.upload;

import java.util.List;

public class FilePreview {

	private List<String> initialPreview;
	private List<FilePreviewConfig> initialPreviewConfig;
	private boolean append = true;
	private BaseFile basefile;
	
	public List<String> getInitialPreview() {
		return initialPreview;
	}
	public void setInitialPreview(List<String> initialPreview) {
		this.initialPreview = initialPreview;
	}
	public List<FilePreviewConfig> getInitialPreviewConfig() {
		return initialPreviewConfig;
	}
	public void setInitialPreviewConfig(List<FilePreviewConfig> initialPreviewConfig) {
		this.initialPreviewConfig = initialPreviewConfig;
	}
	public boolean isAppend() {
		return append;
	}
	public void setAppend(boolean append) {
		this.append = append;
	}
	public BaseFile getBasefile() {
		return basefile;
	}
	public void setBasefile(BaseFile basefile) {
		this.basefile = basefile;
	}
	
	

}
