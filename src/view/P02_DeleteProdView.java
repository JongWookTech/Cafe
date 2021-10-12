package view;

import java.util.ArrayList;
import java.util.Scanner;

import dao.ProductDAO;
import dto.ProductDTO;

public class P02_DeleteProdView {
	public P02_DeleteProdView() {
		ProductDAO pdao = new ProductDAO();
		Scanner sc = new Scanner(System.in);
		System.out.println("1. 카테고리 전체 삭제하기\n2. 카테고리 내 상품 삭제하기 \n3. 뒤로가기");
		int choice = sc.nextInt();
		if (choice == 3) {
			System.out.println("항목을 다시 선택해주세요.");
		}
		if (choice == 1) {
			// 카테고리 전체 삭제
			while (true) {
				int choice1 = 0;
				String selCategory = "";
				if (pdao.getCategory().size() != 0) {
					while (true) {
						System.out.println("삭제하실 카테고리 번호를 입력해주세요.");
						for (int i = 0; i < pdao.getCategory().size(); i++) {
							System.out.println((i + 1) + ". " + pdao.getCategory().get(i));
						}
						System.out.println(pdao.getCategory().size() + 1 + ". 나가기");
						choice1 = sc.nextInt();
						if (choice1 == pdao.getCategory().size() + 1) {
							System.out.println("뒤로 갑니다.");
							break;
						}
						if (choice1 <= pdao.getCategory().size() && choice1 > 0) {
							selCategory = pdao.getCategory().get(choice1 - 1);
							if (pdao.removeCategory(selCategory)) {
								System.out.println("해당 " + selCategory + " 카테고리가 삭제되었습니다.");
								new A02_AdminView();
							} else {
								System.out.println("알 수 없는 오류 / 다음에 다시 시도하세요.");
							}
						} else {
							System.out.println("입력하신 카테고리 번호가 존재하지 않습니다. 카테고리 번호를 다시 입력해주세요.");
						}
					}
				} else {
					System.out.println("현재 추가된 카테고리가 없습니다.");
					break;
				}
				break;
			}
		} else if (choice == 2) {
			while (true) {
				if (pdao.getCategory().size() != 0) {
					while (true) {
						System.out.println("삭제하실 카테고리 번호를 입력해주세요.");
						for (int i = 0; i < pdao.getCategory().size(); i++) {
							System.out.println((i + 1) + "." + pdao.getCategory().get(i));
						}
						System.out.println(pdao.getCategory().size() + 1 + ".나가기");
						int choice2 = sc.nextInt();
						if (choice2 == pdao.getCategory().size() + 1) {
							System.out.println("뒤로 갑니다.");
							break;
						}
						if (choice2 <= pdao.getCategory().size() && choice2 > 0) {
							String selCategory = pdao.getCategory().get(choice2 - 1);
							ArrayList<ProductDTO> prodList = pdao.getList(selCategory);
							ProductDTO[] arPdto = new ProductDTO[prodList.size()];
							while (true) {
								System.out.println("★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆선택하신 카테고리 상품 목록입니다★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆\n상품번호\t"
										+ String.format("%-30s\t", "상품이름") + "현재재고\t상품가격\t상품정보");
								for (int i = 0; i < prodList.size(); i++) {
									arPdto[i] = prodList.get(i);
								}
								for (int i = 0; i < arPdto.length; i++) {
									System.out.println(String.format("%d.\t%-30s\t%d\t%d\t%s", arPdto[i].getProdnum(),
											arPdto[i].getProdname(), arPdto[i].getProdamount(),
											arPdto[i].getProdprice(), arPdto[i].getProdinfo()));
								}
								System.out.println("\n0. 뒤로가기");
								System.out.println("★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★");
								System.out.println("삭제하실 상품 번호를 입력해주세요.");
								int prodnum = sc.nextInt();
								if (prodnum == 0) {
									new A02_AdminView();
								}
								boolean flag2 = false;
								for (int i = 0; i < arPdto.length; i++) {
									if (arPdto[i].getProdnum() == prodnum) {
										flag2 = true;
										if (pdao.removeProduct(prodnum)) {
											System.out.println(prodnum + "번 상품 삭제가 완료되었습니다.");
											new A02_AdminView();
										} else {
											System.out.println("알 수 없는 오류 / 다음에 다시 시도하세요.");
										}
									}
								}
								if (flag2 != true) {
									System.out.println("삭제하실 상품 번호가 존재하지 않습니다. 다시 입력해주세요.");
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
										e.printStackTrace();
										System.out.println("Thread.sleep 오류 - DeleteProdView");
									}
								}
							}

						} else {
							System.out.println("입력하신 카테고리 번호가 존재하지 않습니다. 카테고리 번호를 다시 입력해주세요.");
						}
					}
				} else {
					System.out.println("현재 추가된 카테고리가 없습니다.");
					break;
				}
				break;
			}
		}
	}
}
