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
private String signal;

	public String getSignal() {
	return signal;
}

public void setSignal(String signal) {
	this.signal = signal;
}

	public void out() {

		Dao d = new Dao();

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		
		try {
			PrintWriter out = response.getWriter();
			out.print(d.getIndexList(getSignal()));//传递signal参数给数据库 控制数据库拉取到数据
			System.out.println(getSignal());
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

}
