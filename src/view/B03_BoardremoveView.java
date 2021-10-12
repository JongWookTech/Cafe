package view;

import java.util.Scanner;

import dao.BoardDAO;
import dao.Session;
import dto.BoardDTO;
import dto.UserDTO;

public class B03_BoardremoveView {
   public B03_BoardremoveView() {
      Scanner sc = new Scanner(System.in);
      BoardDAO bdao= new BoardDAO();
      UserDTO udto = Session.get("session");
      System.out.println("내가 작성한 게시물");
      System.out.println(bdao.MygetBoard(udto.getMember_id()));
      System.out.print("삭제할 게시물의 번호를 입력해 주세요. : ");
      int choice = sc.nextInt();
      System.out.print("게시물의 비밀번호를 입력해 주세요.: ");
      String Pw = sc.next();
      if(bdao.remove(choice, Pw)) {
         System.out.println("삭제하시겠습니까? ");
         System.out.println("(Y|N)둘 중 입력해 주세요");
         String choice2 = "";
         choice2 = sc.next();
         if (choice2.equals("Y") || choice2.equals("y")) {
            bdao.remove(choice , Pw);
            System.out.println("삭제가 완료되었습니다.");
         } else {
            System.out.println("삭제가 취소되었습니다.");
            new B07_AdminBoardModifyView();
         }
      } else {
         System.out.println("비밀번호가 틀렸습니다.");
      }
         
      

   
   }
}