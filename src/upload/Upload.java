package upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.struts2.ServletActionContext;

import util.Convert;

import com.opensymphony.xwork2.ActionSupport;

public class Upload extends ActionSupport{
	
	private File file; // 上传到文件
	private String fileContentType; // xxxContentType
	private String fileFileName;// xxxFileName
	private String description;

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	
	public String execute()
	{
		return "input";
	}
	
	public String upload() throws Exception {
		File saved = new File(ServletActionContext.getServletContext()
				.getRealPath("files"), fileFileName);
		InputStream ins = null;
		OutputStream ous = null;

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
		//文件转换测试
		String officeFile=ServletActionContext.getServletContext()
		.getRealPath("files")+"/word.doc";
		String pdfFile=ServletActionContext.getServletContext()
		.getRealPath("pdfs")+"/testpdf.pdf";
		String swfFile=ServletActionContext.getServletContext()
		.getRealPath("swfs")+"/testswf.swf";
		
		Convert c=new Convert(officeFile,pdfFile,swfFile);
		c.office2PDF();
		c.pdf2swf();
		

		
		return "success";
	}

}
