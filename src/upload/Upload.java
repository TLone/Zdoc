package upload;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringBufferInputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import util.Convert;

import com.opensymphony.xwork2.ActionSupport;

import dao.Dao;

public class Upload extends ActionSupport {

	private File file; // 上传的文件
	private String fileContentType; // xxxContentType
	private String fileFileName;// xxxFileName
	private String allowedTypes; // 允许上传到文件类型 struts.xml中配置
	private String description;// form中的文件描述字段
	private String type;// form中的文件类型字段
	private String result; // 结果

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getAllowedTypes() {
		return allowedTypes;
	}

	public void setAllowedTypes(String allowedTypes) {
		this.allowedTypes = allowedTypes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	// 此方法用于过滤文件类型
	public String filterType(String[] types) {
		// 获取希望上传的文件类型
		String fileType = getFileContentType();
		for (String type : types) {
			if (type.equals(fileType)) {
				return "yes";
			}
		}
		return "no"; // 文件类型不匹配 则ajax返回error
	}

	public String execute() throws Exception {
		String officesDir = "/offices/";// office 文件上传后到保存路径
		int size = 0; // 文件大小 kb为单位

		if (getFileFileName() != null && getType() != null) {
			// 在所有操作之前 先进行文件类型检测
			String filterResult = filterType(getAllowedTypes().split(","));
			if (filterResult.equals("no")) {
				// 文件類型不匹配
				result = "文件類型不匹配";
				System.out.println(result);
				return SUCCESS;
			}

			// ready to copy file
			String savedFileName=getFileFileName()+"_"+new Date();//最后保存到文件名，加上日期防止重复
			File saved = new File(officesDir, savedFileName);
			InputStream ins = null;
			OutputStream ous = null;
			size = (int)saved.length() / 1024;

			try {
				saved.getParentFile().mkdirs();// 确保目的路径存在

				ins = new FileInputStream(file);
				ous = new FileOutputStream(saved);

				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = ins.read(buffer)) != -1) {
					ous.write(buffer, 0, len);
				}
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if (ous != null)
					ous.close();
				if (ins != null)
					ins.close();

			}
			
			//Dao
			Dao d=new Dao();
			//String officepath,String originnaltype,int size,String description,String type
			String officepath=officesDir+savedFileName;
			String originnaltype=getFileContentType();
			//size 在之前已经计算
			//getDescription()
			//getType()
			
		
			
			if(!d.officeUpload(officepath, originnaltype, size, getDescription(), getType()))
			{
				result = "上传成功";
				System.out.println(result);
				return SUCCESS;
			}
			else
			{
				result = "数据库写入失败";
				System.out.println(result);
				return SUCCESS;
			}
			
		} else {
			result = "請选择文件";
			System.out.println(result);
			return SUCCESS;
		}

	}
	/*
	 * public String getAllowTypes() { return allowTypes; }
	 * 
	 * public void setAllowTypes(String allowTypes) { this.allowTypes =
	 * allowTypes; }
	 * 
	 * public String getType() { return type; }
	 * 
	 * public void setType(String type) { this.type = type; }
	 * 
	 * public long getSize() { return size; }
	 * 
	 * public void setSize(long size) { this.size = size; }
	 * 
	 * private final String officesDir = "/offices/"; private final String
	 * pdfsDir = "/pdfs/"; private final String swfsDir =
	 * ServletActionContext.getServletContext() .getRealPath("swfs") + "/";
	 * String originnaltype = "";// 原始类型 String contributer = "";// 贡献者 long
	 * size = 0; // 文件大小
	 * 
	 * public String getDescription() { return description; }
	 * 
	 * public void setDescription(String description) { this.description =
	 * description; }
	 * 
	 * public File getFile() { return file; }
	 * 
	 * public void setFile(File file) { this.file = file; }
	 * 
	 * public String getFileContentType() { return fileContentType; }
	 * 
	 * public void setFileContentType(String fileContentType) {
	 * this.fileContentType = fileContentType; }
	 * 
	 * public String getFileFileName() { return fileFileName; }
	 * 
	 * public void setFileFileName(String fileFileName) { this.fileFileName =
	 * fileFileName; }
	 * 
	 * public String execute() { return "input"; }
	 * 
	 * public String upload() throws Exception { // File saved = new
	 * File(ServletActionContext.getServletContext() // .getRealPath("files"),
	 * fileFileName); if (getDescription() != null && getFileFileName() != null)
	 * { File saved = new File(officesDir, getFileFileName()); InputStream ins =
	 * null; OutputStream ous = null; size = saved.length() / 1024; try {
	 * saved.getParentFile().mkdirs();// 确保目的路径存在
	 * 
	 * ins = new FileInputStream(file); ous = new FileOutputStream(saved);
	 * 
	 * byte[] buffer = new byte[1024]; int len = 0; while ((len =
	 * ins.read(buffer)) != -1) { ous.write(buffer, 0, len); } } catch
	 * (Exception e) { e.printStackTrace();
	 * 
	 * } finally { if (ous != null) ous.close(); if (ins != null) ins.close();
	 * 
	 * }
	 * 
	 * int fileIndex = fileFileName.indexOf("."); String trueFileName =
	 * fileFileName.substring(0, fileIndex);// 获得文件名，去除后缀
	 * 
	 * String officePath = officesDir + fileFileName; String pdfPath = pdfsDir +
	 * trueFileName + ".pdf"; String swfPath = swfsDir + trueFileName + ".swf";
	 * 
	 * // fileContentType contributer = "游客"; // size; // description
	 * 
	 * // 文件转换测试 Convert c = new Convert(officePath, pdfPath, swfPath);
	 * c.office2PDF(); c.pdf2swf();
	 * 
	 * // 文件信息保存到数据库officepath pdfpath swfpath originnaltype contributer // size
	 * // description Dao d = new Dao();
	 * 
	 * int hit=1; int good=0; d.insertFile(officePath, pdfPath, swfPath,
	 * originnaltype, contributer, size, description,hit,type,good);
	 * 
	 * // session设置swf文件到路径 让show.jsp页面展示 HttpServletRequest request =
	 * ServletActionContext.getRequest(); HttpSession session =
	 * request.getSession(); session.setAttribute("swfPath", "./swfs/" +
	 * trueFileName + ".swf"); System.out.println(type); return "success"; }
	 * else { return "error"; } }
	 */

}
