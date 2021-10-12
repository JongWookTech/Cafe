package view;

import java.util.Scanner;

import dao.BoardDAO;
import dao.Session;
import dto.BoardDTO;
import dto.UserDTO;

public class B02_BoardCreatView {
	public B02_BoardCreatView() {
		Scanner sc = new Scanner(System.in);
		BoardDAO bdao= new BoardDAO();
		UserDTO udto = Session.get("session");
		System.out.print("게시글  제목 : ");
		String notice_name = sc.nextLine();
		sc = new Scanner(System.in);
		System.out.print("비밀번호 : ");
		String notice_pw = sc.nextLine();
		sc = new Scanner(System.in);
		System.out.print("내용: ");
		String contents=sc.nextLine();
		sc = new Scanner(System.in);
		BoardDTO newboard = new BoardDTO(udto.getMember_id(), notice_pw, notice_name, contents);
		if(bdao.Create(newboard)) {
			System.out.println("게시글 작성 성공");
		}else {
			System.out.println("알수 없는 오류 다음에 다시 시도해주세요");
		}
		
	}
}
