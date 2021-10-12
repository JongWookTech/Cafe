package view;

import java.util.InputMismatchException;
import java.util.Scanner;

import dao.Session;

public class A01_MainView {
	Scanner sc = new Scanner(System.in);
	boolean flag = false;

	public A01_MainView() {
		while (true) {
			if (Session.get("session") == null) {
				System.out.println("로그인 후 이용해주세요");
				new U02_LoginView();
				break;
			} else {
				System.out.println("[Main 창입니다]");
				System.out.println("1. 주문하기\n2. 게시판 가기\n3. 내정보\n4. 로그아웃");
				int choice = sc.nextInt();
				if (choice == 4) {
					System.out.println("로그아웃 합니다");
					Session.put("session", null);
					break;

				}
				switch (choice) {
				// ??하기
				case 1:
					// 주문하기
					new O00_OrderView();
					break;
				case 2:
					// 게시판 가기
					new B01_BoardListView();
					break;
				case 3:
					// 내정보
					new U05_MyInfoView();
					if (Session.get("session") == null) {
						flag = true;
						break;

					}
					continue;
				}
				
				/*
				 * if (Session.get("session_id")==null) { break; }
				 */
			}
		}
	}
}
