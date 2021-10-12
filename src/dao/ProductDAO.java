package dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.Cart;
import dto.ProductDTO;
import dto.UserDTO;

public class ProductDAO {
	Connection conn;
	PreparedStatement pstm;
	ResultSet rs;

	public ProductDAO() {
		conn = DBConnection.getConnection();
	}

	public boolean addProduct(ProductDTO newProduct) {
		String sql = "INSERT INTO PRODUCT VALUES(PRODUCT_SEQ.NEXTVAL,?,?,?,?,?)";
		int result = 0;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, newProduct.getProdname());
			pstm.setInt(2, newProduct.getProdprice());
			pstm.setString(3, newProduct.getProdinfo());
			pstm.setInt(4, newProduct.getProdamount());
			pstm.setString(5, newProduct.getProdtype());
			result = pstm.executeUpdate();

		} catch (SQLException e) {
			System.out.println("join 예외 발생 : " + e);
		}
		return result == 1;
	}

	public ArrayList<String> getCategory() {
		String sql = "SELECT PRODTYPE FROM PRODUCT GROUP BY PRODTYPE";
		// String sql = "SELECT DISTINCT PRODTYPE FROM PRODUCT";
		// String result = "";
		ArrayList<String> result = new ArrayList<>();
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();

			while (rs.next()) {
				String category = rs.getString(1);
				// result += String.format("%s\n", category);
				result.add(category);
			}
		} catch (SQLException e) {
		}
		return result;
	}

	public ArrayList<ProductDTO> getList(String type) {

		String sql = "SELECT * FROM PRODUCT WHERE PRODTYPE= ? ORDER BY PRODNUM";
		ProductDTO product = null;
		ArrayList<ProductDTO> prodList = new ArrayList<>();
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, type);
			rs = pstm.executeQuery();
			while (rs.next()) {
				product = new ProductDTO(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5),
						rs.getString(6));
				prodList.add(product);
			}

		} catch (SQLException e) {
		}
		return prodList;
	}
	public ProductDTO getDetail(String prodname) {
		
		String sql = "SELECT * FROM PRODUCT WHERE PRODNAME = ?";
		ProductDTO product = null;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, prodname);
			rs = pstm.executeQuery();
			if(rs.next()) {
				product = new ProductDTO(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5),
						rs.getString(6));
			}
			
		} catch (SQLException e) {
		}
		return product;
	}

	public boolean removeProduct(int prodnum) {
		String sql = "DELETE FROM PRODUCT WHERE PRODNUM = " + prodnum;
		int result = 0;
		try {
			pstm = conn.prepareStatement(sql);
			result = pstm.executeUpdate();
		} catch (SQLException e) {
		}
		return result == 1;
	}

	public boolean removeCategory(String prodtype) {
		String sql = "DELETE FROM PRODUCT WHERE PRODTYPE = ?";
		int result = 0;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, prodtype);
			result = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result != 0;
	}

	public boolean updateCategory(String type, String newData) {
		String sql = "UPDATE PRODUCT SET PRODTYPE = ? WHERE PRODTYPE = ?";
		int result = 0;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, newData);
			pstm.setString(2, type);
			result = pstm.executeUpdate(); // 바뀐 행의 수를 반환해준다.
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result != 0;
	}

	public boolean updateProd(int prodnum, int choice, String newData) {
		// 1. 상품이름\n2. 상품가격\n3. 상품설명\n4. 상품재고\n5. 카테고리
		String[] columns = { "PRODNAME", "PRODPRICE", "PRODINFO", "PRODAMOUNT", "PRODTYPE" };
		String sql = "UPDATE PRODUCT SET " + columns[choice - 1] + " = ? WHERE PRODNUM = ?";
		int result = 0;
		try {
			pstm = conn.prepareStatement(sql);
			if (choice == 2 || choice == 4) {
				pstm.setInt(1, Integer.parseInt(newData));
			} else {
				pstm.setString(1, newData);
			}
			pstm.setInt(2, prodnum);

			result = pstm.executeUpdate(); // 바뀐 행의 수를 반환해준다.
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result != 0;
	}

	public boolean checkType(String type) {
		String sql = "SELECT COUNT(*) FROM PRODUCT WHERE PRODTYPE = ?";
		int result = 0;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, type);
			rs = pstm.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
		}
		return result != 0;
	}

	public boolean updateAmount(int amount, String prodname) {
		String sql = "UPDATE PRODUCT SET PRODAMOUNT=? WHERE PRODNAME=?";
		int result = 0;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, amount);
			pstm.setString(2, prodname);
			result = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("주문후 재고 수정 오류" + e);
		}
		return result != 0;
	}

	// 매출 더하기
	public boolean salesSum(String today, int sales) {
		String sql_select = "SELECT COUNT(*) FROM SALES WHERE CL_DATE =?";
		int result_cnt = 0;
		try {
			pstm = conn.prepareStatement(sql_select);
			pstm.setString(1, today);
			rs = pstm.executeQuery();
			if (rs.next()) {
				result_cnt = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("오류3" + e);
		}
		// System.out.println(result_cnt);

		if (result_cnt == 1) {
			// 그 날짜의 테이블이 존재할 때 현재 날짜의 매출액을 뽑는 쿼리문
			String sql_sale = "SELECT SUM(TODAY_SALES) FROM SALES WHERE CL_DATE = ?";
			int result_sale = 0;
			try {
				pstm = conn.prepareStatement(sql_sale);
				pstm.setString(1, today);
				rs = pstm.executeQuery();
				if (rs.next()) {
					result_sale = rs.getInt(1);
				}
			} catch (SQLException e) {
				System.out.println("오류1" + e);
			}
			String sql = "UPDATE SALES SET TODAY_SALES = ? WHERE CL_DATE =?";
			int result = 0;
			try {
				sales = result_sale + sales;
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1, sales);
				pstm.setString(2, today);
				result = pstm.executeUpdate();
			} catch (SQLException e) {
				System.out.println("오류5" + e);
			}
			return result == 1;
		} else if (result_cnt == 0) {
			// 그 날짜의 테이블이 없을때
			String sql = "INSERT INTO SALES VALUES(?, ?)";
			int result = 0;
			try {
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, today);
				pstm.setInt(2, sales);
				result = pstm.executeUpdate();
			} catch (SQLException e) {
				System.out.println("오류2" + e);
			}
			return result == 1;
		} else {
			System.out.println("관리자에게 문의해서 DB를 확인해보세요.");
		}
		return false;
	}

	// 매출 확인 구문
	public int sales(String date) {
		String sql = "SELECT TODAY_SALES FROM SALES WHERE CL_DATE = ?";
		int result = 0;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, date);
			// 검색된 결과 테이블을 ResultSet 타입으로 리턴
			rs = pstm.executeQuery();
			// rs.next() : 행을 하나 아래로 이동
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("join 예외 발생 : " + e);
			System.out.println("여기3");
		}
		return result;
	}

	// 장바구니 담기
	public boolean addCart(Cart cart) {
		int result =0;
		try {
			String sql = "SELECT * FROM TBL_CART WHERE PRODNAME = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, cart.getProdname());
			rs = pstm.executeQuery();
			if(rs.next()) {
				sql = "UPDATE TBL_CART SET PRODAMOUNT = PRODAMOUNT+? WHERE PRODNAME = ?";
				pstm=conn.prepareStatement(sql);
				pstm.setInt(1, cart.getProdamount());
				pstm.setString(2, cart.getProdname());
			}else {
				sql = "INSERT INTO TBL_CART VALUES(?,?,?,?)";
				pstm=conn.prepareStatement(sql);
				pstm.setInt(1, cart.getProdnum());
				pstm.setString(2, cart.getProdname());
				pstm.setInt(3, cart.getProdprice());
				pstm.setInt(4, cart.getProdamount());
			}
			result=pstm.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("장바구니 추가 오류" +e);
		}
		return result==1;
	}
	//장바구니 불러오기
	public ArrayList<Cart> cartList() {
		String sql = "SELECT * FROM TBL_CART";
		Cart cart = new Cart();
		ArrayList<Cart> cartList = new ArrayList<>();
		try {
			pstm=conn.prepareStatement(sql);
			rs=pstm.executeQuery();
			while (rs.next()) {
				cart = new Cart(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
				cartList.add(cart);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("장바구니 불러오기 오류"+e);
		}
		return cartList;
	}

	public boolean deleteCart(String prodname) {
		String sql="DELETE FROM TBL_CART WHERE PRODNAME=?";
		int result=0;
		try {
			pstm=conn.prepareStatement(sql);
			pstm.setString(1, prodname);
			result=pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("장바구니 삭제 오류"+e);
		}
		return result==1;
	}


}
