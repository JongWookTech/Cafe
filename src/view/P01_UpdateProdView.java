package view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import dao.ProductDAO;
import dto.ProductDTO;

public class P01_UpdateProdView {
	public P01_UpdateProdView() {
		ProductDAO pdao = new ProductDAO();
		Scanner sc = new Scanner(System.in);
		System.out.println("1. 카테고리 이름 수정하기\n2. 상품정보 수정하기 \n3. 뒤로 가기");
		int choice = sc.nextInt();
		if (choice == 3) {
			System.out.println("항목을 다시 선택해주세요.");
		}
		if (choice == 1) {
			// 카테고리 수정
			while (true) {
				int choice1 = 0;
				String selCategory = "";
				if (pdao.getCategory().size() != 0) {
					while (true) {
						System.out.println("수정하실 카테고리 번호를 입력해주세요.");
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
							if (pdao.checkType(selCategory)) {
								System.out.print("새롭게 수정할 데이터 : ");
								sc = new Scanner(System.in);
								String newData = sc.nextLine();
								if (pdao.updateCategory(selCategory, newData)) {
									System.out.println(selCategory + " 카테고리 이름이 " + newData + "(으)로 수정 완료되었습니다.");
									new A02_AdminView();
								} else {
									System.out.println("수정을 실패하였습니다. / 다시 시도해주세요");
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
		} else if (choice == 2) {
			while (true) {
				if (pdao.getCategory().size() != 0) {
					while (true) {
						System.out.println("수정하실 카테고리 번호를 입력해주세요.");
						// if문으로 choice가 그 배열의 크기보다 크거나같은지 보면 될거같은데 0보다 작거나 같은지
					for (int i = 0; i < pdao.getCategory().size(); i++) {
						System.out.println((i + 1) + ". " + pdao.getCategory().get(i));
					}
					System.out.println(pdao.getCategory().size() + 1 + ". 나가기");

						int choice2 = sc.nextInt();
						if (choice2 == pdao.getCategory().size() + 1) {
							System.out.println("뒤로 갑니다");
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
								System.out.println("수정하실 상품 번호를 입력해주세요.");
								int prodnum = sc.nextInt();
								if (prodnum == 0) {
									new A02_AdminView();
								}
								for (int i = 0; i < arPdto.length; i++) {
									if (prodnum == arPdto[i].getProdnum()) {
										System.out.println("수정하실 데이터 항목을 선택해주세요.\n1. 상품이름\n2. 상품가격\n3. 상품설명\n4. 상품재고\n5. 카테고리 \n6. 뒤로가기");
										choice = sc.nextInt();
										if (choice == 6) {
											System.out.println("뒤로 갑니다.\n");
											new A02_AdminView();
											break;
										}
										String[] columns = { "상품이름", "상품가격", "상품설명", "상품재고", "카테고리" };
										System.out.print("새롭게 수정할 데이터를 입력해주세요.\n" + columns[choice - 1] + " : ");
										sc = new Scanner(System.in);
										String newData = sc.nextLine();
										if (pdao.updateProd(prodnum, choice, newData)) {
											System.out.println(prodnum + "번 상품 수정이 완료되었습니다.");
											new A02_AdminView();
											break;
										} else {
											System.out.println("수정을 실패하였습니다. / 다시 시도해주세요");
										}
									} else {
										System.out.println("상품 번호를 다시 입력해주세요.");
										try {
											Thread.sleep(1000);
										} catch (InterruptedException e) {
											e.printStackTrace();
											System.out.println("Thread.sleep 오류 - UpdateProdView");
										}
									}
									break;
								}
							}
						} else {
							System.out.println("입력하신 카테고리 번호가 존재하지 않습니다. ");
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
