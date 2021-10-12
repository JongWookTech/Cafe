package view;

import java.util.ArrayList;
import java.util.Scanner;

import dao.ProductDAO;
import dao.Session;
import dto.ProductDTO;

public class A02_AdminView {
	public A02_AdminView() {
		ProductDAO pdao = new ProductDAO();
		Scanner sc = new Scanner(System.in);
		while (true) {
			// 메인 화면
			if (Session.get("session").getMember_type().equals("회원")) {
				break;
			}
			System.out.println("1. 상품추가\n2. 상품수정\n3. 상품삭제\n4. 내 상품보기\n5. 매출 확인하기\n6. 게시판 관리\n7. 로그아웃");
			int choice = sc.nextInt();
			if (choice == 7) {
				System.out.println("관리자님 안녕히가세요.");
				break;
			}
			switch (choice) {
			case 1:
				// 상품추가
				new P00_AddProductView();
				break;
			case 2:
				// 상품수정
				new P01_UpdateProdView();
				break;
			case 3:
				// 상품삭제
				new P02_DeleteProdView();
				break;
			case 4:
				// 내 상품 보기
				while (true) {
					int choice1 = 0;
					String selCategory = "";
					if (pdao.getCategory().size() != 0) {
						System.out.println("목록 보실 카테고리 번호를 입력해주세요.");
						for (int i = 0; i < pdao.getCategory().size(); i++) {
							System.out.println((i + 1) + ". " + pdao.getCategory().get(i));
						}
						System.out.println(pdao.getCategory().size()+1 + ". 뒤로가기");
						while (true) {
							choice1 = sc.nextInt();
							if(choice1 == pdao.getCategory().size()+1) {
								System.out.println("뒤로 갑니다.");
								 break;
							}
							if (choice1 <= pdao.getCategory().size() && choice1 > 0) {
								selCategory = pdao.getCategory().get(choice1 - 1);
								ArrayList<ProductDTO> prodList = pdao.getList(selCategory);
								ProductDTO[] arPdto = new ProductDTO[prodList.size()];
								System.out.println("★☆★☆★☆★☆★☆선택하신 카테고리 상품 목록입니다★☆★☆★☆★☆★☆\n상품번호\t"
										+ String.format("%-30s\t", "상품이름") + "현재재고\t상품가격\t상품정보");
								for (int i = 0; i < prodList.size(); i++) {
									arPdto[i] = prodList.get(i);
								}
								for (int i = 0; i < arPdto.length; i++) {
									System.out.println(String.format("%d.\t%-30s\t%d\t%d\t%s", arPdto[i].getProdnum(),
											arPdto[i].getProdname(), arPdto[i].getProdamount(),
											arPdto[i].getProdprice(), arPdto[i].getProdinfo()));
								}
							} else {
								System.out.println("카테고리 번호를 다시 입력해주세요.");
								break;
							}
						}
						break;
						
					} else {
						System.out.println("현재 추가된 카테고리가 없습니다.");
						break;
					}
				}
				break;
			case 5:
				// 매출 확인하기
				new P03_SalesView();
				break;
			case 6:
				// 게시판 관리
				new B06_AdminBoardView();
				break;
			default:
				System.out.println("항목을 다시 선택해주세요.");
			}
		}
	}
}