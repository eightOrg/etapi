package com.eight.trundle.mail;

import java.util.ArrayList;
import java.util.List;

public class Mail {
	
	//发件人
    private String fromAddress;  
    //收件人  
    private String[] toAddress;
	//主题  
    private String subject;  
    //正文
    private String content;
    //附件
    private List<String> files = new ArrayList<String>();

	public List<String> getFiles() {
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String[] getToAddress() {
		return toAddress;
	}

	public void setToAddress(String[] toAddress) {
		this.toAddress = toAddress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
    
    

}
