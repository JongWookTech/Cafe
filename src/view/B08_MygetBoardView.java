package view;

import dao.BoardDAO;
import dao.Session;
import dto.BoardDTO;

public class B08_MygetBoardView {

	public B08_MygetBoardView(String member_id) {
		BoardDAO bdao= new BoardDAO();
		if(bdao.MygetBoard(member_id).equals("")) {
			System.out.println("내가 쓴 글이 없습니다.");
		}else {
			System.out.println(bdao.MygetBoard(member_id));
		}
		
	}

}
