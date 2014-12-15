package dao;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONObject;

import util.Convert;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class Dao {
	public Connection getConn() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/doc", "root", "");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public String savePath(int id)//转换文件时，并且保存路径到数据库
	{
		Connection conn = getConn();
		String sql1="select officepath from file where id="+id;
		String sql2="update file set pdfpath=?,swfpath=? where id=?";
	
		final String PDFDIR="/pdfs/";
		final String SWFDIR=ServletActionContext.getServletContext() .getRealPath("swfs") + "/";
		
	
		String officepath=null; // office文件完整路径
		String pdfpath=null;
		String swfpath=null;
		String filename=null;//office文件的文件名
		String suffix=null;
		try {
			ResultSet rs = conn.createStatement().executeQuery(sql1);
			while(rs.next())
			{
				officepath=rs.getString(1);
			}
			rs.close();
			
			int index=officepath.lastIndexOf("/");
			int indexEnd=officepath.lastIndexOf(".");
			filename=officepath.substring(index+1,indexEnd);
			
			pdfpath=PDFDIR+filename+".pdf";
			swfpath=SWFDIR+filename+".swf";
			//转换
			Convert c=new Convert(officepath,pdfpath,swfpath);
			c.office2PDF();
			c.pdf2swf();
			
			java.sql.PreparedStatement pstmt = conn.prepareStatement(sql2);
			pstmt.setString(1, pdfpath);
			pstmt.setString(2, swfpath);
			pstmt.setInt(3, id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return swfpath;
	}
	
	public String isConverted(int id) {

		String sql = "select swfpath from file where id=" + id;
		Connection conn = getConn();
		Statement stmt;
		ResultSet rs;
		JSONObject json = new JSONObject();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

				json.put("swfpath", "null");
				while (rs.next()) {
					String swfpath = rs.getString(1);				
					json.put("swfpath",swfpath);
					
				}
				stmt.close();
				rs.close();
				conn.close();
			
		} catch (SQLException e) {
		}

		return json.toString();
	}

	public boolean officeUpload(String officepath, String originnaltype,
			int size, String description, String type) // office文件上传后 修改file表
	{
		boolean isFail = true;
		Connection conn = getConn();
		String sql = "insert into file (officepath,originnaltype,size,description,type) values (?,?,?,?,?)";
		try {
			java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, officepath);
			stmt.setString(2, originnaltype);
			stmt.setInt(3, size);
			stmt.setString(4, description);
			stmt.setString(5, type);
			isFail = stmt.execute();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isFail;
	}

	public String getIndexList(String signal) {
		Connection conn = getConn();
		String sql = "select officepath,originnaltype,good,id from file";// 选择热门文档

		String choose = "";// 会有多条sql语句，根据signal选择一条给choose
		// 等待获取到数据
		// IndexListData
		JSONObject json = new JSONObject();
		JSONArray arr = new JSONArray();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData meta = rs.getMetaData();

			while (rs.next()) {
				String officepath = rs.getString(1);
				String type = rs.getString(2);
				int good = rs.getInt(3);
				int id = rs.getInt(4);
				int indexStart = officepath.lastIndexOf("/");
				int indexEnd = officepath.lastIndexOf("_");

				String filename = officepath
						.substring(indexStart + 1, indexEnd);// 截取文件名
				JSONObject temp = new JSONObject();
				temp.put("filename", filename);
				temp.put("type", type);
				temp.put("good", good);
				temp.put("id", id);
				arr.put(temp);
			}
			json.put("data", arr);
			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return json.toString();
	}

	/*
	 * public void insertFile(String officepath,String pdfpath,String
	 * swfpath,String originnaltype,String contributer,long size,String
	 * description,int hit,String type,int good) { Connection conn = getConn();
	 * Stringsql=
	 * "insert into file (officepath,pdfpath,swfpath,originnaltype,contributer,size,description,hit,type,good) values(?,?,?,?,?,?,?,?,?,?)"
	 * ; try { PreparedStatement pstmt = (PreparedStatement)
	 * conn.prepareStatement(sql); pstmt.setString(1, officepath);
	 * pstmt.setString(2, pdfpath); pstmt.setString(3, swfpath);
	 * pstmt.setString(4, originnaltype); pstmt.setString(5, contributer);
	 * pstmt.setLong(6, size); pstmt.setString(7, description); pstmt.setInt(8,
	 * hit); pstmt.setString(9, type); pstmt.setInt(10, good);
	 * 
	 * pstmt.execute(); conn.close();
	 * 
	 * } catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } } // public void query(String sql) { // Connection
	 * conn = getConn(); // ResultSet rs = null; // try { // PreparedStatement
	 * pstmt = (PreparedStatement) conn // .prepareStatement(sql); // rs =
	 * pstmt.executeQuery(); // ResultSetMetaData metadata = rs.getMetaData();
	 * // // 通过metadata 自动生成while循环中获取的内容 // // int columcount =
	 * metadata.getColumnCount(); // String type[] = new String[columcount]; //
	 * for (int i = 0; i < columcount; i++) { // // } // // while (rs.next()) {
	 * // // } // conn.close(); // } catch (SQLException e) { //
	 * e.printStackTrace(); // } // }
	 */

	public static void main(String[] args) {
		// Dao d=new Dao();
		// d.getIndexList();
	}
}
