package view;

import java.util.Scanner;

import dao.Session;
import dao.UserDAO;
import dto.UserDTO;

public class U11_PointAddView {

	public U11_PointAddView() {
		Scanner sc = new Scanner(System.in);
		UserDAO udao = new UserDAO();
		UserDTO udto = Session.get("session");
		int point = udto.getPoint();
		String member_id = udto.getMember_id();
		int sum_point = 0;
		int choice = 0;

		while (true) {
			System.out.println("충전하실 금액을 입력해주세요");
			sum_point = sc.nextInt();
			System.out.println("충전 금액 : " + sum_point + " 원 맞으십니까?");

			System.out.println("1. 예 \n2. 아니오 \n3. 나가기");
			choice = sc.nextInt();
			if (choice == 1) {
//			예를 눌렀을때
//				기존의 포인트와 충전할 포인트를 합친다.
				sum_point += point;
				if(udao.sumPoint(sum_point, member_id)) {
//					포인트 충전에 성공했을 때
					System.out.println("포인트 충전에 성공했습니다.");
					
//					세션에 값을 다시 넣어주는 과정
					udto = udao.reSession(member_id);
					Session.put("session", udto);
					
					System.out.println("현재 포인트 : " + sum_point + "원 있습니다.");
					break;
				}else {
//					포인트 충전에 실패했을 때
					System.out.println("포인트 충전 실패입니다. 다시 시도해주세요");
					break;
				}
					
			} else if (choice == 2) {
//			아니오
				continue;
			} else if (choice == 3) {
//			나가기
				System.out.println("뒤로 갑니다");
				break;
			}

		}

	}
}
