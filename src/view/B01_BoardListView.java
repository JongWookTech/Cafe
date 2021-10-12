package view;

import java.util.Scanner;

import dao.BoardDAO;

public class B01_BoardListView {
	public B01_BoardListView(){
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("1.게시글 보기 \n2.게시글 작성 \n3.게시글 삭제\n4.이전으로 가기");
			int choice = sc.nextInt();
			if (choice == 4) {
				System.out.println("안녕히 가세요");
				break;
			}

			switch (choice) {
			case 1:// 게시글 보기
				new B04_GetBoardView();
				break;
			case 2:// 게시글 작성
				new B02_BoardCreatView();
				break;
			case 3:
				//게시글 삭제
				new B03_BoardremoveView();
				break;
				
				

			}
		}
	}

}