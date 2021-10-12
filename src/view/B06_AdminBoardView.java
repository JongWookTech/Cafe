package view;

import java.util.Scanner;

import dao.BoardDAO;
import dao.Session;
import dto.UserDTO;

public class B06_AdminBoardView {
	public B06_AdminBoardView() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("1.게시글 보기 \n2.게시글 작성 \n3.게시글 관리\n4.이전으로 가기");
			int choice1 = sc.nextInt();
			if (choice1 == 4) {
				System.out.println("안녕히 가세요");
				break;
			}

			switch (choice1) {
			case 1:// 게시글 보기
				new B04_GetBoardView();
				break;
			case 2:// 게시글 작성
				new B02_BoardCreatView();
				break;
			case 3:
				// 게시글 관리
				new B07_AdminBoardModifyView();
				break;
			}
		}
	}
}