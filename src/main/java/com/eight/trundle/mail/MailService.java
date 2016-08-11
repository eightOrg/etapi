package com.eight.trundle.mail;

import java.io.File;
import java.io.IOException;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class MailService {
	
	private JavaMailSender mailSender;
    private TaskExecutor taskExecutor;
	
	private Logger logger = Logger.getLogger(MailService.class);
	
	public void sendMail(final Mail mail) {  
        taskExecutor.execute(new Runnable() {  
            public void run() {  
                try {  
                	sendMailSync(mail);  
                } catch (Exception e) {  
                	logger.error(e);  
                }  
            }  
        });  
    }  
  
    private void sendMailSync(Mail mail)  
            throws Exception {  
    	MimeMessage mime = mailSender.createMimeMessage();  
        MimeMessageHelper helper = new MimeMessageHelper(mime, true, "utf-8");  
        helper.setFrom(mail.getFromAddress());
        helper.setTo(mail.getToAddress());
        helper.setReplyTo(mail.getFromAddress());  
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getContent(), true);
        if(mail.getFiles().size() > 0){
        	for(int i = 0; i < mail.getFiles().size(); i++){
        		String filename = mail.getFiles().get(i);
        		FileSystemResource file = new FileSystemResource(new File(filename)); 
        		if(filename.indexOf("/") > -1)
        			filename = filename.substring(filename.lastIndexOf("/")+1);
        		helper.addAttachment(MimeUtility.encodeWord(filename),file);
        	}
        }        
        mailSender.send(mime);  
    }

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}  
    
    
    

}
