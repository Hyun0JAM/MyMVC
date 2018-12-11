package myshop.model;

public class CartVO {
	private int cartno;
	private String fk_userid;
	private int pnum;
	private int oqty;
	private int status;
	
	private ProductVO item;
	
	public CartVO() {}
	public CartVO(int cartno, String fk_userid, int pnum, int oqty, int status,ProductVO item) {
		super();
		this.cartno = cartno;
		this.fk_userid = fk_userid;
		this.pnum = pnum;
		this.oqty = oqty;
		this.status = status;
		this.item = item;
	}
	public int getCartno() {
		return cartno;
	}
	public void setCartno(int cartno) {
		this.cartno = cartno;
	}
	public String getFk_userid() {
		return fk_userid;
	}
	public void setFk_userid(String fk_userid) {
		this.fk_userid = fk_userid;
	}
	public int getPnum() {
		return pnum;
	}
	public void setPnum(int pnum) {
		this.pnum = pnum;
	}
	public int getOqty() {
		return oqty;
	}
	public void setOqty(int oqty) {
		this.oqty = oqty;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public ProductVO getItem() {
		return item;
	}
	public void setItem(ProductVO item) {
		this.item = item;
	}
}
