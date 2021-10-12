package view;

import java.util.Scanner;

public class A00_Index {
	
	public static void main(String[] args) {
		System.out.println("6조 무인카페");
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("1. 회원가입\n2. 로그인\n3. 비회원\n4. 아이디 찾기\n5. 비밀번호 찾기\n6. 나가기");
			int choice = sc.nextInt();
			
			//Controller
			if(choice == 6) {
				System.out.println("안녕히가세요");
				break;
			}
			switch(choice) {
			case 1:
				//회원가입
				new U01_JoinView();
				break;
			case 2:
				//로그인
				new U02_LoginView();
				break;
				//비회원 로그인
			case 3:
				new A03_NonMainView();
				continue;
				//아이디 찾기
			case 4:
				new U03_IdFindView();
				break;
				//비밀번호 찾기
			case 5:
				new U07_PwFindView();
				break;
			}
		}
	}
}
