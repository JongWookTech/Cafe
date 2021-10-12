package dto;

import java.util.ArrayList;

public class UserDTO {
	// Alt + Shift + A : 여러줄 동시에 수정하기
	private String member_id;
	private String member_pw;
	private String member_name;
	private int point;
	private String phone_num;
	private String member_type;
//	public static ArrayList<ProductDTO> cart;

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getMember_pw() {
		return member_pw;
	}

	public void setMember_pw(String member_pw) {
		this.member_pw = member_pw;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	public String getMember_type() {
		return member_type;
	}

	public void setMember_type(String member_type) {
		this.member_type = member_type;
	}

	public UserDTO() {
	}

	public UserDTO(String member_id, String member_pw, String member_name, int point, String phone_num,
			String member_type) {
		this.member_id = member_id;
		this.member_pw = member_pw;
		this.member_name = member_name;
		this.point = point;
		this.phone_num = phone_num;
		this.member_type = member_type;
	}

	public UserDTO(String member_id, String member_pw, String member_name, int point, String phone_num,
			String member_type, int prodnum, String prodname, int prodprice, int prodamount) {
		super();
		this.member_id = member_id;
		this.member_pw = member_pw;
		this.member_name = member_name;
		this.point = point;
		this.phone_num = phone_num;
		this.member_type = member_type;
	}

	@Override
	public String toString() {
		return member_id + "\t" + member_pw + "\t" + member_name + "\t" + point + "\t" + phone_num + "\t" + member_type;
	}

}
