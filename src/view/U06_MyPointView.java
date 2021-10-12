package view;



import java.util.Scanner;

import dao.Session;
import dao.UserDAO;
import dto.UserDTO;

public class U06_MyPointView {
//	포인트 사용
	public U06_MyPointView() {
		UserDTO udto = Session.get("session");
		Scanner sc= new Scanner(System.in);
		UserDAO check = new UserDAO();
		System.out.print("비밀번호를 입력하세요 : ");
		String pw_check = sc.next();
		if(pw_check.equals(udto.getMember_pw())){
			System.out.println("비밀번호가 일치하였습니다! \n---- 포인트 정보 ----");
			check.pointCheck(udto.getMember_id());			
		} else {
		System.out.println("비밀번호 오답입니다.");
		}
		
		
	}
}
