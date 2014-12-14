package ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

import dao.Dao;

public class IndexList extends ActionSupport {

	public void out() {

		Dao d = new Dao();

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		
		try {
			PrintWriter out = response.getWriter();
			out.print(d.getIndexList());
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

}
