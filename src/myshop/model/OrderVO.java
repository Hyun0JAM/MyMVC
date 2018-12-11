package myshop.model;

public class OrderVO {
	private int odrcode;
	private String fk_userid;
	private int odrtotalPrice;
	private int odrtotalPoint;
	private String odrdate;
	
	public OrderVO() {}
	public OrderVO(int odrcode, String fk_userid, int odrtotalPrice, int odrtotalPoint, String odrdate) {
		super();
		this.odrcode = odrcode;
		this.fk_userid = fk_userid;
		this.odrtotalPrice = odrtotalPrice;
		this.odrtotalPoint = odrtotalPoint;
		this.odrdate = odrdate;
	}
	public int getOdrcode() {
		return odrcode;
	}
	public void setOdrcode(int odrcode) {
		this.odrcode = odrcode;
	}
	public String getFk_userid() {
		return fk_userid;
	}
	public void setFk_userid(String fk_userid) {
		this.fk_userid = fk_userid;
	}
	public int getOdrtotalPrice() {
		return odrtotalPrice;
	}
	public void setOdrtotalPrice(int odrtotalPrice) {
		this.odrtotalPrice = odrtotalPrice;
	}
	public int getOdrtotalPoint() {
		return odrtotalPoint;
	}
	public void setOdrtotalPoint(int odrtotalPoint) {
		this.odrtotalPoint = odrtotalPoint;
	}
	public String getOdrdate() {
		return odrdate;
	}
	public void setOdrdate(String odrdate) {
		this.odrdate = odrdate;
	}
}
