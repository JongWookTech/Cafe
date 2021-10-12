package view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import dao.ProductDAO;
import dao.Session;
import dao.UserDAO;
import dto.ProductDTO;
import dto.UserDTO;

public class O01_NonOrderView {
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
	int choiceprod = 0;
	int prodAmount = 0;
	public O01_NonOrderView(String selCategory) {
		ArrayList<ProductDTO> orderProdList = pdao.getList(selCategory);
		ProductDTO[] arPdto = new ProductDTO[orderProdList.size()];
		int choice2 = 0;
		while (true) {
			System.out.println("★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆선택하신 카테고리 상품 목록입니다★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆\n상품번호\t"
					+ String.format("%-30s\t", "상품이름") + "현재재고\t상품가격\t상품정보");
			for (int i = 0; i < orderProdList.size(); i++) {
				arPdto[i] = orderProdList.get(i);
			}
			for (int i = 0; i < arPdto.length; i++) {
				// System.out.println((i+1)+".\t"+arPdto[i].getProdname()+"\t\t"+arPdto[i].getProdprice()+"\t"+arPdto[i].getProdinfo());
				System.out.println(String.format("%d.\t%" + (-30) + "s\t%d\t%d\t%s", (i + 1), arPdto[i].getProdname(),
						arPdto[i].getProdamount(), arPdto[i].getProdprice(), arPdto[i].getProdinfo()));
			}
			System.out.println("★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★");
			System.out.println("주문하실 상품번호를 입력해주세요");
			choiceprod = sc.nextInt();
			if (choiceprod - 1 < arPdto.length && choiceprod - 1 >= 0) {
				while (true) {
					System.out.println("구매 할 수량을 입력해주세요");
					prodAmount = sc.nextInt();
					int sales = prodAmount * orderProdList.get(choiceprod - 1).getProdprice();
					if (orderProdList.get(choiceprod - 1).getProdamount() >= prodAmount) {
						// System.out.print("결제 금액은 :
						// "+(prodAmount*orderProdList.get(choiceprod-1).getProdprice()));
						if (udto != null) {
							// 회원일 경우
							System.out.println("결제 금액은 " + sales + "원 입니다.");
							System.out.println(
									"현재 " + udto.getMember_id() + "님의 보유 포인트는 " + udto.getPoint() + "point 입니다.");
							System.out.println("1. 결제하기\n2. 포인트 충전하기\n3. 나가기");
							choice2 = sc.nextInt();
							if (choice2 == 1) {
								System.out.println("결제하시겠습니까?");
								System.out.println("(Y|N)둘 중 입력해 주세요");
								String choice1 = "";
								choice1 = sc.next();
								if (choice1.equals("Y") || choice1.equals("y")) {
									if (sales <= udto.getPoint()) { // 주문금액이 보유 포인트보다 작거나 같을 시
										// 결제완료되었을 시
										int amount = orderProdList.get(choiceprod - 1).getProdamount() - prodAmount;
										int updatePoint = udto.getPoint() - sales;
										udto.setPoint(updatePoint);
										// 잔여 재고 업데이트
										pdao.updateAmount(amount, orderProdList.get(choiceprod - 1).getProdname());
										// 잔여 보유포인트 업데이트
										udao.updatePoint(updatePoint, udto.getMember_id());
										System.out.println("결제 완료되었습니다.");
										System.out.println("현재 " + udto.getMember_id() + "님의 보유 포인트는 " + updatePoint
												+ "point 입니다.");
										// 매출 테이블에 추가
										pdao.salesSum(today, sales);
										flag = true;
										break;
									} else {
										System.out.println("보유 포인트가 부족합니다.");
										System.out.println("현재 " + udto.getMember_id() + "님의 보유 포인트는 " + udto.getPoint()
												+ "point 입니다.");
									}
								} else {
									new A01_MainView();
									break;
								}
							} else if (choice2 == 2) {
								// 포인트 충전하기
								new U11_PointAddView();
								break;
							} else if (choice2 == 3) {
								// 나가기
								break;
							}
						} else {
							// 비회원일 경우
							System.out.print("결제 금액은 " + sales + "원 입니다.");
							int amount = orderProdList.get(choiceprod - 1).getProdamount() - prodAmount;
							System.out.println("결제하시겠습니까?");
							System.out.println("(Y|N)둘 중 입력해 주세요");
							String choice1 = "";
							choice1 = sc.next();
							if (choice1.equals("Y") || choice1.equals("y")) {
								pdao.updateAmount(amount, orderProdList.get(choiceprod - 1).getProdname());
								System.out.println("결제 완료되었습니다.");
								// 매출 테이블에 추가
								pdao.salesSum(today, sales);
								flag = true;
								break;
							} else {
								new A03_NonMainView();
								break;
							}
						}
					} else {
						System.out.println(
								"재고가 부족합니다. 현재 재고는 " + orderProdList.get(choiceprod - 1).getProdamount() + "개 입니다.");
					}
				}
				if (flag == true) {
					break;
				}
			} else {
				System.out.println("해당 상품번호가 존재하지 않습니다. 다시 입력 부탁드립니다.");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.out.println("Thread.sleep 오류 - OrderView");
				}
			}
			if (choice2 == 3) {
				break;
			}
		}
	}
}
