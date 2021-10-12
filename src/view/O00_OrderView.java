package view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import dao.ProductDAO;
import dao.Session;
import dao.UserDAO;
import dto.Cart;
import dto.ProductDTO;
import dto.UserDTO;

public class O00_OrderView {
	boolean flag = false;
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	Date time = new Date();
	String today = format1.format(time.getTime());
	// System.out.println(today); // 확인용
	Scanner sc = new Scanner(System.in);
	UserDTO udto = Session.get("session");
	ProductDAO pdao = new ProductDAO();
	UserDAO udao = new UserDAO();
	ProductDTO pdto = new ProductDTO();
	Cart cart = new Cart();
	ArrayList<Cart> cartList = new ArrayList<>();
	int choiceprod = 0;
	int prodAmount = 0;
	

	// 회원일 시 주문
	public O00_OrderView() {
		// 카테고리 보여주기
		String choice1 = "";
		String selCategory = "";
		int choice2 = 0;

		while (true) {
			if (pdao.getCategory().size() != 0) {
				System.out.println("[주문 카테고리 입니다]\n 원하시는 카테고리 번호를 입력해 주세요");
				for (int i = 0; i < pdao.getCategory().size(); i++) {
					System.out.println((i + 1) + "." + pdao.getCategory().get(i));
				}
				System.out.println((pdao.getCategory().size() + 1) + ".나가기");
				choice2 = sc.nextInt();
				if (choice2 == pdao.getCategory().size() + 1) {
					break;
				}
				if (choice2 <= pdao.getCategory().size() && choice2 > 0) {
					selCategory = pdao.getCategory().get(choice2 - 1);
					ArrayList<ProductDTO> orderProdList = pdao.getList(selCategory);
					ProductDTO[] arPdto = new ProductDTO[orderProdList.size()];
					System.out.println("★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆선택하신 카테고리 상품 목록입니다★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆\n상품번호\t"
							+ String.format("%-30s\t", "상품이름") + "현재재고\t상품가격\t상품정보");
					for (int i = 0; i < orderProdList.size(); i++) {
						arPdto[i] = orderProdList.get(i);
					}
					for (int i = 0; i < arPdto.length; i++) {
						System.out.println(String.format("%d.\t%-30s\t%d\t%d\t%s", (i + 1), arPdto[i].getProdname(),
								arPdto[i].getProdamount(), arPdto[i].getProdprice(), arPdto[i].getProdinfo()));
					}
					System.out.println("★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★");
					System.out.println("주문 하시겠습니까?");
					System.out.println("(Y|N)둘 중 입력해 주세요");
					choice1 = sc.next();
					if (choice1.equals("Y") || choice1.equals("y")) {
						while (true) {

							System.out.println("★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆선택하신 카테고리 상품 목록입니다★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆\n상품번호\t"
									+ String.format("%-30s\t", "상품이름") + "현재재고\t상품가격\t상품정보");
							for (int i = 0; i < orderProdList.size(); i++) {
								arPdto[i] = orderProdList.get(i);
							}
							for (int i = 0; i < arPdto.length; i++) {
								System.out.println(String.format("%d.\t%-30s\t%d\t%d\t%s", (i + 1),
										arPdto[i].getProdname(), arPdto[i].getProdamount(), arPdto[i].getProdprice(),
										arPdto[i].getProdinfo()));
							}
							System.out.println("★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★");
							System.out.println("주문하실 상품번호를 입력해주세요");
							choiceprod = sc.nextInt();
							if (choiceprod - 1 < arPdto.length && choiceprod - 1 >= 0) {
								System.out.println("구매 할 수량을 입력해주세요");
								prodAmount = sc.nextInt();
								int sales = prodAmount * orderProdList.get(choiceprod - 1).getProdprice();
								if (orderProdList.get(choiceprod - 1).getProdamount() >= prodAmount) {
									System.out.println("결제 금액은 " + sales + "원 입니다.");
									System.out.println("현재 " + udto.getMember_id() + "님의 보유 포인트는 " + udto.getPoint()
											+ "point 입니다.");
									System.out.println("1. 결제하기\n2. 포인트 충전하기\n3. 장바구니 담기\n4. 나가기");
									choice2 = sc.nextInt();
									switch (choice2) {
									case 4:
										// 나가기
										break;
									case 1:
										// 결제하기
										System.out.println("결제하시겠습니까?");
										System.out.println("(Y|N)둘 중 입력해 주세요");
										choice1 = "";
										choice1 = sc.next();
										if (choice1.equals("Y") || choice1.equals("y")) {
											if (sales <= udto.getPoint()) { // 주문금액이 보유 포인트보다 작거나 같을 시
												// 결제완료되었을 시
												int amount = orderProdList.get(choiceprod - 1).getProdamount()
														- prodAmount;
												int updatePoint = udto.getPoint() - sales;
												udto.setPoint(updatePoint);
												// 잔여 재고 업데이트
												pdao.updateAmount(amount,
														orderProdList.get(choiceprod - 1).getProdname());
												// 잔여 보유포인트 업데이트
												udao.updatePoint(updatePoint, udto.getMember_id());
												System.out.println("결제 완료되었습니다.");
												System.out.println("현재 " + udto.getMember_id() + "님의 보유 포인트는 "
														+ updatePoint + "point 입니다.");
												// 매출 테이블에 추가
												pdao.salesSum(today, sales);
												flag = true;
												break;
											} else {
												System.out.println("보유 포인트가 부족합니다.");
												System.out.println("현재 " + udto.getMember_id() + "님의 보유 포인트는 "
														+ udto.getPoint() + "point 입니다.");
											}
										}
										break;
									case 2:
										// 포인트 충전하기
										new U11_PointAddView();
										break;
									case 3:
										// 장바구니에 담기
										System.out.println("장바구니에 담으시겠습니까?");
										System.out.println("(Y|N)둘 중 입력해 주세요");
										choice1 = sc.next();
										if (choice1.equals("Y") || choice1.equals("y")) {
											cart.setProdnum(choiceprod-1);
											cart.setProdname(orderProdList.get(choiceprod-1).getProdname());
											cart.setProdprice(orderProdList.get(choiceprod-1).getProdprice());
											cart.setProdamount(prodAmount);
											pdao.addCart(cart);
											
//											UserDTO.cart.add(new ProductDTO(0, orderProdList.get(choiceprod-1).getProdname(), 
//											orderProdList.get(choiceprod-1).getProdprice(), prodAmount));
//											UserDTO.cart = new ArrayList<>();
											System.out.println("장바구니에 담겼습니다");
											System.out.println("주문을 더 하시겠습니까?");
											System.out.println("(Y|N)둘 중 입력해 주세요");
											choice1 = sc.next();
											if (choice1.equals("Y") || choice1.equals("y")) {
												cartList= pdao.cartList();
												System.out.println(cartList);
												for (Cart cart : cartList) {
													System.out.println(cart.getProdname());
												}
												break;
											}
										}
										break;

									}
									break;
								}
							} else {
								System.out.println("상품번호를 다시 입력해주세요");
							}
						}

					}
				} else {
					System.out.println("카테고리 번호를 다시 입력해주세요.");
				}
				if (flag == true) {
					break;
				}
			} else {
				System.out.println("현재 추가된 카테고리가 없습니다.");
				System.out.println("관리자에게 연락 부탁드립니다. \n(☎ 연락처 : " + udao.contactAdmin() + ")");
				break;
			}
		}
	}

}
