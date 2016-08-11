/*
 * UserController.java
 * Copyright(C) 2015 杭州天翼智慧城市科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2015-08-31 Created
 */
package com.eight.trundle.upload;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.eight.trundle.common.DateUtils;
import com.eight.trundle.message.Message;
import com.eight.trundle.upload.BaseFile;
import com.eight.trundle.upload.FileError;
import com.eight.trundle.upload.FilePreview;
import com.eight.trundle.upload.FilePreviewConfig;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 附件上传类
 * @author micktiger
 * 先实现测试，代码待整理。。。
 */
@Controller
@RequestMapping(value="/file")
public class FileController {
	
	/*private Logger logger = Logger.getLogger(FileController.class);
	
	//默认处理文件上传的编码
	private String FILE_UPLOAD_ENCODING = Message.getConfig("file_upload_encoding", "UTF-8");
	//默认的图片格式
	private String FILE_UPLOAD_IMGEXTENSION = Message.getConfig("file_upload_extension", ".jpg,.png,.jpeg,.gif,.bmp");

    *//**
     * 图片上传--ckeditor4.5
     * @param model
     * @return
    *//*
    @RequestMapping(value="/ckeditorImg", method=RequestMethod.POST)
    public String ckeditorImg(@RequestParam MultipartFile upload, HttpServletRequest request, HttpServletResponse response){
    	logger.info("upload ckeditorImg...");
    	
		PrintWriter pout = null;
        
		//附件默认保存路径
		String midpath = "/ckeditor";
		String _midpath = request.getParameter("midpath");
		if(_midpath != null && _midpath.trim().length() > 0) midpath = _midpath;
		//允许上传的附件后缀名,统一转化为小写
		String extension = "";
		String _extension = request.getParameter("extension");
		if(_extension != null && _extension.trim().length() > 0) extension = _extension;
		//限制图片大小，如果超出限制，则压缩，单位是K
		String maxpicm = "";
		String _maxpicm = request.getParameter("maxpicm");
		if(_maxpicm != null && _maxpicm.trim().length() > 0) maxpicm = _maxpicm;
		//限制图片宽度，如果超出则等比例缩小
		String maxpicwidth = "";
		String _maxpicwidth = request.getParameter("maxpicwidth");
		if(_maxpicwidth != null && _maxpicwidth.trim().length() > 0) maxpicwidth = _maxpicwidth;
		//限制图片高度，如果超出则等比例缩小		
		String maxpicheight = "";
		String _maxpicheight = request.getParameter("maxpicheight");
		if(_maxpicheight != null && _maxpicheight.trim().length() > 0) maxpicheight = _maxpicheight;
		
		try {
			request.setCharacterEncoding(FILE_UPLOAD_ENCODING);
			response.setContentType("text/html; charset=UTF-8");
	        response.setHeader("Cache-Control", "no-cache");
	        pout = response.getWriter();
	        
			String fileName = upload.getOriginalFilename();
			String fileextension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
			//后缀名不允许
			if(extension == null || extension.trim().length() == 0) 
				extension = FILE_UPLOAD_IMGEXTENSION;
			if(extension.indexOf(fileextension) == -1){
				pout.println("不是正确的格式");
				return null;
			}
			//确定根目录
			String basepathStr = Message.getConfig("file_upload_path", "/upload");
			String basepath = request.getServletContext().getRealPath(basepathStr);
			//确定完整目录
			File filepath = new File(basepath + midpath);
			if(!filepath.exists()){
				filepath.mkdirs();
			}
			//附件名
			String realfilename = DateUtils.getFormatDateTime(System.currentTimeMillis(), "yyyyMMddHHmmssSS") + new Random().nextInt(1000) + fileextension;
			//写入附件
			File file = new File(basepath + midpath+"/"+realfilename);
			if (upload.getSize() != 0) {
				upload.transferTo(file);
			}
			upload = null;
			
			//如果是图片的话，判断：大小是否合适，宽高是否合适
			if(FILE_UPLOAD_IMGEXTENSION.indexOf(fileextension) > -1	){
				Image img = null;  //图片对象
				int newWidth = 0;  //压缩后的图片宽
				int newHeight = 0;  //压缩后的图片高
				BufferedImage tag = null; //压缩图片过程中需要用到的缓存对象
				FileOutputStream out = null; //写入新图片用到的对象
				JPEGImageEncoder encoder = null; //压缩图片过程中需要用到的缓存对象
				
				//判断是否需要压缩宽高
				if( (maxpicwidth != null && maxpicwidth.trim().length() > 0) || 
						(maxpicheight != null && maxpicheight.trim().length() > 0)){
					try{
						img = ImageIO.read(file); 
						double rate = 0;//需要压缩的比例
						double imgwidth = (double)img.getWidth(null);
						double imgheight = (double)img.getHeight(null);
						//如果如需要压缩图片的宽
						if(maxpicwidth != null && maxpicwidth.length() > 0){
							double maxw = Double.valueOf(maxpicwidth);
							if(imgwidth > maxw){
								double _rate = imgwidth / maxw + 0.1;
								if(_rate > rate) rate = _rate;
							}
						}
						//如果如需要压缩图片的高
						if(maxpicheight != null && maxpicheight.length() > 0){
							double maxh = Double.valueOf(maxpicheight);
							if(imgheight > maxh){
								double _rate = imgheight / maxh + 0.1;
								if(_rate > rate) rate = _rate;
							}
						}
						
						if(rate > 0){
							newWidth = (int) (imgwidth / rate);   
		                    newHeight = (int) (imgheight / rate); 
		                    tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);   
		                    //Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 
		                    //优先级比速度高 生成的图片质量比较好 但速度慢 
		                    tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);  
		                    //重新生成新的图片名
		                    realfilename = "_" + realfilename;
		                    out = new FileOutputStream(basepath + midpath+"/"+realfilename);  
		                    // JPEGImageEncoder可适用于其他图片类型的转换   
		                    encoder = JPEGCodec.createJPEGEncoder(out);   
		                    encoder.encode(tag);   
		                    out.close();  
						}
					}catch(Exception ex){	
						ex.printStackTrace();
					}finally{
						if(out != null){ out = null;}
						if(tag != null) tag = null;
						if(encoder != null) encoder = null;
						if(img != null) img = null;
					}
				}
				
				//判断是否需要判断图片的大小
				if(maxpicm != null && maxpicm.length() > 0){
					try{
						long maxpicml = Long.valueOf(maxpicm);
						maxpicml = maxpicml * 1024;
						file = new File(basepath + midpath+"/"+realfilename);
						long oldm = file.length();
						if(oldm >  maxpicml){
							img = ImageIO.read(file);
							newWidth = newWidth == 0 ? img.getWidth(null) : newWidth;
							newHeight = newHeight == 0 ? img.getHeight(null) : newHeight;
							float quality =  (float)maxpicml / (float)oldm;
							tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);   
		                    //Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 
		                    //优先级比速度高 生成的图片质量比较好 但速度慢 
		                    tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);  
		                    //重新生成新的图片名
		                    realfilename = "_" + realfilename;
		                    out = new FileOutputStream(basepath + midpath+"/"+realfilename);  
		                    // JPEGImageEncoder可适用于其他图片类型的转换   
		                    encoder = JPEGCodec.createJPEGEncoder(out);   
		                    JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
		                    jep.setQuality(quality, true);
		                    encoder.encode(tag);   
		                    out.close(); 					                    
						}
					}catch(Exception ex){
						ex.printStackTrace();
					}finally{
						if(out != null){ out = null;}
						if(tag != null) tag = null;
						if(encoder != null) encoder = null;
						if(img != null) img = null;
					}
				}
				file = null;
			}
			String resultpath = request.getContextPath() + "/upload" + midpath + "/"+realfilename;
			
			// 将上传的图片的url返回给ckeditor
            String callback = request.getParameter("CKEditorFuncNum");
            pout.println("<script type=\"text/javascript\">");
            pout.println("window.parent.CKEDITOR.tools.callFunction("
                    + callback + ",'" + resultpath + "',''" + ")");
            pout.println("</script>");
			
			return null;
					
		} catch (Exception e) {
			e.printStackTrace();
			pout.println("上传错误");
			return null;
		}finally{
			if(pout != null){
				pout.flush();
				pout.close();
			}
		}
    }
    
    *//**
     * 通用删除方法，不做任何事情
     * @param model
     * @return
    *//*
    @ResponseBody
    @RequestMapping(value="/deletefile", method=RequestMethod.DELETE)
    public Msg delete(HttpServletRequest request){
    	return new Msg();
    }
    
    *//**
     * 通用上传方法
     * @param model
     * @return
    *//*
    @ResponseBody
    @RequestMapping(value="/uploadfile", method=RequestMethod.POST)
    public Object upload(HttpServletRequest request, @RequestParam MultipartFile file){
    	logger.info("upload file...");
        
		//附件默认保存路径
		String midpath = "/file";
		String _midpath = request.getParameter("midpath");
		if(_midpath != null && _midpath.trim().length() > 0) midpath = _midpath;
		//允许上传的附件后缀名,统一转化为小写
		String extension = "";
		String _extension = request.getParameter("extension");
		if(_extension != null && _extension.trim().length() > 0) extension = _extension;
		//限制图片大小，如果超出限制，则压缩，单位是K
		String maxpicm = "";
		String _maxpicm = request.getParameter("maxpicm");
		if(_maxpicm != null && _maxpicm.trim().length() > 0) maxpicm = _maxpicm;
		//限制图片宽度，如果超出则等比例缩小
		String maxpicwidth = "";
		String _maxpicwidth = request.getParameter("maxpicwidth");
		if(_maxpicwidth != null && _maxpicwidth.trim().length() > 0) maxpicwidth = _maxpicwidth;
		//限制图片高度，如果超出则等比例缩小		
		String maxpicheight = "";
		String _maxpicheight = request.getParameter("maxpicheight");
		if(_maxpicheight != null && _maxpicheight.trim().length() > 0) maxpicheight = _maxpicheight;
		
		try {
			request.setCharacterEncoding(FILE_UPLOAD_ENCODING);
			BaseFile bf = new BaseFile();
			
			String fileName = file.getOriginalFilename(); 
			String fileextension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
			//后缀名不允许
			if(extension == null || extension.trim().length() == 0) 
				extension = FILE_UPLOAD_IMGEXTENSION;
			if(extension.indexOf(fileextension) == -1){
				FileError fe = new FileError();
				fe.setError("格式不允许："+extension);
				return fe;
			}
			//确定根目录
			String basepathStr = Message.getConfig("file_upload_path", "/upload");
			String basepath = request.getServletContext().getRealPath(basepathStr);
			//确定完整目录
			File filepath = new File(basepath + midpath);
			if(!filepath.exists()){
				filepath.mkdirs();
			}
			//附件名
			String realfilename = DateUtils.getFormatDateTime(System.currentTimeMillis(), "yyyyMMddHHmmssSS") + new Random().nextInt(1000) + fileextension;
			//写入附件
			File realfile = new File(basepath + midpath+"/"+realfilename);
			if (file.getSize() != 0) {
				file.transferTo(realfile);
			}
			file = null;
			bf.setLengthK(realfile.length()/1024);
			bf.setOldName(fileName);			
			bf.setExtension(fileextension);
			String filetype = "other";
			
			//如果是图片的话，判断：大小是否合适，宽高是否合适
			if(FILE_UPLOAD_IMGEXTENSION.indexOf(fileextension) > -1	){
				filetype = "image";
				Image img = ImageIO.read(realfile);  //图片对象
				int newWidth = 0;  //压缩后的图片宽
				int newHeight = 0;  //压缩后的图片高
				BufferedImage tag = null; //压缩图片过程中需要用到的缓存对象
				FileOutputStream out = null; //写入新图片用到的对象
				JPEGImageEncoder encoder = null; //压缩图片过程中需要用到的缓存对象
				
				bf.setWidth(img.getWidth(null));
				bf.setHeight(img.getHeight(null));
				
				//判断是否需要压缩宽高
				if( (maxpicwidth != null && maxpicwidth.trim().length() > 0) || 
						(maxpicheight != null && maxpicheight.trim().length() > 0)){
					try{
						double rate = 0;//需要压缩的比例
						double imgwidth = (double)img.getWidth(null);
						double imgheight = (double)img.getHeight(null);
						
						//如果如需要压缩图片的宽
						if(maxpicwidth != null && maxpicwidth.length() > 0){
							double maxw = Double.valueOf(maxpicwidth);
							if(imgwidth > maxw){
								double _rate = imgwidth / maxw + 0.1;
								if(_rate > rate) rate = _rate;
							}
						}
						//如果如需要压缩图片的高
						if(maxpicheight != null && maxpicheight.length() > 0){
							double maxh = Double.valueOf(maxpicheight);
							if(imgheight > maxh){
								double _rate = imgheight / maxh + 0.1;
								if(_rate > rate) rate = _rate;
							}
						}
						
						if(rate > 0){
							newWidth = (int) (imgwidth / rate);   
		                    newHeight = (int) (imgheight / rate); 
		                    tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);   
		                    //Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 
		                    //优先级比速度高 生成的图片质量比较好 但速度慢 
		                    tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);  
		                    //重新生成新的图片名
		                    realfilename = "_" + realfilename;
		                    out = new FileOutputStream(basepath + midpath+"/"+realfilename);  
		                    // JPEGImageEncoder可适用于其他图片类型的转换   
		                    encoder = JPEGCodec.createJPEGEncoder(out);   
		                    encoder.encode(tag);   
		                    out.close();  
						}
						realfile = new File(basepath + midpath+"/"+realfilename);
						img = ImageIO.read(realfile);
						bf.setLengthK(realfile.length()/1024);								
						bf.setWidth(img.getWidth(null));
						bf.setHeight(img.getHeight(null));
					}catch(Exception ex){	
						ex.printStackTrace();
					}finally{
						if(out != null){ out = null;}
						if(tag != null) tag = null;
						if(encoder != null) encoder = null;
						if(img != null) img = null;
					}
				}
				
				//判断是否需要判断图片的大小
				if(maxpicm != null && maxpicm.length() > 0){
					try{
						long maxpicml = Long.valueOf(maxpicm);
						maxpicml = maxpicml * 1024;
						realfile = new File(basepath + midpath+"/"+realfilename);
						long oldm = realfile.length();
						if(oldm >  maxpicml){
							img = ImageIO.read(realfile);
							newWidth = newWidth == 0 ? img.getWidth(null) : newWidth;
							newHeight = newHeight == 0 ? img.getHeight(null) : newHeight;
							float quality =  (float)maxpicml / (float)oldm;
							tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);   
		                    //Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 
		                    //优先级比速度高 生成的图片质量比较好 但速度慢 
		                    tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);  
		                    //重新生成新的图片名
		                    realfilename = "_" + realfilename;
		                    out = new FileOutputStream(basepath + midpath+"/"+realfilename);  
		                    // JPEGImageEncoder可适用于其他图片类型的转换   
		                    encoder = JPEGCodec.createJPEGEncoder(out);   
		                    JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
		                    jep.setQuality(quality, true);
		                    encoder.encode(tag);   
		                    out.close(); 
		                    realfile = new File(basepath + midpath+"/"+realfilename);
							bf.setLengthK(realfile.length()/1024);					
						}
					}catch(Exception ex){
						ex.printStackTrace();
					}finally{
						if(out != null){ out = null;}
						if(tag != null) tag = null;
						if(encoder != null) encoder = null;
						if(img != null) img = null;
					}
				}
				realfile = null;
			}
			String resultpath = "/upload" + midpath + "/"+realfilename;
			bf.setNewPath(resultpath);
			bf.setType(filetype);
			
			FilePreview filePreview = new FilePreview();
			
			List<String> previews = new ArrayList<String>();
			previews.add(getInitalPreview(request.getContextPath() + resultpath,filetype));
			filePreview.setInitialPreview(previews);
			
			List<FilePreviewConfig> previewConfigs = new ArrayList<FilePreviewConfig>();
			FilePreviewConfig previewConfig = new FilePreviewConfig();
			previewConfig.setCaption(fileName);
			previewConfig.setUrl(request.getContextPath()+ "/file/deletefile");
			previewConfig.setKey(realfilename);
			previewConfigs.add(previewConfig);
			filePreview.setInitialPreviewConfig(previewConfigs);
			
			filePreview.setBasefile(bf);
			
	    	return filePreview;
					
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
    }
    *//**
     * 内部方法，生成预览图
     * @param path
     * @param type
     * @return
     *//*
    private String getInitalPreview(String path,String type){
    	if("image".equals(type)){
    		return "<img src='"+path+"' class='file-preview-"+type+"' />";
    	}else{
    		return "<div class='file-preview-other'><h2><a href='"+path+"' target='_blank'><i class='glyphicon glyphicon-file'></i></a></h2></div>";
    	}
    }*/
    
    
    /**
     * 图片上传--原生基于request的上传方法，放这里备查，使用springmvc的上传拦截后废弃
     * @param model
     * @return
    */
//    @RequestMapping(value="/ckeditorImg", method=RequestMethod.POST)
//    public String ckeditorImg(Model model, HttpServletRequest request, HttpServletResponse response){
//    	logger.info("upload ckeditorImg...");
//    	
//    	RequestContext requestContext = new ServletRequestContext(request);
//		PrintWriter pout = null;
//        
//		//附件默认保存路径
//		String midpath = "/ckeditor";
//		String _midpath = request.getParameter("midpath");
//		if(_midpath != null && _midpath.trim().length() > 0) midpath = _midpath;
//		//允许上传的附件后缀名,统一转化为小写
//		String extension = "";
//		String _extension = request.getParameter("extension");
//		if(_extension != null && _extension.trim().length() > 0) extension = _extension;
//		//限制图片大小，如果超出限制，则压缩，单位是K
//		String maxpicm = "";
//		String _maxpicm = request.getParameter("maxpicm");
//		if(_maxpicm != null && _maxpicm.trim().length() > 0) maxpicm = _maxpicm;
//		//限制图片宽度，如果超出则等比例缩小
//		String maxpicwidth = "";
//		String _maxpicwidth = request.getParameter("maxpicwidth");
//		if(_maxpicwidth != null && _maxpicwidth.trim().length() > 0) maxpicwidth = _maxpicwidth;
//		//限制图片高度，如果超出则等比例缩小		
//		String maxpicheight = "";
//		String _maxpicheight = request.getParameter("maxpicheight");
//		if(_maxpicheight != null && _maxpicheight.trim().length() > 0) maxpicheight = _maxpicheight;
//		
//		// 实例化一个文件工厂
//		FileItemFactory factory = new DiskFileItemFactory();
//		// 配置上传组件ServletFileUpload
//		ServletFileUpload upload = new ServletFileUpload(factory);
//		// 图片上传的总大小：不做限制
//		upload.setSizeMax(-1);
//		try {
//			request.setCharacterEncoding(FILE_UPLOAD_ENCODING);
//			response.setContentType("text/html; charset=UTF-8");
//	        response.setHeader("Cache-Control", "no-cache");
//	        pout = response.getWriter();
//	        //不是上传的form
//			if(!FileUpload.isMultipartContent(requestContext)){
//				pout.println("不是上传控件");
//				return null;
//			}
//			// 从request得到所有上传域的列表,并逐个进行处理
//			List<FileItem> items = upload.parseRequest(requestContext);
//			Iterator it = items.iterator();
//			while(it.hasNext()){
//				FileItem fileItem = (FileItem) it.next();
//				//如果不是普通字段
//				if(!fileItem.isFormField()){
//					String fileName = fileItem.getName();
//					String fileextension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
//					//后缀名不允许
//					if(extension == null || extension.trim().length() == 0) 
//						extension = FILE_UPLOAD_IMGEXTENSION;
//					if(extension.indexOf(fileextension) == -1){
//						pout.println("不是正确的格式");
//						return null;
//					}
//					//确定根目录
//					String basepathStr = Message.getConfig("file_upload_path", "/upload");
//					String basepath = request.getServletContext().getRealPath(basepathStr);
//					//确定完整目录
//					File filepath = new File(basepath + midpath);
//					if(!filepath.exists()){
//						filepath.mkdirs();
//					}
//					//附件名
//					String realfilename = DateUtils.getFormatDateTime(System.currentTimeMillis(), "yyyyMMddHHmmssSS") + new Random().nextInt(1000) + fileextension;
//					//写入附件
//					File file = new File(basepath + midpath+"/"+realfilename);
//					if (fileItem.getName() != null && fileItem.getSize() != 0) {
//						fileItem.write(file);
//					}
//					fileItem = null;
//					
//					//如果是图片的话，判断：大小是否合适，宽高是否合适
//					if(FILE_UPLOAD_IMGEXTENSION.indexOf(fileextension) > -1	){
//						Image img = null;  //图片对象
//						int newWidth = 0;  //压缩后的图片宽
//						int newHeight = 0;  //压缩后的图片高
//						BufferedImage tag = null; //压缩图片过程中需要用到的缓存对象
//						FileOutputStream out = null; //写入新图片用到的对象
//						JPEGImageEncoder encoder = null; //压缩图片过程中需要用到的缓存对象
//						
//						//判断是否需要压缩宽高
//						if( (maxpicwidth != null && maxpicwidth.trim().length() > 0) || 
//								(maxpicheight != null && maxpicheight.trim().length() > 0)){
//							try{
//								img = ImageIO.read(file); 
//								double rate = 0;//需要压缩的比例
//								double imgwidth = (double)img.getWidth(null);
//								double imgheight = (double)img.getHeight(null);
//								//如果如需要压缩图片的宽
//								if(maxpicwidth != null && maxpicwidth.length() > 0){
//									double maxw = Double.valueOf(maxpicwidth);
//									if(imgwidth > maxw){
//										double _rate = imgwidth / maxw + 0.1;
//										if(_rate > rate) rate = _rate;
//									}
//								}
//								//如果如需要压缩图片的高
//								if(maxpicheight != null && maxpicheight.length() > 0){
//									double maxh = Double.valueOf(maxpicheight);
//									if(imgheight > maxh){
//										double _rate = imgheight / maxh + 0.1;
//										if(_rate > rate) rate = _rate;
//									}
//								}
//								
//								if(rate > 0){
//									newWidth = (int) (imgwidth / rate);   
//				                    newHeight = (int) (imgheight / rate); 
//				                    tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);   
//				                    //Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 
//				                    //优先级比速度高 生成的图片质量比较好 但速度慢 
//				                    tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);  
//				                    //重新生成新的图片名
//				                    realfilename = "_" + realfilename;
//				                    out = new FileOutputStream(basepath + midpath+"/"+realfilename);  
//				                    // JPEGImageEncoder可适用于其他图片类型的转换   
//				                    encoder = JPEGCodec.createJPEGEncoder(out);   
//				                    encoder.encode(tag);   
//				                    out.close();  
//								}
//							}catch(Exception ex){	
//								ex.printStackTrace();
//							}finally{
//								if(out != null){ out = null;}
//								if(tag != null) tag = null;
//								if(encoder != null) encoder = null;
//								if(img != null) img = null;
//							}
//						}
//						
//						//判断是否需要判断图片的大小
//						if(maxpicm != null && maxpicm.length() > 0){
//							try{
//								long maxpicml = Long.valueOf(maxpicm);
//								maxpicml = maxpicml * 1024;
//								file = new File(basepath + midpath+"/"+realfilename);
//								long oldm = file.length();
//								if(oldm >  maxpicml){
//									img = ImageIO.read(file);
//									newWidth = newWidth == 0 ? img.getWidth(null) : newWidth;
//									newHeight = newHeight == 0 ? img.getHeight(null) : newHeight;
//									float quality =  (float)maxpicml / (float)oldm;
//									tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);   
//				                    //Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 
//				                    //优先级比速度高 生成的图片质量比较好 但速度慢 
//				                    tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);  
//				                    //重新生成新的图片名
//				                    realfilename = "_" + realfilename;
//				                    out = new FileOutputStream(basepath + midpath+"/"+realfilename);  
//				                    // JPEGImageEncoder可适用于其他图片类型的转换   
//				                    encoder = JPEGCodec.createJPEGEncoder(out);   
//				                    JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
//				                    jep.setQuality(quality, true);
//				                    encoder.encode(tag);   
//				                    out.close(); 					                    
//								}
//							}catch(Exception ex){
//								ex.printStackTrace();
//							}finally{
//								if(out != null){ out = null;}
//								if(tag != null) tag = null;
//								if(encoder != null) encoder = null;
//								if(img != null) img = null;
//							}
//						}
//						file = null;
//					}
//					String resultpath = request.getContextPath() + "/upload" + midpath + "/"+realfilename;
//					
//					// 将上传的图片的url返回给ckeditor
//	                String callback = request.getParameter("CKEditorFuncNum");
//	                pout.println("<script type=\"text/javascript\">");
//	                pout.println("window.parent.CKEDITOR.tools.callFunction("
//	                        + callback + ",'" + resultpath + "',''" + ")");
//	                pout.println("</script>");
//					
//					return null;
//				}
//			}			
//		} catch (Exception e) {
//			e.printStackTrace();
//			pout.println("上传错误");
//			return null;
//		}finally{
//			if(pout != null){
//				pout.flush();
//				pout.close();
//			}
//		}
//        return null;
//    }
    
      


}