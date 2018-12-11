package myshop.model;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.*;
import javax.sql.DataSource;

import jdbc.util.AES256;
import member.model.MemberDAO;
import member.model.MemberVO;
import my.util.MyKey;

public class ProductDAO implements InterProductDAO {
	private DataSource ds = null;
    //객체변수 ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
    
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    AES256 aes = null;
    
    /*
     === MemberDAO 생성자에서 해야할일 ===
     
      아파치톰캣이 제공하는 DBCP 객체인 ds 를 얻어오는 것이다.
     */
 public ProductDAO() {
    try {
             
       Context initContext = new InitialContext();
       Context envContext;
       envContext = (Context)initContext.lookup("java:/comp/env");
       ds = (DataSource)envContext.lookup("jdbc/myoracle");// web.xml , context.xml_name. 풀장을 땡겨온것.
       String key = MyKey.key; // 암호화 , 복호화 키 
       aes = new AES256(key);
    } catch (UnsupportedEncodingException e) {
       e.printStackTrace();
    }catch (NamingException e) {
       e.printStackTrace();
    }
    }// end of 생성자
 // === 사용한 자원을 반납하는 close()메소드 생성하기 ===
 public void close() {
    try {
       if(rs !=null) {
          rs.close();
          rs = null;
       }
       if(pstmt!=null) {
          pstmt.close();
          pstmt = null;
       }
       if(conn !=null) {
          conn.close();
          conn = null;
       }
       
    }catch(SQLException e){
       
    }
 }
 
 //*** jsp_product 테이블에서 pspec 컬럼의 값(HIT,new,BEST)별로 상품 목록을 가져오는 추상메소드
 @Override
 public List<ProductVO> selectByPspec(String pspec) throws SQLException {
    List<ProductVO> productList = null;
    try {
       conn = ds.getConnection();
       
       String sql =" select pnum,pname,pcategory_fk,pcompany,pimage1,pimage2,pqty,price,saleprice,pspec,pcontent,point,pinputdate "+
                " from jsp_product "+
                " where pspec = ? "+
                " order by pnum desc ";
       pstmt = conn.prepareStatement(sql);
       pstmt.setString(1,pspec);
       
       rs = pstmt.executeQuery();
       int cnt =0;
       while(rs.next()) {
          cnt++;
          if(cnt ==1) {
             productList = new ArrayList<ProductVO>();
          }
             int pnum = rs.getInt("pnum");
             String pname = rs.getString("pname");
             String pcategory_fk = rs.getString("pcategory_fk");
             String pcompany = rs.getString("pcompany");
             String pimage1 = rs.getString("pimage1");
             String pimage2 = rs.getString("pimage2");
             int pqty = rs.getInt("pqty");
             int price = rs.getInt("price");
             int saleprice = rs.getInt("saleprice");
             String v_pspec = rs.getString("pspec");
             String pcontent = rs.getString("pcontent");
             int point = rs.getInt("point"); 
             String pinputdate = rs.getString("pinputdate");
          
             ProductVO productvo = new ProductVO(pnum, pname, pcategory_fk, pcompany, pimage1, pimage2, pqty, price, saleprice, v_pspec, pcontent, point, pinputdate);
             productList.add(productvo);
       }//end of while
    } finally {
       close();
    }
    
    return productList;
 }
	@Override
	public ProductVO getProductOne(int pnum) throws SQLException {
		ProductVO product=null;
		try {
			conn = ds.getConnection();
			String sql=" select pnum,pname,pcategory_fk,pcompany,pimage1,pimage2,pqty,price,saleprice,pspec,pcontent,point,pinputdate "
				  	 + " from jsp_product "
				  	 + " where pnum=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pnum);
			rs = pstmt.executeQuery();
			if(rs.next()) {
	            String pname = rs.getString("pname");
	            String pcategory_fk = rs.getString("pcategory_fk");
	            String pcompany = rs.getString("pcompany");
	            String pimage1 = rs.getString("pimage1");
	            String pimage2 = rs.getString("pimage2");
	            int pqty = rs.getInt("pqty");
	            int price = rs.getInt("price");
	            int saleprice = rs.getInt("saleprice");
	            String v_pspec = rs.getString("pspec");
	            String pcontent = rs.getString("pcontent");
	            int point = rs.getInt("point"); 
	            String pinputdate = rs.getString("pinputdate");
	         
	            product = new ProductVO(pnum, pname, pcategory_fk, pcompany, pimage1, pimage2, pqty, price, saleprice, v_pspec, pcontent, point, pinputdate);
			}
		} finally {close();}
		return product;
	}
	@Override
	public List<HashMap<String,Object>> getAllCategory() throws SQLException {
		conn = ds.getConnection();
		List<HashMap<String,Object>> hashList = null;
		try {
			String sql = " select cnum,code,cname from jsp_category ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			int cnt=0;
			while(rs.next()) {
				cnt++;
				if(cnt==1) hashList = new ArrayList<HashMap<String,Object>>();
				int cnum = rs.getInt("cnum");
				String code = rs.getString("code");
				String cname = rs.getString("cname");
				HashMap<String,Object> hashmap = new HashMap<String,Object>();
				hashmap.put("cnum", cnum);
				hashmap.put("code", code);
				hashmap.put("cname", cname);
				hashList.add(hashmap);
			}
		} finally {close();}
		return hashList;
	}
	@Override
	public List<HashMap<String,Object>> getAllSpec() throws SQLException {
		conn = ds.getConnection();
		List<HashMap<String,Object>> hashList = null;
		try {
			String sql = " select snum,sname from jsp_spec ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			int cnt=0;
			while(rs.next()) {
				cnt++;
				if(cnt==1) hashList = new ArrayList<HashMap<String,Object>>();
				int snum = rs.getInt("snum");
				String sname = rs.getString("sname");
				HashMap<String,Object> hashmap = new HashMap<String,Object>();
				hashmap.put("snum", snum);
				hashmap.put("sname", sname);
				hashList.add(hashmap);
			}
		} finally {close();}
		return hashList;
	}
	@Override
	public int productInsert(ProductVO pvo) throws SQLException {
		conn = ds.getConnection();
		int result=0;
		try {
			String sql=" insert into jsp_product(pnum, pname, pcategory_fk, pcompany, pimage1, pimage2, pqty, price, saleprice, pspec, pcontent, point) "+
					   " values(?,?,?,?,?,?,?,?,?,?,?,?) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pvo.getPnum());
			pstmt.setString(2, pvo.getPname());
			pstmt.setString(3, pvo.getPcategory_fk());
			pstmt.setString(4, pvo.getPcompany());
			pstmt.setString(5, pvo.getPimage1());
			pstmt.setString(6, pvo.getPimage2());
			pstmt.setInt(7, pvo.getPqty());
			pstmt.setInt(8, pvo.getPrice());
			pstmt.setInt(9, pvo.getSaleprice());
			pstmt.setString(10, pvo.getPspec());
			pstmt.setString(11, pvo.getPcontent());
			pstmt.setInt(12, pvo.getPoint());
			result = pstmt.executeUpdate();
		} finally {close();}
		return result;
	}
	@Override
	public int getPnumOfProduct() throws SQLException {
		conn = ds.getConnection();
		int result=0;
		try {
			String sql=" select seq_jsp_product_pnum.nextval as seq from dual ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			result = rs.getInt("seq");
		} finally {close();}
		return result;
	}
	@Override
	public int product_imagefile_Insert(int pnum, String attachFilename) throws SQLException {
		int result = 0;
		try {
			 conn = ds.getConnection();
			 String sql = " insert into jsp_product_imagefile(imgfileno, fk_pnum, imgfilename) "
			 		    + " values(seq_imgfileno.nextval, ?, ?) ";
			 pstmt = conn.prepareStatement(sql);
			 pstmt.setInt(1, pnum);
			 pstmt.setString(2, attachFilename);
			 result = pstmt.executeUpdate();
		} finally {close();}
		return result;
	}
	// *** 특정 제품에 대한 추가 이미지 파일명을 읽어오는 메소드 생성하기 *** //
	@Override
	public List<HashMap<String, String>> getImagesByPnum(int pnum) throws SQLException {
		List<HashMap<String, String>> imageList = null;
		try {
			 conn = ds.getConnection();
				 String sql = " select imgfilename "
			 		    + " from jsp_product_imagefile "
			 		    + " where fk_pnum = ? "
			 		    + " order by imgfileno asc ";
			 pstmt = conn.prepareStatement(sql);
			 pstmt.setInt(1, pnum);
			 rs = pstmt.executeQuery();
			 int cnt = 0;
			 while(rs.next()) {
				 cnt++;
				 if(cnt==1)  imageList = new ArrayList<HashMap<String, String>>();
				 String imgfilename = rs.getString("imgfilename");
				 HashMap<String, String> imgmap = new HashMap<String, String>();
				 imgmap.put("IMGFILENAME", imgfilename);
				 imageList.add(imgmap);
			 }// end of while-----------------------
		} finally {close();}
		return imageList;		
	}// end of List<HashMap<String, String>> getImagesByPnum(int pnum)-------------------
	
	// *** jsp_category 테이블에서 카테고리코드(code)와 카테고리명(cname)을 읽어오는 메소드 생성하기 *** //
	@Override
	public List<CategoryVO> getCategoryList() throws SQLException {
		List<CategoryVO> categoryList = null;
		try {
			 conn = ds.getConnection();
				 String sql = " select cnum, code, cname "
			 		    + " from jsp_category "
			 		    + " order by cnum asc ";
			 pstmt = conn.prepareStatement(sql);
			 rs = pstmt.executeQuery();
			 int cnt = 0;
			 while(rs.next()) {
				 cnt++;
				 if(cnt==1) categoryList = new ArrayList<CategoryVO>();
				 int cnum = rs.getInt("cnum");
				 String code = rs.getString("code");
				 String cname = rs.getString("cname");
				 CategoryVO cvo = new CategoryVO(cnum, code, cname);
				 categoryList.add(cvo);
			 }// end of while-----------------------
		} finally {close();}
		return categoryList;
	}// end of List<CategoryVO> getCategoryList()------------------
	
	public List<HashMap<String,String>> getProductsByCategory(String code) throws SQLException {
		List<HashMap<String,String>> productList = null;	
		try {
			 conn = ds.getConnection();

			String sql = "select pnum,C.cname as cname, pname, pcategory_fk, pcompany, pimage1, pimage2, pqty, price, saleprice, pspec, pcontent, point "+
						 " , to_char(pinputdate, 'yyyy-mm-dd') as pinputdate "+
						 " from jsp_category C left join jsp_product P on C.code=P.pcategory_fk "+ 
						 " where C.code=? "+
						 " order by pnum desc ";
			 pstmt = conn.prepareStatement(sql);
			 pstmt.setString(1, code);
			 rs = pstmt.executeQuery();
			 int cnt = 0;
			 while(rs.next()) {
				 cnt++;
				 if(cnt==1) productList = new ArrayList<HashMap<String,String>>(); 
				 int pnum = rs.getInt("pnum");
				 String cname = rs.getString("cname");
				 String pname = rs.getString("pname");
				 String pcategory_fk = rs.getString("pcategory_fk");
				 String pcompany = rs.getString("pcompany");
				 String pimage1 = rs.getString("pimage1");
				 String pimage2 = rs.getString("pimage2");
				 int pqty = rs.getInt("pqty");
				 int price = rs.getInt("price");
				 int saleprice = rs.getInt("saleprice");
				 String pspec = rs.getString("pspec");
				 String pcontent = rs.getString("pcontent");
				 int point = rs.getInt("point");
				 String pinputdate = rs.getString("pinputdate");
				 HashMap<String,String> hashProduct = new HashMap<String,String>();
				 hashProduct.put("pnum", String.valueOf(pnum));
				 hashProduct.put("cname",cname);
				 hashProduct.put("pname",pname);
				 hashProduct.put("pcategory_fk",pcategory_fk);
				 hashProduct.put("pcompany",pcompany);
				 hashProduct.put("pimage1",pimage1);
				 hashProduct.put("pimage2",pimage2);
				 hashProduct.put("pqty",String.valueOf(pqty));
				 hashProduct.put("price",String.valueOf(price));
				 hashProduct.put("saleprice",String.valueOf(saleprice));
				 hashProduct.put("pspec",pspec);
				 hashProduct.put("pcontent",pcontent);
				 hashProduct.put("point",String.valueOf(point));
				 hashProduct.put("pinputdate",pinputdate);
				 productList.add(hashProduct);
			 }// end of while-----------------------
		} finally {close();}
		return productList;
	}// end of List<ProductVO> getProductsByCategory(String code)----------------
	
	@Override
	public ProductVO getProductOneByPnum(int pnum) throws SQLException {
        ProductVO pvo = null;
		try {
			 conn = ds.getConnection();
			 String sql = "select pnum, pname, pcategory_fk, pcompany, pimage1, pimage2, pqty, price, saleprice, pspec, pcontent, point "+
					 " , to_char(pinputdate, 'yyyy-mm-dd') as pinputdate "+
					 " from jsp_product "+
					 " where pnum = ? ";
			 pstmt = conn.prepareStatement(sql);
			 pstmt.setInt(1, pnum);
			 rs = pstmt.executeQuery();
			 if(rs.next()) {
				 int v_pnum = rs.getInt("pnum");
				 String pname = rs.getString("pname");
				 String pcategory_fk = rs.getString("pcategory_fk");
				 String pcompany = rs.getString("pcompany");
				 String pimage1 = rs.getString("pimage1");
				 String pimage2 = rs.getString("pimage2");
				 int pqty = rs.getInt("pqty");
				 int price = rs.getInt("price");
				 int saleprice = rs.getInt("saleprice");
				 String v_pspec = rs.getString("pspec");
				 String pcontent = rs.getString("pcontent");
				 int point = rs.getInt("point");
				 String pinputdate = rs.getString("pinputdate");
				 pvo = new ProductVO(v_pnum, pname, pcategory_fk, pcompany, pimage1, pimage2, pqty, price, saleprice, v_pspec, pcontent, point, pinputdate); 
			 }
		} finally {close();}
		return pvo;		
	}// end of ProductVO getProductOneByPnum(int pnum)---------------------
	// **** 장바구니 담기를 해주는 메소드 생성하기 **** //
	// 장바구니 테이블에 해당 제품이 존재하지 않는 경우는 jsp_cart 테이블에 insert 를 해야 한다.
	// 장바구니 테이블에 해당 제품이 존재하는 경우에 또 동일한 제품을 장바구니에 추가해서 담는 경우이라면 jsp_cart 테이블에 update 를 해야 한다.
	@Override
	public int addCart(String userid, String pnum, String oqty) throws SQLException {
		int result = 0;
		try {
			conn = ds.getConnection();
			/*
			    먼저 장바구니 테이블(jsp_cart)에 새로운 제품을 넣는 것인지,
			    아니면 또 다시 제품을 추가로 더 구매하는 것인지 알기 위해서 
			    사용자가 장바구니에 넣으려고 하는 제품번호가 장바구니 테이블에
			    이미 있는지 없는지 먼저 장바구니 번호(cartno)의 값을 알아온다.  
			
			   ------------------------------------------
			    cartno  fk_userid  fk_pnum  oqty  status
			   ------------------------------------------  
			       1      leess      7       2       1
			       2      hongkd     7       5       1
			       3      leess      6       3       1
			 */
			String sql = " select cartno "
					   + " from jsp_cart "
					   + " where status = 1 and "
					   + " fk_userid = ? and "
					   + " pnum = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setString(2, pnum);
			rs = pstmt.executeQuery();
			if(rs.next()) { // 이미 장바구니 테이블에 담긴 제품이라면 update 해주어야 한다.
				int cartno = rs.getInt("cartno");
				sql = " update jsp_cart set oqty = oqty + ? "
					+ " where cartno = ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(oqty));
				pstmt.setInt(2, cartno);
				result = pstmt.executeUpdate();
			}
			else { // 장바구니 테이블에 없는 제품이라면 insert 해준다.
				sql = " insert into jsp_cart(cartno, fk_userid, pnum, oqty) "  
					+ " values(seq_jsp_cart_cartno.nextval, ?, to_number(?), to_number(?) ) "; 
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userid);
				pstmt.setString(2, pnum);
				pstmt.setString(3, oqty);
				result = pstmt.executeUpdate();
			}
		} finally {	close();}
		return result;
	}// end of int addCart(String userid, String pnum, String oqty)-------------------
	// *** 페이징 처리 하기 이전의 장바구니 목록 보여주는 메소드 생성하기 *** //  
	@Override
	public List<CartVO> getCartList(String userid) throws SQLException {
		List<CartVO> cartList = null;
		try {
			 conn = ds.getConnection();
			 String sql = "select A.cartno, A.fk_userid,pqty, A.pnum, pname, pcategory_fk, pimage1, price, saleprice, point, oqty, status "+
					 " from jsp_cart A join jsp_product B on A.pnum = B.pnum "+
					 " where A.status = 1 and A.fk_userid = ? "+
					 " order by A.cartno desc ";
			 pstmt = conn.prepareStatement(sql);
			 pstmt.setString(1, userid);
			 rs = pstmt.executeQuery();
			 int cnt = 0;
			 while(rs.next()) {
				 cnt++;
				 if(cnt==1) cartList = new ArrayList<CartVO>();
				 int cartno = rs.getInt("cartno");
				 String fk_userid = rs.getString("fk_userid");
				 int pnum = rs.getInt("pnum");
				 String pname = rs.getString("pname");
				 String pcategory_fk = rs.getString("pcategory_fk");
				 String pimage1 = rs.getString("pimage1");
				 int price = rs.getInt("price");
			     int saleprice = rs.getInt("saleprice");
			     int point = rs.getInt("point");
			     int oqty = rs.getInt("oqty");
			     int pqty = rs.getInt("pqty");
			     int status = rs.getInt("status");
			     ProductVO item = new ProductVO();
			     item.setPnum(pnum);
			     item.setPname(pname);
			     item.setPcategory_fk(pcategory_fk);
			     item.setPimage1(pimage1);
			     item.setPrice(price);
			     item.setPqty(pqty);
			     item.setSaleprice(saleprice);
			     item.setPoint(point);
			     // !!!!!!!! 중요함 !!!!!!!! //
			     item.setTotalPriceTotalPoint(oqty);
			     // !!!!!!!! 중요함 !!!!!!!! //
				 CartVO cvo = new CartVO();
				 cvo.setCartno(cartno);
				 cvo.setFk_userid(fk_userid);
				 cvo.setPnum(pnum);
				 cvo.setOqty(oqty);
				 cvo.setStatus(status);
				 cvo.setItem(item);
				 cartList.add(cvo);
			 }// end of while-----------------------
		} finally {close();}
		return cartList;
	}// end of List<CartVO> getCartList(String userid)-----------------

	@Override
	public int updateDeleteCart(String cartno, String oqty) throws SQLException {
		//수량의 값이 0이면 장바구니 비우기 0보가 크변 수량 수정
		int result=0;
		try {
			if(Integer.parseInt(oqty)==0) {
				conn = ds.getConnection();
				String sql = " update jsp_cart set status=0 where cartno=? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(cartno));
				result = pstmt.executeUpdate();
			}
			else {
				String sql = " update jsp_cart set oqty=? where cartno=? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, oqty);
				pstmt.setInt(2, Integer.parseInt(cartno));
				result = pstmt.executeUpdate();
			}
		} finally{close();}
		return result;
	}
	@Override
	public int addOrder(String odrcode,String userid,String odrtotalPrice,String odrtotalPoint,String[] pnumArr,String[] oqtyArr,String[] salepriceArr,String[] cartnoArr) throws SQLException {
		int result = 1;
		try {
			// 1. 장바구니테이블(jsp_order)에 입력(insert)
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			String sql=" insert into jsp_order(odrcode,fk_userid,odrtotalPrice,odrtotalPoint,odrdate) "
					 + " values(?,?,?,?,default) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, odrcode);
			pstmt.setString(2, userid);
			pstmt.setInt(3, Integer.parseInt(odrtotalPrice));
			pstmt.setInt(4, Integer.parseInt(odrtotalPoint));
			result *= pstmt.executeUpdate();
			// 2. 주문상세 테이블(jsp_order_detail)에 입력(insert)
			for(int i=0;i<pnumArr.length;i++) {
				sql = " insert into jsp_order_detail(odrseqnum,fk_odrcode,fk_pnum,oqty,odrprice,deliverStatus) "
					+ " values(seq_jsp_order_detail.nextval,?,?,?,?,default) ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, odrcode);
				pstmt.setInt(2, Integer.parseInt(pnumArr[i]));
				pstmt.setInt(3, Integer.parseInt(oqtyArr[i]));
				pstmt.setInt(4, Integer.parseInt(salepriceArr[i]));
				result *= pstmt.executeUpdate();
			}
			// 3. 구매하는 사용자 coin의 컬럼 값을 구입한 가격만큼 감하고(update),point는 더한다.
			sql = " update jsp_member set coin=coin-?,point=point+? where userid=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(odrtotalPrice));
			pstmt.setInt(2, Integer.parseInt(odrtotalPoint));
			pstmt.setString(3, userid);
			result *= pstmt.executeUpdate();
			// 4. 주문한 제품의 잔고량은 주문량만큼 감해야하고(update)
			for(int i=0;i<pnumArr.length;i++) {
				sql = " update jsp_product set pqty=pqty-? where pnum=? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(oqtyArr[i]));
				pstmt.setInt(2, Integer.parseInt(pnumArr[i]));
				result *= pstmt.executeUpdate();
			}
			// 5. 장바구니에서 주문한 것이라면 장바구니비우기(jsp_cart 테이블의 status값을 0으로 변경하는 update)를 해주는 productdao 객체의 메소드를 호출 해야한다. 
			if(cartnoArr!=null) {
				for(int i=0;i<cartnoArr.length;i++) {
					sql = " update jsp_cart set status=0 where cartno=? ";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, Integer.parseInt(cartnoArr[i]));
					result *= pstmt.executeUpdate();
				}	
			}	
			if(result!=0) conn.commit();
		} finally {close();}
		return result;
	}
	@Override
	public String getNumOfOrder() throws SQLException {
		String result ="";
		try {
			conn = ds.getConnection();
			String sql=" select 's'||to_char(sysdate,'yyyymmdd')||'-'||seq_jsp_order.nextval as seq from dual ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			result = rs.getString("seq");
		} finally {close();}
		return result;
	}
	// ==== 사용자가 주문완료한 제품들의 정보를 조회해주는 메소드 생성하기 ===== //
	@Override
	public List<ProductVO> getJumunProductList(String pnumes) throws SQLException {
		List<ProductVO> jumunProductList = null;
		try {
			conn = ds.getConnection();
			String sql = "select pnum, pname, pcategory_fk, pcompany, pimage1, pimage2, pqty, price, saleprice, pspec, pcontent, point\n"+
					 "     , to_char(pinputdate, 'yyyy-mm-dd') as pinputdate\n"+
					 "from jsp_product\n"+
					 "where pnum in("+ pnumes +") ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			int cnt = 0;
			while(rs.next()) {
				cnt++;
				if(cnt==1) jumunProductList = new ArrayList<ProductVO>();
				 int pnum = rs.getInt("pnum");
				 String pname = rs.getString("pname");
				 String pcategory_fk = rs.getString("pcategory_fk");
				 String pcompany = rs.getString("pcompany");
				 String pimage1 = rs.getString("pimage1");
				 String pimage2 = rs.getString("pimage2");
				 int pqty = rs.getInt("pqty");
				 int price = rs.getInt("price");
				 int saleprice = rs.getInt("saleprice");
				 String pspec = rs.getString("pspec");
				 String pcontent = rs.getString("pcontent");
				 int point = rs.getInt("point");
				 String pinputdate = rs.getString("pinputdate");
				 ProductVO productvo = new ProductVO(pnum, pname, pcategory_fk, pcompany, pimage1, pimage2, pqty, price, saleprice, pspec, pcontent, point, pinputdate); 
				 jumunProductList.add(productvo);
			} // end of while-------------------
		} finally {close();}
		return jumunProductList;
	}// end of List<ProductVO> getJumunProductList(String pnumes)---------------
	@Override
	public List<HashMap<String, String>> getOrderList(String userid) throws SQLException {
		List<HashMap<String, String>> orderlist = null;
		try {
			conn = ds.getConnection();
			String sql = " select A.odrcode,A.fk_userid,to_char(A.odrdate,'yyyy-mm-dd hh24:mi:ss') as odrdate "+
					 	 "      ,B.odrseqnum,B.fk_pnum,B.oqty,B.odrprice "+
				 		 "      ,case B.deliverstatus "+
						 "      when 1 then '주문완료' "+
						 "      when 2 then '배송시작' "+
						 "      when 3 then '배송완료' "+
						 "      end as deliverstatus "+
						 "      ,C.pname,C.pimage1,C.price,C.saleprice,C.point "+
						 " from jsp_order A join jsp_order_detail B on A.odrcode = b.fk_odrcode "+
						 "                  join jsp_product C on b.fk_pnum = c.pnum "+ 
						 " where 1=1 ";
			if(!"admin".equalsIgnoreCase(userid)) {
				sql += " and A.fk_userid=? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userid);
				rs = pstmt.executeQuery();
			}
			else {
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
			}
			int cnt = 0;
			while(rs.next()) {
				cnt++;
				if(cnt==1) orderlist = new ArrayList<HashMap<String, String>>();
				HashMap<String,String> hashorderlist = new HashMap<String,String>();
				hashorderlist.put("odrcode", rs.getString("odrcode"));
				hashorderlist.put("odrdate", rs.getString("odrdate"));
				hashorderlist.put("odrseqnum", rs.getString("odrseqnum"));
				hashorderlist.put("pnum", rs.getString("fk_pnum"));
				hashorderlist.put("oqty", rs.getString("oqty"));
				hashorderlist.put("odrprice", rs.getString("odrprice"));
				hashorderlist.put("deliverstatus", rs.getString("deliverstatus"));
				hashorderlist.put("pname", rs.getString("pname"));
				hashorderlist.put("pimage1", rs.getString("pimage1"));
				hashorderlist.put("price", rs.getString("price"));
				hashorderlist.put("saleprice", rs.getString("saleprice"));
				hashorderlist.put("point", rs.getString("point"));
				orderlist.add(hashorderlist);
			} // end of while-------------------
		} finally {close();}
		return orderlist;
	}
	@Override
	public List<HashMap<String, String>> getOrderList(String userid,int currentShowPageNo,int sizePerPage) throws SQLException {
		List<HashMap<String, String>> orderlist = null;
		try {
			conn = ds.getConnection();
			String sql = " select*from( "+
						 " select rownum as rno, A.odrcode, fk_userid,to_char(A.odrdate,'yyyy-mm-dd hh24:mi:ss') as odrdate "+
					 	 "      ,B.odrseqnum,B.fk_pnum,B.oqty,B.odrprice "+
				 		 "      ,case B.deliverstatus "+
						 "      when 1 then '주문완료' "+
						 "      when 2 then '배송시작' "+
						 "      when 3 then '배송완료' "+
						 "      end as deliverstatus "+
						 "      ,C.pname,C.pimage1,C.price,C.saleprice,C.point "+
						 " from jsp_order A join jsp_order_detail B on A.odrcode = b.fk_odrcode "+
						 "                  join jsp_product C on b.fk_pnum = c.pnum "+ 
						 " order by odrseqnum desc "+
						 " ) "+ 
						 " where 1=1 ";
			if(!"admin".equalsIgnoreCase(userid)) {
				sql += " and fk_userid=? and rno between ? and ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userid);
				pstmt.setInt(2,(currentShowPageNo*sizePerPage)-(sizePerPage-1));
				pstmt.setInt(3,currentShowPageNo*sizePerPage);
				rs = pstmt.executeQuery();
			}
			else {
				sql += " and rno between ? and ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,(currentShowPageNo*sizePerPage)-(sizePerPage-1));
				pstmt.setInt(2,currentShowPageNo*sizePerPage);
				rs = pstmt.executeQuery();
			}
			int cnt = 0;
			while(rs.next()) {
				cnt++;
				if(cnt==1) orderlist = new ArrayList<HashMap<String, String>>();
				HashMap<String,String> hashorderlist = new HashMap<String,String>();
				hashorderlist.put("odrcode", rs.getString("odrcode"));
				hashorderlist.put("odrdate", rs.getString("odrdate"));
				hashorderlist.put("odrseqnum", rs.getString("odrseqnum"));
				hashorderlist.put("fk_userid", rs.getString("fk_userid"));
				hashorderlist.put("fk_pnum", rs.getString("fk_pnum"));
				hashorderlist.put("oqty", rs.getString("oqty"));
				hashorderlist.put("odrprice", rs.getString("odrprice"));
				hashorderlist.put("deliverstatus", rs.getString("deliverstatus"));
				hashorderlist.put("pname", rs.getString("pname"));
				hashorderlist.put("pimage1", rs.getString("pimage1"));
				hashorderlist.put("price", rs.getString("price"));
				hashorderlist.put("saleprice", rs.getString("saleprice"));
				hashorderlist.put("point", rs.getString("point"));
				orderlist.add(hashorderlist);
			} // end of while-------------------
		} finally {close();}
		return orderlist;
	}
	@Override
	public int getTotalProductNum(String userid) throws SQLException {
		int result=0;
		try {
			conn = ds.getConnection();
			String sql = " select count(*) as cnt from jsp_order_detail A "+
						 " join jsp_order B on a.fk_odrcode=b.odrcode "+ 
						 " where 1=1 ";
			if(!"admin".equals(userid)) {
				sql+=" and fk_userid=? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userid);
				rs = pstmt.executeQuery();
			}
			else {
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
			}
			if(rs.next()) {
				result = rs.getInt("cnt");
			}
		}finally {close();}
		return result;
	}
	@Override
	public MemberVO getOrderMemberOne(String odrcode) throws SQLException  {
		MemberVO member = null;
		try {
			conn = ds.getConnection();
			String sql = " select idx "+
					 	 " from jsp_member "+
						 " where userid =(select fk_userid from jsp_order where odrcode=?) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, odrcode);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				MemberDAO memberdao = new MemberDAO();
				member = memberdao.getMemberByIdx(rs.getInt("idx"));
			}
		} finally {close();}
		return member;
	}
	@Override
	public int updateDeliverStart(String odrcodepnum,int length) throws SQLException {
		int result=0;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			String sql = "update jsp_order_detail set deliverstatus=2 where fk_odrcode||'/'||fk_pnum in("+odrcodepnum+") ";
			pstmt = conn.prepareStatement(sql);
			result = pstmt.executeUpdate();
			if(length==result) conn.commit();
		} finally {close();}
		return result;
	}
	@Override
	public int updateDeliverEnd(String odrcodepnum,int length) throws SQLException {
		int result=0;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			String sql = "update jsp_order_detail set deliverstatus=3 where fk_odrcode||'/'||fk_pnum in("+odrcodepnum+") ";
			pstmt = conn.prepareStatement(sql);
			result = pstmt.executeUpdate();
			if(length==result) conn.commit();
		} finally {close();}
		return result;
	}
	@Override
	public List<HashMap<String,String>> distinctOrder(List<MemberVO> member) throws SQLException {
		List<HashMap<String,String>> emailList = null;
		try {
			for(int i=0;i<member.size()-1;i++) {
				conn = ds.getConnection();
				String sql = " select distinct email,name from jsp_member where userid in("+member.get(i).getUserid()+")";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				int cnt=0;
				while(rs.next()) {
					cnt++;
					if(cnt==1) emailList = new ArrayList<HashMap<String,String>>();
					HashMap<String,String> hashemail = new HashMap<String,String>();
					hashemail.put("email",rs.getString("email"));
					hashemail.put("name",rs.getString("name"));
					emailList.add(hashemail);
				}
			}
		} finally {close();}
		return emailList;
	}
	// *** jsp_storemap 테이블의 모든 정보(위,경도)를 조회해주는 메소드 생성하기 *** //
		@Override
		public List<StoremapVO> getStoreMap() throws SQLException {

			List<StoremapVO> storemapList = null;
			
			try{
				conn = ds.getConnection();
							
				String sql = " select storeno, storeName, latitude, longitude, zindex, tel, addr, transport "
						   + " from jsp_storemap "
						   + " order by storeno ";
				
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				int cnt = 0;
				while(rs.next()) {
					cnt++;
					
					if(cnt==1)
						storemapList = new ArrayList<StoremapVO>();
					
					int storeno = rs.getInt("storeno");
					String storeName = rs.getString("storeName");
					double latitude = rs.getDouble("latitude");
					double longitude = rs.getDouble("longitude");
					int zindex = rs.getInt("zindex");
					String tel = rs.getString("tel");
					String addr = rs.getString("addr");
					String transport = rs.getString("transport");
									
					StoremapVO mapvo = new StoremapVO(storeno, storeName, latitude, longitude, zindex, tel, addr, transport);
					
					storemapList.add(mapvo);
					
				}// end of while---------------------
				
			 } finally{
				close();
			 }
			
			return storemapList;		

		}// end of List<StoremapVO> getStoreMap()-------------------


		// *** 매장 상세 정보보기 메소드 생성하기 *** //
		@Override
		public List<HashMap<String, String>> getStoreDetail(String storeno) throws SQLException {

			List<HashMap<String, String>> mapList = null;
			
			try{
				conn = ds.getConnection();
				
				String sql = " select storeno, storeName, tel, addr, transport "
						   + " from jsp_storemap  "
						   + " where storeno = ? ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, storeno);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					mapList = new ArrayList<HashMap<String, String>>();
					
					storeno = rs.getString("storeno");
					String storeName = rs.getString("storeName");
					String tel = rs.getString("tel");
					String addr = rs.getString("addr");
					String transport = rs.getString("transport");
									
					HashMap<String, String> map = new HashMap<String, String>();
					
					map.put("storeno", storeno);
					map.put("storeName", storeName);
					map.put("tel", tel);
					map.put("addr", addr);
					map.put("transport", transport);
					
					mapList.add(map);
					
				}// end of if---------------------
				
				sql =  " select img " 
	                + " from jsp_storedetailImg "
	                + " where fk_storeno = ? ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, storeno);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
						
					String img = rs.getString("img");
													
					HashMap<String, String> map = new HashMap<String, String>();
					
					map.put("img", img);
									
					mapList.add(map);
					
				}// end of while---------------------
				
			 } finally{
				close();
			 }
			
			return mapList;		
		}// end of List<HashMap<String, String>> getStoreDetail(String storeno)-----------------
		@Override
		public int totalSpecCount(String pspec) throws SQLException {
			int result =0;
			try {
				conn = ds.getConnection();
				String sql = " select count(*) as cnt from jsp_product where pspec = ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, pspec);
				rs = pstmt.executeQuery();
				rs.next();
				result = rs.getInt("cnt");
			} finally {close();}
			return result;
		}
		@Override
		public List<ProductVO> getProductByPspec(String pspec,int startRno,int endRno) throws SQLException{
			List<ProductVO> pvoList = null;
			try {
				conn = ds.getConnection();
				String sql ="select * from("+
							"    select row_number() over(order by pnum desc) as rno,pnum, pname, pcategory_fk, pcompany, pimage1, pimage2, pqty, price, saleprice,pspec, pcontent, point "+
							"    from jsp_product where pspec = ? "+
							")"+
							"where rno between ? and ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, pspec);
				pstmt.setInt(2, startRno);
				pstmt.setInt(3, endRno);
				rs = pstmt.executeQuery();
				int cnt =0;
				while(rs.next()) {
					cnt++;
					if(cnt==1) pvoList = new ArrayList<ProductVO>();
					int pnum = rs.getInt("pnum");
		            ProductVO pvo = new ProductVO(); 
					String pname = rs.getString("pname");
		             String pcategory_fk = rs.getString("pcategory_fk");
		             String pcompany = rs.getString("pcompany");
		             String pimage1 = rs.getString("pimage1");
		             String pimage2 = rs.getString("pimage2");
		             int pqty = rs.getInt("pqty");
		             int price = rs.getInt("price");
		             int saleprice = rs.getInt("saleprice");
		             String v_pspec = rs.getString("pspec");
		             String pcontent = rs.getString("pcontent");
		             pvo.setPnum(pnum);
		             pvo.setPname(pname);
		             pvo.setPcategory_fk(pcategory_fk);
		             pvo.setPcompany(pcompany);
		             pvo.setPimage1(pimage1);
		             pvo.setPimage2(pimage2);
		             pvo.setPqty(pqty);
		             pvo.setPrice(price);
		             pvo.setSaleprice(saleprice);
		             pvo.setPspec(v_pspec);
		             pvo.setPcontent(pcontent);
		             pvoList.add(pvo);
				}
			} finally {close();}
			return pvoList;
		}
		@Override
		public HashMap<String,Integer> getLikeDislikeCnt(String pnum) throws SQLException{
			HashMap<String,Integer> cntList = new HashMap<String,Integer>();
			try {
				conn = ds.getConnection();
				String sql = " select (select count(*) from jsp_like where pnum = ?) as likecnt "+
							 "    ,(select count(*) from jsp_dislike where pnum = ?) as dislikecnt "+
							 " from dual ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, pnum);
				pstmt.setString(2, pnum);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					cntList.put("likeCnt", rs.getInt("likecnt"));
					cntList.put("dislikeCnt", rs.getInt("dislikecnt"));
				}
			} finally {close();}
			return cntList;
		}
		@Override
		public int insertLike(String userid,String pnum) throws SQLException {
			int result = 0;
			try {
				conn = ds.getConnection();
				conn.setAutoCommit(false);
				String sql = " delete from jsp_dislike where userid=? and pnum=? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userid);
				pstmt.setString(2, pnum);
				pstmt.executeUpdate();
				
				sql = " insert into jsp_like(userid,pnum) values(?,?) ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userid);
				pstmt.setString(2, pnum);
				result = pstmt.executeUpdate();
				
				if(result!=0) conn.commit();
				
			} finally {close();}
			return result;
		}
		@Override
		public int insertDislike(String userid,String pnum) throws SQLException {
			int result = 0;
			try {
				conn = ds.getConnection();
				conn.setAutoCommit(false);
				String sql = " delete from jsp_like where userid=? and pnum=? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userid);
				pstmt.setString(2, pnum);
				pstmt.executeUpdate();
				
				sql = " insert into jsp_dislike(userid,pnum) values(?,?) ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userid);
				pstmt.setString(2, pnum);
				result = pstmt.executeUpdate();
				
				if(result!=0) conn.commit();
				
			} finally {close();}
			return result;
		}
}
