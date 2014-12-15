package ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;



import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import dao.Dao;

public class BrowseFile {
	int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void out() {
		
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/html;charset=utf-8");
		PrintWriter out;
		try {
			out = res.getWriter();
			Dao d=new Dao();
			out.print(d.isConverted(id));
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void convertThenOut()
	{
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/html;charset=utf-8");
		PrintWriter out;
		
	
		
		try {
			out = res.getWriter();
			Dao d=new Dao();
			//调用dao的方法
			
			JSONObject json=new JSONObject();
			json.put("swfpath", d.savePath(id));
			out.print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
