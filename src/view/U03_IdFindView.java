package view;

import java.util.Scanner;

import dao.UserDAO;

public class U03_IdFindView {
	public U03_IdFindView() {
		Scanner sc = new Scanner(System.in);
		UserDAO udao = new UserDAO();
		String phone_num = "";
		System.out.println("가입 시 입력한 핸드폰 번호를 입력하세요");
		while (true) {
			String phone_num_check = sc.next();
			String result = phone_num_check.substring(0, 3);
			if (!result.equals("010")) {
				System.out.println("제대로된 핸드폰 번호를 입력하세요");
			} else if (phone_num_check.length() < 11) {
				System.out.println("번호 형식이 너무 짧습니다.");
			} else if (phone_num_check.length() > 11) {
				System.out.println("번호 형식이 너무 깁니다.");
			} else if (phone_num_check.length() == 11) {
				phone_num = phone_num_check;
				udao.IdFind(phone_num);
				break;
			}
		}
		
	}
}
