package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import org.omg.CORBA.Request;

import dto.UserDTO;

public class UserDAO {
	Connection conn;
	PreparedStatement pstm;
	ResultSet rs;

	public UserDAO() {
		conn = DBConnection.getConnection();
	}

	public boolean join(UserDTO newUser) {

		String sql = "INSERT INTO TBL_MEMBER VALUES(?,?,?,?,?,?)";
		int result = 0;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, newUser.getMember_id());
			pstm.setString(2, newUser.getMember_pw());
			pstm.setString(3, newUser.getMember_name());
			pstm.setInt(4, newUser.getPoint());
			pstm.setString(5, newUser.getPhone_num());
			pstm.setString(6, newUser.getMember_type());

			result = pstm.executeUpdate();
		} catch (SQLException e) {
			System.out.println("join 예외 발생 : " + e);
			System.out.println("여기1");
		}
		return result == 1;

	}

//	로그인 할때 세션에 USERDTO 정보를 담아줍니다.
	public UserDTO login(String member_id, String member_pw) {
		String sql = "SELECT * FROM TBL_MEMBER WHERE MEMBER_ID=? AND MEMBER_PW=?";

		UserDTO user = null;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, member_id);
			pstm.setString(2, member_pw);

			rs = pstm.executeQuery();
			if (rs.next()) {
				user = new UserDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5),
						rs.getString(6));
				sql = "DELETE FROM TBL_CART";
				pstm = conn.prepareStatement(sql);
				pstm.executeUpdate();
			}

		} catch (SQLException e) {
			System.out.println("join 예외 발생 : " + e);
			System.out.println("여기2");
		}
		return user;
	}

	public boolean idCheckup(String member_id) {
		String sql = "SELECT COUNT(*) FROM TBL_MEMBER WHERE MEMBER_ID=?";
		int result = 1;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, member_id);

			// 검색된 결과 테이블을 ResultSet 타입으로 리턴
			rs = pstm.executeQuery();
			// rs.next() : 행을 하나 아래로 이동
			if (rs.next()) {
				result = rs.getInt(1);

			}

		} catch (SQLException e) {
			System.out.println("join 예외 발생 : " + e);
			System.out.println("여기3");
		}
		return result == 0;

	}

	public boolean nameCheckup(String member_name) {
		String sql = "SELECT COUNT(*) FROM TBL_MEMBER WHERE MEMBER_NAME=?";
		int result = 1;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, member_name);

			// 검색된 결과 테이블을 ResultSet 타입으로 리턴
			rs = pstm.executeQuery();
			// rs.next() : 행을 하나 아래로 이동
			if (rs.next()) {
				result = rs.getInt(1);

			}

		} catch (SQLException e) {
			System.out.println("join 예외 발생 : " + e);
			System.out.println("여기3");
		}
		return result == 0;

	}

//	수정해야함
//	포인트 체크 부분
	public int pointCheck(String member_id) {
		String sql = "SELECT POINT FROM TBL_MEMBER WHERE MEMBER_ID=?";

		int result = 0;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, member_id);

			// 검색된 결과 테이블을 ResultSet 타입으로 리턴
			rs = pstm.executeQuery();
			// rs.next() : 행을 하나 아래로 이동
			if (rs.next()) {
				result = rs.getInt("POINT");

			}

		} catch (SQLException e) {
			System.out.println("join 예외 발생 : " + e);
			System.out.println("여기3");
		}

		System.out.printf(member_id + "님의 포인트 현황 : %s포인트\n", result);
		return result;
	}

//	아이디 찾기
	   public String[] IdFind(String phone_num) {
		      String sql = "SELECT MEMBER_ID FROM TBL_MEMBER WHERE PHONE_NUM=?";
		      String[] result = new String[1000];
		      int i = 0;
		      try {
		         pstm = conn.prepareStatement(sql);
		         pstm.setString(1, phone_num);

		         // 검색된 결과 테이블을 ResultSet 타입으로 리턴
		         rs = pstm.executeQuery();
		         if (!rs.next()) {
		            System.out.println("가입하신 아이디가 존재하지 않습니다.");
		            return null;
		         }
		         // rs.next() : 행을 하나 아래로 이동
		         while (true) {
		            result[i] = rs.getString(1);
		            System.out.println((i + 1) + "번째 아이디 : " + result[i]);
		            i++;
		            if(!rs.next()) {
		               break;
		            }
		         }

		      } catch (SQLException e) {
		         System.out.println("join 예외 발생 : " + e);
		         System.out.println("여기3");
		      }
		      return result;
		   }

//	비밀번호 찾기
	public String PwFind(String member_id) {
		String sql = "SELECT MEMBER_PW FROM TBL_MEMBER WHERE MEMBER_ID=?";
		String result = "";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, member_id);

			// 검색된 결과 테이블을 ResultSet 타입으로 리턴
			rs = pstm.executeQuery();
			// rs.next() : 행을 하나 아래로 이동
			if (rs.next()) {
				result = rs.getString(1);

			} else if (!rs.next()) {
				System.out.println("가입하신 아이디가 존재하지 않습니다.");
				return null;
			}

		} catch (SQLException e) {
			System.out.println("join 예외 발생 : " + e);
			System.out.println("여기3");
		}
		System.out.println(member_id + "님의 비밀번호는 " + result + " 입니다.");
		return result;
	}

// 유저 삭제	 
	  public boolean userDelete(String member_id, String member_pw) {
	      String sql = "DELETE FROM TBL_MEMBER WHERE MEMBER_ID = ? AND MEMBER_PW = ?";
	      int result = 0;
	      try {
	         pstm = conn.prepareStatement(sql);

	         pstm.setString(1, member_id);
	         pstm.setString(2, member_pw);
	         result = pstm.executeUpdate();
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }
	      return result != 0;
	   }

	public boolean AdminCheck(String member_id, String member_pw) {

		String sql = "SELECT COUNT(*) FROM TBL_MEMBER WHERE MEMBER_TYPE='관리자' AND MEMBER_ID=? AND MEMBER_PW=?";
		int result = 0;

		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, member_id);
			pstm.setString(2, member_pw);

			// 검색된 결과 테이블을 ResultSet 타입으로 리턴
			rs = pstm.executeQuery();
			// rs.next() : 행을 하나 아래로 이동
			if (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			System.out.println("join 예외 발생1 : " + e);
		}
		return result == 1;
	}

//  세션에 정보를 다시 담아줄 구문
	public UserDTO reSession(String changeInfo) {
		String sql = "SELECT * FROM TBL_MEMBER WHERE MEMBER_ID=?";

		UserDTO user = null;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, changeInfo);

			rs = pstm.executeQuery();
			if (rs.next()) {
				user = new UserDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5),
						rs.getString(6));

			}

		} catch (SQLException e) {
			System.out.println("join 예외 발생 : " + e);
			System.out.println("여기2");
		}
		return user;

	}

//  아이디변경
//  UPDATE [테이블] SET [열] = '변경할값' WHERE [조건]

	public boolean idChange(String change_id, String member_id) {

		String sql = "UPDATE TBL_MEMBER SET MEMBER_ID =? WHERE MEMBER_ID =? ";
		int result = 0;
		try {
			pstm = conn.prepareStatement(sql);
//        어떤 정보를 바꿀것인가.
			pstm.setString(1, change_id);
//        어떤 값이었던걸 바꿀것인가.
			pstm.setString(2, member_id);

			result = pstm.executeUpdate();
//        세션에 다시 담아줄 구문

		} catch (SQLException e) {
			System.out.println("join 예외 발생 : " + e);
			System.out.println("여기1");
		}
		return result == 1;

	}

//  비밀번호 변경
	public boolean pwChange(String change_pw, String member_pw, String member_id) {

		String sql = "UPDATE TBL_MEMBER SET MEMBER_PW =? WHERE MEMBER_PW =? AND MEMBER_ID =? ";
		int result = 0;
		try {
			pstm = conn.prepareStatement(sql);
//        어떤 정보를 바꿀것인가.
			pstm.setString(1, change_pw);
//        어떤 값이었던걸 바꿀것인가.
			pstm.setString(2, member_pw);
			pstm.setString(3, member_id);

			result = pstm.executeUpdate();
//        세션에 다시 담아줄 구문

		} catch (SQLException e) {
			System.out.println("join 예외 발생 : " + e);
			System.out.println("여기1");
		}
		return result == 1;

	}

//  닉네임 변경
	public boolean nameChange(String change_name, String member_name, String member_id) {

		String sql = "UPDATE TBL_MEMBER SET MEMBER_NAME =? WHERE MEMBER_NAME =? AND MEMBER_ID =?";
		int result = 0;
		try {
			pstm = conn.prepareStatement(sql);
//        어떤 정보를 바꿀것인가.
			pstm.setString(1, change_name);
//        어떤 값이었던걸 바꿀것인가.
			pstm.setString(2, member_name);
			pstm.setString(3, member_id);

			result = pstm.executeUpdate();
//        세션에 유저정보를 다시 담아준다.

		} catch (SQLException e) {
			System.out.println("join 예외 발생 : " + e);
			System.out.println("여기1");
		}
		return result == 1;

	}

	public boolean sumPoint(int point, String member_name) {

		String sql = "UPDATE TBL_MEMBER SET POINT =? WHERE MEMBER_ID =?";
		int result = 0;
		try {
			pstm = conn.prepareStatement(sql);
//	         
			pstm.setInt(1, point);
			pstm.setString(2, member_name);

			result = pstm.executeUpdate();

		} catch (SQLException e) {
			System.out.println("join 예외 발생 : " + e);
			System.out.println("여기1");
		}
		return result == 1;

	}

	public String contactAdmin() {
		String sql = "SELECT PHONE_NUM FROM TBL_MEMBER WHERE MEMBER_TYPE='관리자'";
		String result = "";
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			if (rs.next()) {
				result = rs.getString(1);
			}
		} catch (SQLException e) {
			System.out.println("join 예외 발생1 : " + e);
		}
		return result;
	}

	public boolean updatePoint(int updatePoint, String member_id) {
		String sql = "UPDATE TBL_MEMBER SET POINT=? WHERE MEMBER_ID=?";
		int result = 0;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, updatePoint);
			pstm.setString(2, member_id);
			result = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("주문 후 보유포인트 수정 오류" + e);
		}
		return result != 0;
	}

}