package view;

import java.util.ArrayList;
import java.util.Scanner;

import dao.ProductDAO;
import dao.UserDAO;
import dto.ProductDTO;

public class A03_NonMainView {
	public A03_NonMainView() {
		boolean flag = false;
		Scanner sc = new Scanner(System.in);
		ProductDAO pdao = new ProductDAO();
		UserDAO udao = new UserDAO();
		System.out.println("[비회원 상품 주문 창입니다]");
		System.out.println("회원일시 혜택이 있습니다.\n 회원가입 하시겠습니까?");
		System.out.println("(Y|N)둘 중 입력해 주세요");
		String choice1 = "";
		String selCategory = "";
		int choice2 = 0;
		while (true) {
			choice1 = sc.next();
			if (choice1.equals("Y") || choice1.equals("y")) {
				new U01_JoinView();
			} else if (choice1.equals("N") || choice1.equals("n")) {
				while (true) {
					if (pdao.getCategory().size() != 0) {
						System.out.println("[주문 카테고리 입니다]\n 원하시는 카테고리 번호를 입력해 주세요");
						for (int i = 0; i < pdao.getCategory().size(); i++) {
							System.out.println((i + 1) + "." + pdao.getCategory().get(i));
						}
						while (true) {
							choice2 = sc.nextInt();
							if (choice2 <= pdao.getCategory().size() && choice2 > 0) {
								selCategory = pdao.getCategory().get(choice2 - 1);
								ArrayList<ProductDTO> prodList = pdao.getList(selCategory);
								ProductDTO[] arPdto = new ProductDTO[prodList.size()];
								System.out.println("★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆선택하신 카테고리 상품 목록입니다★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆\n상품번호\t"
										+ String.format("%-30s\t", "상품이름") + "현재재고\t상품가격\t상품정보");
								for (int i = 0; i < prodList.size(); i++) {
									arPdto[i] = prodList.get(i);
								}
								for (int i = 0; i < arPdto.length; i++) {
									System.out.println(String.format("%d.\t%-30s\t%d\t%d\t%s", (i + 1),
											arPdto[i].getProdname(), arPdto[i].getProdamount(),
											arPdto[i].getProdprice(), arPdto[i].getProdinfo()));
								}
								System.out.println("★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★");
								System.out.println("주문 하시겠습니까?");
								System.out.println("(Y|N)둘 중 입력해 주세요");
								choice1 = sc.next();
								if (choice1.equals("Y") || choice1.equals("y")) {
									new O01_NonOrderView(selCategory);
									flag = true;
									break;
								} else {
									break;
								}
							} else {
								System.out.println("카테고리 번호를 다시 입력해주세요.");
								break;
							}
						}
						if(flag = true) {
							break;
						}
					} else {
						System.out.println("현재 추가된 카테고리가 없습니다.");
						System.out.println("관리자에게 연락 부탁드립니다. \n(☎ 연락처 : " + udao.contactAdmin() + ")");
						break;
					}
				}
				if(flag = true) {
					break;
				}
			} else {
				System.out.println("다시 입력해 주세요");
				new A03_NonMainView();
				break;
			}
		}
	}
}
