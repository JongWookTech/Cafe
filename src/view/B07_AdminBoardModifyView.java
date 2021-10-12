package view;

import java.util.ArrayList;
import java.util.Scanner;

import dao.BoardDAO;
import dao.ProductDAO;
import dao.Session;
import dao.UserDAO;
import dto.BoardDTO;
import dto.ProductDTO;
import dto.UserDTO;

public class B07_AdminBoardModifyView {
	public B07_AdminBoardModifyView() {
		BoardDAO bdao = new BoardDAO();
		UserDTO udto = new UserDTO();
		Scanner sc = new Scanner(System.in);
		System.out.println("1. 게시글 수정하기\n2. 게시글 삭제하기\n3. 이전으로 가기");
		int choice = sc.nextInt();
		if (choice == 1) {
			// 카테고리 수정
			System.out.println(bdao.getBoard());
			System.out.print("수정하실 게시글 번호 : ");
			sc = new Scanner(System.in);
			int num = sc.nextInt();
			if (bdao.boardCheck(num)) {
				ArrayList<BoardDTO> boardList = bdao.getBoardList(num);
				for (int i = 0; i < boardList.size(); i++) {
					System.out.print(boardList.get(i).getNoticename() + " ");
					System.out.print("/ 내용" + boardList.get(i).getNoticeContents());
				}

				System.out.println("무엇을 수정 하시겠습니까? \n1. 게시글 이름\n2. 게시글 내용 ");
				choice = sc.nextInt();
				System.out.print("새롭게 수정할 데이터 : ");
				sc = new Scanner(System.in);
				String newData = sc.nextLine();
				if (bdao.updateBoard(num, choice, newData)) {
					System.out.println(num + "번 게시글 수정이 완료되었습니다.");
					new B07_AdminBoardModifyView();
				} else {
					System.out.println("수정을 실패하였습니다. / 다시 시도해주세요");

				}

			}

		}
		if (choice == 2) {
//			new B05_GetBoardView();

//			udto = Session.get("session");

			System.out.println("1. 번호로 삭제\n2. 아이디 검색\n3. 이전으로 가기");
			int choice2 = sc.nextInt();
			switch (choice2) {
			case 1: {
				System.out.println("=============게시판의 모든글=============");
				System.out.println(bdao.all());
				 System.out.println("삭제할 게시물의 번호를 선택해 주세요.");
		            int choice3 = sc.nextInt();
		            System.out.println("삭제하시겠습니까? ");
		            System.out.println("(Y|N)둘 중 입력해 주세요");
		            String choice4 = "";
		            choice4 = sc.next();
		            if (choice4.equals("Y") || choice4.equals("y")) {
		               bdao.remove_admin(choice3);
		               System.out.println("삭제가 완료되었습니다.");
		               break;
		            } else {
		               System.out.println("삭제가 취소되었습니다.");
		               new B07_AdminBoardModifyView();
		               break;
		            }
		         }
		         case 2: {
		            //System.out.println("검색할 아이디를 입력하세요 : ");
		            //sc = new Scanner(System.in);
		            new B09_BoardSearchIDdelete();
		            break;
		         }
		         case 3: {
		        	 new B07_AdminBoardModifyView();
		         }
		         default :
		            System.out.println("알 수 없는 오류 관리자에게 문의");
		         }
		      }
		      if (choice == 3) {
		         new B06_AdminBoardView();
		      }
		   }
		}