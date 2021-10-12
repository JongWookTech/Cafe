package view;

import java.util.Scanner;

import dao.UserDAO;

public class U07_PwFindView {
	public U07_PwFindView() {
		UserDAO udao = new UserDAO();
		Scanner sc = new Scanner(System.in);
		
		System.out.println("비밀번호를 찾을 아이디를 입력해주세요");
		String member_id = sc.next();
		udao.PwFind(member_id);
		
	}
}
