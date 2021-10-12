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

public class U12_MyCart {
	UserDTO udto = Session.get("session");
	Scanner sc = new Scanner(System.in);
	ProductDAO pdao = new ProductDAO();
	UserDAO udao = new UserDAO();
	Cart cart = new Cart();
	ArrayList<Cart> cartList = new ArrayList<>();
	int prodnum = 0;
	boolean result = false;
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	Date time = new Date();
	String today = format1.format(time.getTime());

	public U12_MyCart() {
		while (true) {
			cartList = pdao.cartList();
			System.out.println("★My Cart★\n");
			if (cartList.size() != 0) {
				int tot = 0;
				for (int i = 0; i < cartList.size(); i++) {
					System.out.println((i + 1) + "." + cartList.get(i).getProdname() + "(주문 수량 : "
							+ cartList.get(i).getProdamount() + "개)");
					tot += cartList.get(i).getProdprice() * cartList.get(i).getProdamount();
				}
				System.out.println("1. 전체 상품 주문하기\n2. 상품 삭제하기\n3. 나가기");
				int choice = sc.nextInt();
				if (choice == 3) {
					break;
				} else if (choice == 1) {
					// 주문
					System.out.println("전체 구매하실 금액은 " + tot + "원 입니다.");
					System.out.println("결제하시겠습니까?");
					System.out.println("(Y|N)둘 중 입력해 주세요");
					String choice1 = "";
					choice1 = sc.next();
					if (choice1.equals("Y") || choice1.equals("y")) {
						if (tot <= udto.getPoint()) { // 주문금액이 보유 포인트보다 작거나 같을 시
							// 결제완료되었을 시
							int updatePoint = udto.getPoint() - tot;
							udto.setPoint(updatePoint);
							// 잔여 보유포인트 업데이트
							udao.updatePoint(updatePoint, udto.getMember_id());
							for (int i = 0; i < cartList.size(); i++) {
								ProductDTO product = pdao.getDetail(cartList.get(i).getProdname());
								int amount = product.getProdamount()-cartList.get(i).getProdamount();
								// 잔여 재고 업데이트
								pdao.updateAmount(amount, cartList.get(i).getProdname());
								pdao.deleteCart(cartList.get(i).getProdname());
							}
							System.out.println("결제 완료되었습니다.");
							System.out.println(
									"현재 " + udto.getMember_id() + "님의 보유 포인트는 " + updatePoint + "point 입니다.");
							// 매출 테이블에 추가
							pdao.salesSum(today, tot);
						} else {
							System.out.println("보유 포인트가 부족합니다.");
							System.out.println(
									"현재 " + udto.getMember_id() + "님의 보유 포인트는 " + udto.getPoint() + "point 입니다.");
						}
					}
					break;
				} else if (choice == 2) {
					System.out.println("삭제하시겠습니까?");
					System.out.println("(Y|N)둘 중 입력해 주세요");
					String choice1 = "";
					choice1 = sc.next();
					if (choice1.equals("Y") || choice1.equals("y")) {
						for (int i = 0; i < cartList.size(); i++) {
							System.out.println((i + 1) + "." + cartList.get(i).getProdname() + "(주문 수량 : "
									+ cartList.get(i).getProdamount() + "개)");
						}
						System.out.println("삭제하실 상품번호를 입력해주세요");
						prodnum = sc.nextInt();
						result = pdao.deleteCart(cartList.get(prodnum - 1).getProdname());
						if (result) {
							System.out.println("삭제 되었습니다");
							break;
						} else {
							System.out.println("다시시도해주세요");
						}
					} else {
						break;
					}
				}

			} else {
				System.out.println("장바구니에 담긴 상품이 없습니다.");
				break;
			}

		}
	}
}
