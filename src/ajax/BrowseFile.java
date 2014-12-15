package ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

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
		System.out.println(id);
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/html;charset=utf-8");
		PrintWriter out;
		try {
			out = res.getWriter();
			Dao d=new Dao();
			out.print(d.isConverted(id));
			System.out.println(d.isConverted(id));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
