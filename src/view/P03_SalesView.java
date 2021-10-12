package view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import dao.ProductDAO;
import dto.ProductDTO;

public class P03_SalesView {
// 관리자가 날짜를 지정해서 매출을 확인하는 view
	public P03_SalesView() {
		Scanner sc = new Scanner(System.in);
		ProductDAO pdao = new ProductDAO();

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date time = new Date();

		String format_time1 = format1.format(time.getTime());
//		오늘 날짜 YYYY-MM-DD
		System.out.println(format_time1);

		while (true) {
			System.out.println("1. 오늘 매출 확인\n2. 날짜 지정하기\n3. 뒤로가기");
			int choice = sc.nextInt();
			if (choice == 3) {
				System.out.println("뒤로 갑니다.");
				break;
			}
			if (choice == 1) {
//			매출 확인 쿼리문
				System.out.println("오늘의 매출 : " + pdao.sales(format_time1) + "원 입니다.");

			}
			if (choice == 2) {
//			매출 확인 쿼리문
				String year = "";
				String month = "";
				String day = "";
				int ex_month = 0;

				while (true) {
					System.out.print("확인하실 년도를 입력하세요 : ");
					int years = sc.nextInt();
					if (years < 2000 || years > 2100) {
						System.out.println("년도가 이상합니다.\n다시 입력하여 주세요.");
						continue;
					}
					year = years + "";
					break;
				}
				while (true) {
					System.out.print("확인하실 월을 입력하세요 : ");
					int months = sc.nextInt();
					if (months < 1 || months > 12) {
						System.out.println("월이 이상합니다.\n다시 입력해주세요.");
						continue;
					}
					if (months < 10) {
						month = "0" + months;
					} else {
						month = months + "";
						ex_month = months;
					}
					break;
				}

				while (true) {
					System.out.print("확인하실 날짜를 입력해주세요 : ");
					int days = sc.nextInt();
					if (days < 1 || days > 31) {
						System.out.println("날짜 형식이 잘못되었습니다.\n다시 입력해주세요");
						continue;
					}
					if (ex_month == 2) {
						if (days > 28) {
							System.out.println(month + "월에는 28일 까지밖에 존재하지 않습니다.");
							continue;
						}
					} else if (ex_month == 4 || ex_month == 6 || ex_month == 5 || ex_month == 7 || ex_month == 9
							|| ex_month == 11) {
						if (days > 30) {
							System.out.println(month + "월에는 30일까지 밖에 존재하지 않습니다.");
							continue;
						}
					}
					if (days < 10) {
						day = "0" + days;
					} else {
						day = days + "";
					}
					break;
				}

				String date = year + "-" + month + "-" + day;

				System.out.printf("%s년 %s월 %s일의 매출 : %s 원입니다.\n", year, month, day, pdao.sales(date));

			}

		}

	}
}
