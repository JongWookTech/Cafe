package view;

import java.util.Scanner;
import java.util.regex.Pattern;

import dao.UserDAO;
import dto.UserDTO;

public class U01_JoinView {

//	관리자로 가입시 필요한 코드넘버 
	int cafe_num = 1001;

	public U01_JoinView() {

		UserDAO udao = new UserDAO();
		Scanner sc = new Scanner(System.in);
//		아이디
		System.out.println("[====회원가입====]\n");
		System.out.print("사용하실 아이디를 입력해주세요 : ");
		String member_id = sc.next();
		if (udao.idCheckup(member_id)) {

			System.out.println("사용 가능한 아이디입니다.\n");
//			비밀번호
			String member_pw = "";
			String thePattern = "[^A-Za-z0-9]+";
			while (true) {
				System.out.print("(8자 이상 / 특수문자 1개 이상 기입)\n비밀번호를 입력해주세요 : ");
				String member_pw_check = sc.next();
				if (member_pw_check.length() < 8) {
					System.out.println("비밀번호는 8자 이상으로 적어주세요!");
				} else if (!Pattern.compile(thePattern).matcher(member_pw_check).find()) {
					System.out.println("특수문자를 1개 이상 넣으세요");
				} else {
					member_pw = member_pw_check;
					System.out.println();
					break;
				}

			}

//			닉네임
			System.out.print("사용하실 닉네임을 입력해주세요 : ");
			String member_name = sc.next();
			if (udao.nameCheckup(member_name)) {
				System.out.println("사용 가능한 닉네임 입니다.\n");
				
//				포인트는 가입시 0으로 고정 설정해놓는다.
				int point = 0;

//			핸드폰 번호
				String phone_num = "";
				while (true) {
					System.out.print("본인의 핸드폰 번호를 입력해주세요 : ");
					String phone_num_check = sc.next();
					String result = phone_num_check.substring(0, 3);
					if (!result.equals("010")) {
						System.out.println("제대로된 핸드폰 번호를 입력하세요");
					} else if (phone_num_check.length() < 11) {
						System.out.println("번호 형식이 너무 짧습니다.");
					} else if (phone_num_check.length() > 11) {
						System.out.println("번호 형식이 너무 깁습니다.");
					} else if (phone_num_check.length() == 11) {
						phone_num = phone_num_check;
						System.out.println();
						break;
					}
				}

//			회원타입 작성
				String member_type = "";
				while (true) {
					System.out.print("회원타입을 입력해주세요.\n1. 관리자 2.회원 : ");
					sc = new Scanner(System.in);
					int member_type_check = sc.nextInt();
					if (member_type_check == 1) {
						System.out.println("매장 코드를 입력해주세요 : ");
						int code = sc.nextInt();
						if (code == 1001) {
							System.out.println("일치하였습니다.");
							member_type = "관리자";
							break;
						} else {
							System.out.println("매장 코드가 틀렸습니다.");
						}
					} else if(member_type_check == 2){
						member_type = "회원";
						break;
					} else {
						System.out.println("선택 항목을 다시 선택하여 주십시오.");
					}
				}

				UserDTO newUser = new UserDTO(member_id, member_pw, member_name, point, phone_num, member_type);

				if (udao.join(newUser)) {
					if (member_type.equals("관리자")) {
						System.out.println("저희 무인카페의 관리자가 되신걸 환영합니다!" + member_id + "님!");

					} else if (member_type.equals("회원")) {
						System.out.println("저희 무인카페의 회원이 되신걸 환영합니다!." + member_id + "님!");
					}
				} else {
					System.out.println("회원가입 실패 / 다시 시도해 주세요.");
					System.out.println();
				}
			} else {
				System.out.println("이미 사용중인 닉네임 입니다.");
				System.out.println();
			}

		} else {
			System.out.println("이미 사용중인 아이디입니다.");
			System.out.println();
		}

	}
}
