package view;

import java.util.Scanner;

import dao.BoardDAO;

public class B09_BoardSearchIDdelete {
	public B09_BoardSearchIDdelete() {
		Scanner sc = new Scanner(System.in);
		BoardDAO bdao = new BoardDAO();
		System.out.print("검색할 아이디를 입력해주세요 :");
		String choiceID = sc.next();
		if(bdao.MygetBoard(choiceID).equals("")) {
			System.out.println(choiceID+"님이 작성한 게시글이 없습니다");
		}else {
			
			System.out.println("1.일괄 삭제 \n2.부분 삭제");
			int choice = sc.nextInt();
			switch (choice) {
			case 1: {
				System.out.println(choiceID + "님이 작성한 게시글");
				System.out.println(bdao.MygetBoard(choiceID));
				System.out.println(choiceID + "님이 작성한 모든 게시글을 삭제하시겠습니까?\nY/N");
				String YN = sc.next();
				if (YN.equals("Y") || YN.equals("y")) {
					bdao.remove_adminID(choiceID);
					System.out.println(choiceID + "님이 작성한 모든 게시물이 삭제 되었습니다.");
				} else {
					System.out.println("게시글들 삭제가 취소되었습니다.");
				}
			}
			
			case 2: {
				
				System.out.println(choiceID + "님이 작성한 글이 없습니다.");
				System.out.println(choiceID + "님이 작성한 게시글");
				System.out.println(bdao.MygetBoard(choiceID));
				System.out.print("삭제할 글의 번호 :");
				int removeNum = sc.nextInt();
				System.out.println(choiceID + "님이 작성한" + removeNum + "글을 삭제하시겠습니까?\nY/N");
				String YN = sc.next();
				if (YN.equals("Y") || YN.equals("y")) {
					bdao.remove_adminIDNum(choiceID, removeNum);
					System.out.println(removeNum + "글이 삭제되었습니다.");
				} else {
					System.out.println(removeNum + "글 삭제가 취소되었습니다.");
				}
				break;
				
			}
			
			}
			
		}
		
	}
		}

