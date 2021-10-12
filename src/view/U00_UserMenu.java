package view;

import java.util.Scanner;

public class U00_UserMenu {

	public U00_UserMenu() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("1. 상품 주문\n2. 회원가입(여러 가지 혜택이 많아요!)");
			int choice = sc.nextInt();

			if (choice == 1) {
				// 상품주문
				break;
			} else if (choice == 2) {
//			회원가입
				new U01_JoinView();
				break;
			} else {
				System.out.println("다시 입력해주세요");
			}

		}
	}
}
