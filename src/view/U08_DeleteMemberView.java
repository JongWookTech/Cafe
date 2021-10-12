package view;

import java.util.Scanner;

import dao.Session;
import dao.UserDAO;
import dto.UserDTO;

public class U08_DeleteMemberView {
	private String UserDTO;

	public U08_DeleteMemberView() {
		Scanner sc = new Scanner(System.in);
		UserDAO udao = new UserDAO();
		UserDTO udto = Session.get("session");
		A00_Index index = new A00_Index();
		String member_id = udto.getMember_id();
		String member_pw = udto.getMember_pw();

		while (true) {
			System.out.println("회원 탈퇴 하시겠습니까?\n1. 예\n2. 아니오");
			int choice = sc.nextInt();
			if (choice == 1) {
				System.out.println("아이디와 비밀번호를 입력해주세요.\n아이디 : \n비밀번호 : ");
				String id = sc.next();
				String pw = sc.next();
				if (id.equals(member_id) && pw.equals(member_pw)) {
					if (udao.userDelete(member_id, member_pw)) {
						System.out.println("탈퇴를 완료 하였습니다.\n고객님, 이용해주셔서 감사합니다");
						new A00_Index();
						break;
					} else {
						System.out.println("탈퇴가 되지 않았습니다.");
					}
				}else {
					System.out.println("아이디, 비밀번호를  정확히 입력해주세요.");
					
				}

			} else if (choice == 2) {
				System.out.println("기존 창으로 돌아갑니다.");
				new U05_MyInfoView();

			}
			
		}
	}
}
