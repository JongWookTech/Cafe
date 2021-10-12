package dto;

public class Cart {
	   private int prodnum;
	   private String prodname;
	   private int prodprice;
	   private int prodamount;
	   public Cart() {
	}
	   
	public Cart(int prodnum, String prodname, int prodprice, int prodamount) {
		super();
		this.prodnum = prodnum;
		this.prodname = prodname;
		this.prodprice = prodprice;
		this.prodamount = prodamount;
	}
	public int getProdnum() {
		return prodnum;
	}
	public void setProdnum(int prodnum) {
		this.prodnum = prodnum;
	}
	public String getProdname() {
		return prodname;
	}
	public void setProdname(String prodname) {
		this.prodname = prodname;
	}
	public int getProdprice() {
		return prodprice;
	}
	public void setProdprice(int prodprice) {
		this.prodprice = prodprice;
	}
	public int getProdamount() {
		return prodamount;
	}
	public void setProdamount(int prodamount) {
		this.prodamount = prodamount;
	}

}
