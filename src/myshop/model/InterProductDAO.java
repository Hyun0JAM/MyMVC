package myshop.model;

import java.sql.SQLException;
import java.util.*;

import member.model.MemberVO;

public interface InterProductDAO {
	List<ProductVO> selectByPspec(String pspec) throws SQLException;
	ProductVO getProductOne(int pnum)throws SQLException;
	List<HashMap<String,Object>> getAllCategory() throws SQLException;
	List<HashMap<String,Object>> getAllSpec() throws SQLException;
	int productInsert(ProductVO pvo) throws SQLException;
	int getPnumOfProduct() throws SQLException;
	int product_imagefile_Insert(int pnum, String fileName) throws SQLException;
	List<HashMap<String,String>> getImagesByPnum(int pnum) throws SQLException;
	// *** jsp_category 테이블에서 카테고리코드(code)와 카테고리명(cname)을 읽어오는 추상 메소드 *** // 
	List<CategoryVO> getCategoryList() throws SQLException; 
	// **** jsp_jsp_product 테이블에서 카테고리코드(code)별로 제품정보를 읽어오는 추상 메소드 *** // 
	List<HashMap<String,String>> getProductsByCategory(String code) throws SQLException;
	ProductVO getProductOneByPnum(int pnum) throws SQLException;
	int addCart(String userid, String pnum, String oqty) throws SQLException;
	List<CartVO> getCartList(String userid) throws SQLException;
	int updateDeleteCart(String cartno,String oqty) throws SQLException;
	String getNumOfOrder() throws SQLException;
	int addOrder(String odrcode, String userid, String odrtotalPrice, String odrtotalPoint, String[] pnumArr,
	String[] oqtyArr, String[] salepriceArr, String[] cartnoArr) throws SQLException;
	List<ProductVO> getJumunProductList(String pnumes) throws SQLException;
	List<HashMap<String,String>> getOrderList(String userid) throws SQLException;
	List<HashMap<String, String>> getOrderList(String userid, int currentShowPageNo, int sizePerPage)throws SQLException;
	int getTotalProductNum(String userid) throws SQLException;
	MemberVO getOrderMemberOne(String odrcode) throws SQLException;
	int updateDeliverStart(String odrcodepnum,int length) throws SQLException;
	int updateDeliverEnd(String odrcodepnum,int length) throws SQLException;
	List<HashMap<String,String>> distinctOrder(List<MemberVO> member) throws SQLException;
	List<HashMap<String, String>> getStoreDetail(String storeno) throws SQLException;
	List<StoremapVO> getStoreMap() throws SQLException;
	int totalSpecCount(String pspec) throws SQLException;
	List<ProductVO> getProductByPspec(String pspec, int startRno, int endRno) throws SQLException;
	HashMap<String, Integer> getLikeDislikeCnt(String pnum) throws SQLException;
	int insertLike(String userid, String pnum) throws SQLException;
	int insertDislike(String userid, String pnum) throws SQLException;
}
