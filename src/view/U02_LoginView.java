package view;

import java.util.Scanner;

import dao.Session;
import dao.UserDAO;
import dto.UserDTO;

public class U02_LoginView {
   public U02_LoginView() {
      UserDTO udto = null;
      Scanner sc = new Scanner(System.in);
      UserDAO udao = new UserDAO();
      System.out.print("아이디 : ");
      String member_id = sc.next();
      System.out.print("비밀번호 : ");
      String member_pw = sc.next();
      udto =udao.login(member_id,member_pw) ; 
      
      if (udao.AdminCheck(member_id,member_pw)) {
            
         System.out.println("관리자님 어서오세요");
         Session.put("session", udto);
         new A02_AdminView();
      }
      
      else if(udto!= null) {
         System.out.println(member_id+"님 어서오세요.");
//         세션에 유저 정보 전체 담아줌
         Session.put("session", udto);
         new A01_MainView();
      }else {
         System.out.println("로그인 실패 / 다시 시도해 주세요.");
      }
   }
}



