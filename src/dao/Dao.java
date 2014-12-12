package dao;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Calendar;


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

	
	public void insertFile(String officepath,String pdfpath,String swfpath,String originnaltype,String contributer,long size,String description,int hit,String type,int good)
	{
		Connection conn = getConn();
		String sql="insert into file (officepath,pdfpath,swfpath,originnaltype,contributer,size,description,hit,type,good) values(?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, officepath);
			pstmt.setString(2, pdfpath);
			pstmt.setString(3, swfpath);
			pstmt.setString(4, originnaltype);
			pstmt.setString(5, contributer);
			pstmt.setLong(6, size);
			pstmt.setString(7, description);
			pstmt.setInt(8, hit);
			pstmt.setString(9, type);
			pstmt.setInt(10, good);
			
			pstmt.execute();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public void query(String sql) {
//		Connection conn = getConn();
//		ResultSet rs = null;
//		try {
//			PreparedStatement pstmt = (PreparedStatement) conn
//					.prepareStatement(sql);
//			rs = pstmt.executeQuery();
//			ResultSetMetaData metadata = rs.getMetaData();
//			// 通过metadata 自动生成while循环中获取的内容
//
//			int columcount = metadata.getColumnCount();
//			String type[] = new String[columcount];
//			for (int i = 0; i < columcount; i++) {
//
//			}
//
//			while (rs.next()) {
//
//			}
//			conn.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

	public static void main(String[] args) {
		// Dao d=new Dao();
		// d.query("select * from file");


	}
}
