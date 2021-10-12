package view;

import java.util.Scanner;

import dao.ProductDAO;
import dto.ProductDTO;

public class P00_AddProductView {
	public P00_AddProductView() {
		Scanner sc = new Scanner(System.in);
		ProductDAO pdao = new ProductDAO();
		System.out.print("상품 이름 : ");
		String prodname = sc.nextLine();
		System.out.print("상품 가격 : ");
		int prodprice = sc.nextInt();
		System.out.print("상품 종류 : ");
		String prodtype = sc.next();
		System.out.print("상품 재고 : ");
		int prodamount = sc.nextInt();
		System.out.print("상품 설명 : ");
		sc = new Scanner(System.in);
		String prodinfo = sc.nextLine();

		// 지금 추가중인 상품의 상품번호는 현재 추가되어 있는 마지막 상품의 번호+1 로 설정해 주어야 한다.
		// 현재 추가된 상품정보 중 가장 마지막에 있는 상품의 번호를 받아서 +1 한 채 사용한다.
		ProductDTO newProduct = new ProductDTO(0, prodname, prodprice, prodinfo, prodamount, prodtype);
		if (pdao.addProduct(newProduct)) {
			System.out.println(prodname + " 상품 추가 완료!");
		} else {
			System.out.println("알 수 없는 오류 / 다음에 다시 시도하세요.");
		}
	}
}

