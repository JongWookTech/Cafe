package view;

import java.util.Scanner;
import java.util.regex.Pattern;

import dao.Session;
import dao.UserDAO;
import dto.UserDTO;

public class U04_EditMyinfoView {
	public U04_EditMyinfoView() {
		Scanner sc = new Scanner(System.in);
		int choice = 0;
		UserDAO udao = new UserDAO();
		UserDTO udto = Session.get("session");
		while (true) {
			System.out.println("1. 아이디 변경\n2.비밀번호 변경\n3.닉네임 변경\n4.뒤로 가기");
			choice = sc.nextInt();
			if (choice == 4) {
				System.out.println("뒤로 갑니다.");
				break;
			}
			switch (choice) {
			case 1:
				do {
					String member_id = "";
					String member_pw = "";
					String changeInfo = "";
//				아이디 변경
					System.out.println("정보확인을 위해 비밀번호를 입력하세요.");
					member_pw = sc.next();
//				비밀번호가 맞다면 아이디 변경을 수행한다.
					if (member_pw.equals(udto.getMember_pw())) {
						System.out.println("변경하실 아이디 :");
						String change_id = sc.next();
						if (udao.idCheckup(change_id)) {
//				쿼리문에 필요한 값을 udto로 넣어준다.
							member_id = udto.getMember_id();
							changeInfo = change_id;
//					아이디 변경이 성공했다면
							if (udao.idChange(change_id, member_id)) {
								udto = udao.reSession(changeInfo);
//					세션에 값을 다시 넣어준다.
								Session.put("session", udto);
								System.out.println(udto.getMember_id() + "으로 아이디가 변경되었습니다!");
								break;
							} else {
//					아이디 변경이 실패했다면
								System.out.println("오류입니다. 다시 시도해주세요.");
								break;
							}
						} else {
							System.out.println("이미 존재하는 아이디입니다.");
							break;
						}
					} else {
						System.out.println("비밀번호가 맞지 않습니다.");
						break;
					}

				} while (false);
				break;

			case 2:
//				비밀번호 변경
				do {
					String member_pw = "";
					String change_pw = "";
					String changeInfo = "";
					String pw_check = "";
					String member_id = udto.getMember_id();
					System.out.println("정보확인을 위해 비밀번호를 입력하세요.");
					member_pw = sc.next();
//				기존 비밀번호가 맞다면
					if (member_pw.equals(udto.getMember_pw())) {

//					비밀번호가 조건에 맞는지 확인
						while (true) {
							System.out.println("변경하실 비밀번호를 입력해주세요");
							String change_pw_check = sc.next();
//							정규식
							String thePattern = "[^A-Za-z0-9]+";
							if (change_pw_check.length() < 8) {
								System.out.println("8자 이상으로 적어주세요");

//						특수문자를 통과하지 못했다면
							} else if (!Pattern.compile(thePattern).matcher(change_pw_check).find()) {
								System.out.println("특수문자를 하나는 넣어주세요");

							} else {
								change_pw = change_pw_check;
								break;
							}

						}
						changeInfo = udto.getMember_id();
//					비밀번호 변경에 성공했다면
						if (udao.pwChange(change_pw, member_pw, member_id)) {
							udto = udao.reSession(changeInfo);
//						세션을 다시 넣어줌
							Session.put("session", udto);
							System.out.println("비밀번호가 " + udto.getMember_pw() + "로 바뀌었습니다.");
							break;
						} else {
							System.out.println("오류 입니다. 다시 시도해주세요");
							break;
						}

					} else {
						System.out.println("비밀번호가 맞지 않습니다.");
						break;
					}
				} while (false);
				break;
			case 3:
				do {
					String change_name = "";
					String member_name = udto.getMember_name();
					String member_pw = "";
					String member_id = udto.getMember_id();

					System.out.print("정보확인을 위해 비밀번호를 입력하세요.\n");
					member_pw = sc.next();
//				비밀번호가 맞다면
					if (member_pw.equals(udto.getMember_pw())) {
						System.out.println("변경하실 닉네임을 적어주세요");
						change_name = sc.next();
//					중복되는 닉네임이 없다면
						if (udao.nameCheckup(change_name)) {
//						닉네임 변경에 성공했다면
							if (udao.nameChange(change_name, member_name, member_id)) {
								udto = udao.reSession(member_id);
//							세션에 다시 값을 넣어준다.
								Session.put("session", udto);
								System.out.println("닉네임이 " + udto.getMember_name() + "으로 바뀌었습니다.");
							}
						} else {
							System.out.print("닉네임이 중복됩니다.\n");
							break;
						}
					} else {
						System.out.print("비밀번호가 맞지 않습니다.\n");
						break;
					}
				} while (false);

				break;

			}

		}

	}
}
