package upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import util.Convert;

import com.opensymphony.xwork2.ActionSupport;

import dao.Dao;

public class Upload extends ActionSupport {

	private File file; // 上传到文件
	private String fileContentType; // xxxContentType
	private String fileFileName;// xxxFileName
	private String description;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	private final String officesDir = "/offices/";
	private final String pdfsDir = "/pdfs/";
	private final String swfsDir = ServletActionContext.getServletContext()
			.getRealPath("swfs")
			+ "/";
	String originnaltype = "";// 原始类型
	String contributer = "";// 贡献者
	long size = 0; // 文件大小

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

	public String execute() {
		return "input";
	}

	public String upload() throws Exception {
		// File saved = new File(ServletActionContext.getServletContext()
		// .getRealPath("files"), fileFileName);
		if (getDescription() != null && getFileFileName() != null) {
			File saved = new File(officesDir, getFileFileName());
			InputStream ins = null;
			OutputStream ous = null;
			size = saved.length() / 1024;
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

			int fileIndex = fileFileName.indexOf(".");
			String trueFileName = fileFileName.substring(0, fileIndex);// 获得文件名，去除后缀

			String officePath = officesDir + fileFileName;
			String pdfPath = pdfsDir + trueFileName + ".pdf";
			String swfPath = swfsDir + trueFileName + ".swf";

			// fileContentType
			contributer = "游客";
			// size;
			// description

			// 文件转换测试
			Convert c = new Convert(officePath, pdfPath, swfPath);
			c.office2PDF();
			c.pdf2swf();

			// 文件信息保存到数据库officepath pdfpath swfpath originnaltype contributer
			// size
			// description
			Dao d = new Dao();
			
			int hit=1;
			int good=0;
			d.insertFile(officePath, pdfPath, swfPath, originnaltype,
					contributer, size, description,hit,type,good);

			// session设置swf文件到路径 让show.jsp页面展示
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			session.setAttribute("swfPath", "./swfs/" + trueFileName + ".swf");
			System.out.println(type);
			return "success";
		}
		else
		{
			return "error";
		}
	}

}
