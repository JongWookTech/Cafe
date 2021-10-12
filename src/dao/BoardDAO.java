package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.BoardDTO;
import dto.UserDTO;

public class BoardDAO {
	Connection conn;
	PreparedStatement pstm;
	ResultSet rs;

	public BoardDAO() {
		// 미리 작성해놓은 DBConnection 클래스를 이용해 건설된 다리 받아오기
		conn = DBConnection.getConnection();
	}
	// 게시글 작성

	public boolean Create(BoardDTO newboard) {
		String sql = "INSERT INTO NOTICE VALUES(BOARD_SEQ.NEXTVAL,?,?,?,?)";
		int result = 0;
		try {
			// 받아온 다리 위를 왔다갔다할 전령 만들기, 전령이 수행할 쿼리문을 전달해준다.
			pstm = conn.prepareStatement(sql);
			// 가지고 있는 쿼리문의 1번째(첫번째) 물음표에 newUser.userid 값으로 대신 채워주기
			pstm.setString(1, newboard.member_id);
			pstm.setString(2, newboard.notice_pw);
			pstm.setString(3, newboard.notice_name);
			pstm.setString(4, newboard.contents);

			// pstm이 가지고 있는 쿼리문 수행
			// INSERT, UPDATE, DELETE : executeUpdate() - 수정된(추가된) 행의 개수 return (int)
			// SELECT : executeQuery() - 검색된 테이블을 return (ResultSet)
			result = pstm.executeUpdate();
		} catch (SQLException e) {
			System.out.println("게시판 작성 예외 : " + e);
		}

		return result == 1;
	}

	// 전체 글 보기
	public String getBoard() {
		String sql = "SELECT * FROM NOTICE ORDER BY NOTICE_NUM";
		String result = "";
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				int notice_num = rs.getInt(1);
				String member_id = rs.getString(2);
//				String notice_pw = rs.getString(3);
				String notice_name = rs.getString(4);
				String contents = rs.getString(5);
				result += String.format("[게시글 번호:%d][게시글 작성자:%s][게시글 제목:%s][게시글 내용:%s]\n", notice_num, member_id,
						notice_name, contents);
			}

		} catch (SQLException e) {
			System.out.println("게시판 오류 : " + e);

		}
		return result;
	}

	// 내가 쓴 글 보기
	public String MygetBoard() {
		String sql = "SELECT * FROM NOTICE WHERE MEMBER_ID=? ORDER BY NOTICE_NUM";
		String result = "";
		UserDTO udto = new UserDTO();
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, udto.getMember_id());
			rs = pstm.executeQuery();
			while (rs.next()) {
				int notice_num = rs.getInt(1);
				String member_id = rs.getString(2);
//			String notice_pw = rs.getString(3);
				String notice_name = rs.getString(4);
				String contents = rs.getString(5);
				result += String.format("[게시글 번호:%d][게시글 작성자:%s][게시글 제목:%s][게시글 내용:%s]\n", notice_num, member_id,
						notice_name, contents);
			}

		} catch (SQLException e) {
			System.out.println("게시판 오류 : " + e);

		}
		return result;
	}

//게시판 글 삭제
	public boolean remove(int removeNum, String Pw) {
		String sql = "DELETE FROM NOTICE WHERE NOTICE_NUM = ? AND NOTICE_PW=?";
		int result = 0;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, removeNum);
			pstm.setString(2, Pw);
			result = pstm.executeUpdate();
		} catch (SQLException e) {
//			e.printStackTrace();  //어디서 오류났는지 확인
			System.out.println("삭제 오류 :" + e);
		}
		return result == 1;

	}

	public String MygetBoard(String memberId) {
		String sql = "SELECT * FROM NOTICE WHERE MEMBER_ID=? ORDER BY NOTICE_NUM";
		String result = "";
		UserDTO udto = new UserDTO();
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, memberId);
			rs = pstm.executeQuery();
			while (rs.next()) {
				int notice_num = rs.getInt(1);
				String member_id = memberId;
//				String notice_pw = rs.getString(3);
				String notice_name = rs.getString(4);
				String contents = rs.getString(5);
				result += String.format("[게시글 번호:%d][게시글 작성자:%s][게시글 제목:%s][게시글 내용:%s]\n", notice_num, member_id,
						notice_name, contents);
			}

		} catch (SQLException e) {
			System.out.println("게시판 오류 : " + e);

		}
		return result;
	}

	public boolean updateBoard(int num, int choice, String newData) {
		// 1. 상품이름\n2. 상품가격\n3. 상품설명\n4. 상품재고\n5. 카테고리
		String[] columns = { "NOTICE_NAME", "CONTENTS" };
		String sql = "UPDATE NOTICE SET " + columns[choice - 1] + " = ? WHERE NOTICE_NUM = ?";
		int result = 0;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, newData);
			pstm.setInt(2, num);
			result = pstm.executeUpdate(); // 바뀐 행의 수를 반환해준다.
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result != 0;
	}

	public boolean boardCheck(int num) {
		String sql = "SELECT COUNT(*) FROM NOTICE WHERE NOTICE_NUM = ?";
		int result = 0;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, num);
			rs = pstm.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
		}
		return result == 1;
	}

	public ArrayList<BoardDTO> getBoardList(int num) {

		String sql = "SELECT * FROM PRODUCT WHERE NOTICE_NUM= ?";
		BoardDTO board = null;
		ArrayList<BoardDTO> boardList = new ArrayList<>();
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, num);
			rs = pstm.executeQuery();
			while (rs.next()) {
				board = new BoardDTO(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				boardList.add(board);
			}

		} catch (SQLException e) {
		}
		return boardList;
	}

	// 관리자 게시글 삭제 삭제 (비밀번호입력없이 그냥 삭제할수있게)
	public boolean remove_admin(int removeNum) {
		String sql = "DELETE FROM NOTICE WHERE NOTICE_NUM = ?";
		int result = 0;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, removeNum);
			result = pstm.executeUpdate();
		} catch (SQLException e) {
//				e.printStackTrace();  //어디서 오류났는지 확인
			System.out.println("삭제 오류 :" + e);
		}
		return result == 1;

	}

	// 관리자가 아이디 검색하면 해당 아이디가 작성한 게시물 일괄삭제 가능
	public boolean remove_adminID(String ID) {
		String sql = "DELETE FROM NOTICE WHERE MEMBER_ID = ?";
		int result = 0;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, ID);
			result = pstm.executeUpdate();
		} catch (SQLException e) {
//				e.printStackTrace();  //어디서 오류났는지 확인
			System.out.println("삭제 오류 :" + e);
		}
		return result == 1;
	}

	// 관리자가 아이디 검색하면 해당 아이디가 작성한 게시물중 선택해서 삭제
	public boolean remove_adminIDNum(String choiceID, int removeNum) {
		String sql = "DELETE FROM NOTICE WHERE MEMBER_ID = ? AND NOTICE_NUM = ? ";
		int result = 0;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, choiceID);
			pstm.setInt(2, removeNum);
			result = pstm.executeUpdate();
		} catch (SQLException e) {
//				e.printStackTrace();  //어디서 오류났는지 확인
			System.out.println("삭제 오류 :" + e);
		}
		return result == 1;
	}

	// 모든 게시글 보여주기
	public String all() {
		String sql = "SELECT * FROM NOTICE ";
		String result = "";
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				int notice_num = rs.getInt(1);
				String member_id = rs.getString(2);
				String notice_pw = rs.getString(3);
				String notice_name = rs.getString(4);
				String contents = rs.getString(5);
				result += String.format("[게시글 번호:%d][게시글 작성자:%s][게시글 비밀번호:%s][게시글 제목:%s][게시글 내용:%s]\n", notice_num,
						member_id, notice_pw, notice_name, contents);
			}
		} catch (SQLException e) {
//				e.printStackTrace();  //어디서 오류났는지 확인
			System.out.println("삭제 오류 :" + e);
		}
		return result;

	}
}
