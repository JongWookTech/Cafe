package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	static Connection conn;
	//다리를 짓기위한 설계도
	static String driver = "oracle.jdbc.driver.OracleDriver";
	//다리를 지을 목표지점
	static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	
	//오라클 계정에 로그인하기 위한 정보들
	static String user = "web";
	static String password = "web";
	
	
	public static Connection getConnection() {
		if(conn == null) {
			try {
				//드라이버 로딩 : 설계도 가지고 오기
				Class.forName(driver);
				//DriverManager : 다리 건설공
				conn = DriverManager.getConnection(url, user, password);
			} catch (ClassNotFoundException cnfe) {
				System.out.println("jdbc driver 로딩 실패!");
			} catch (SQLException sqle) {
				System.out.println("오라클 연결 실패!");
			}
		}
		return conn;
	}
}


