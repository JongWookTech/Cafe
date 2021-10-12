package view;

import java.util.Scanner;

import dao.Session;
import dao.UserDAO;
import dto.UserDTO;

public class U05_MyInfoView {
	public U05_MyInfoView() {
		A00_Index index = new A00_Index();
		UserDTO user = Session.get("session");
		UserDAO udao = new UserDAO();
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("[마이 페이지(My information]\n1. 내 포인트\n2. 포인트에 돈 넣기\n3. 내 정보 수정\n4. 회원탈퇴\n5. 내 장바구니\n6. [Main창 가기]");
			int choice = sc.nextInt();
			if (choice == 6) {
				new A01_MainView();
			}
			switch (choice) {
			case 1:
				// 내 포인트 확인
				new U06_MyPointView();
				continue;
			case 2:
//			포인트 충전
				new U11_PointAddView();
				continue;
			case 3:
				// 내 정보수정
				new U04_EditMyinfoView();
				continue;
			case 4:
				// 회원 탈퇴
				new U08_DeleteMemberView();
				if (Session.get("session") == null) {
					break;
				}
				continue;
			case 5:
				//내 장바구니
				new U12_MyCart();
				break;
			}
			break;
		}
	}
}