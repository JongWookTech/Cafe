package view;

import dao.BoardDAO;

public class B05_GetBoardAllView {

	public B05_GetBoardAllView() {
		BoardDAO bdao= new BoardDAO();
		if(bdao.all().equals("")) {
			System.out.println("게시판에 글이 없습니다.");
		}else {
			System.out.println(bdao.all());
		}
	}

}
