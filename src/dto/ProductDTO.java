package dto;

public class ProductDTO {
   //다른 상품 정보들은 전부 중복이 가능하기 때문에 고유한 값을 하나 만들어 주어야 한다.
   private int prodnum;
   private String prodname;
   private int prodprice;
   private String prodinfo;
   private int prodamount;
   private String prodtype;
   
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

   public String getProdinfo() {
      return prodinfo;
   }

   public void setProdinfo(String prodinfo) {
      this.prodinfo = prodinfo;
   }

   public int getProdamount() {
      return prodamount;
   }

   public void setProdamount(int prodamount) {
      this.prodamount = prodamount;
   }

   public String getProdtype() {
      return prodtype;
   }

   public void setProdtype(String prodtype) {
      this.prodtype = prodtype;
   }

   
   
   public ProductDTO(int prodnum, String prodname, int prodprice, int prodamount) {
	super();
	this.prodnum = prodnum;
	this.prodname = prodname;
	this.prodprice = prodprice;
	this.prodamount = prodamount;
}

public ProductDTO(int prodnum, String prodname, int prodprice, String prodinfo, int prodamount, String prodtype) {
      super();
      this.prodnum = prodnum;
      this.prodname = prodname;
      this.prodprice = prodprice;
      this.prodinfo = prodinfo;
      this.prodamount = prodamount;
      this.prodtype = prodtype;
   }

   public ProductDTO() {}

   @Override
   public String toString() {
      return prodnum+"\t"+prodname+"\t"+prodprice+"\t"+prodinfo+
            "\t"+prodamount+"\t"+prodtype+"\t";
   }
   
}