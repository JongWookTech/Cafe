package view;

import java.util.Scanner;

import dao.BoardDAO;
import dao.Session;
import dto.UserDTO;

public class B04_GetBoardView {
	public B04_GetBoardView() {
		BoardDAO bdao = new BoardDAO();
		Scanner sc = new Scanner(System.in);
		UserDTO udto = Session.get("session");
		while (true) {
			System.out.println("1.전체 글 보기 \n2.내가 쓴글 보기\n3.이전으로 가기");
			int choice = sc.nextInt();

			if (choice == 3) {
				System.out.println("안녕히 가세요");
				break;
			}

			switch (choice) {
			case 1:// 전체 글 보기
				new B05_GetBoardAllView();
				break;
			case 2:// 내가 쓴 글 보기
				new B08_MygetBoardView(udto.getMember_id());
				break;

			}
		}
	}
}
