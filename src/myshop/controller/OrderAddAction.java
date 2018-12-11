package myshop.controller;

import java.util.List;

import javax.servlet.http.*;
import common.controller.AbstractController;
import member.controller.GoogleMail;
import member.model.MemberDAO;
import member.model.MemberVO;
import myshop.model.ProductDAO;
import myshop.model.ProductVO;

public class OrderAddAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String method = req.getMethod();
		if(!"POST".equalsIgnoreCase(method)) {
			req.setAttribute("msg", "비정상적인 접근입니다.");
			req.setAttribute("loc", "javascript:history.back();");
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		else {
			MemberVO member = super.getLoginUser(req);
			if(member==null) { // 로그인을 하지 않은 상태에서 장바구니 담기를 시도한 경우
				//사용자가 로그인을 기도 했을때 장바구니에 담고자 하는 제품 페이지로 돌아가야 한다.
				String goBackURL = req.getParameter("goBackURL");
				HttpSession session = req.getSession();
				session.setAttribute("returnPage", goBackURL);
				return;
			}
			else { // 로그인 한 경우, 장바구니 테이블에 해당 제품을 담아야 한다.
				String[] pnumArr = req.getParameterValues("pnum");
				String[] salepriceArr = req.getParameterValues("saleprice");
				String[] oqtyArr = req.getParameterValues("oqty");
				String[] cartnoArr = req.getParameterValues("cartno");
				String sumtotalprice = req.getParameter("sumtotalprice");
				String sumtotalpoint = req.getParameter("sumtotalpoint");
				/* 1. 장바구니테이블(jsp_order)에 입력(insert)
				 * 2. 주문상세 테이블(jsp_order_detail)에 입력(insert)
				 * 3. 구매하는 사용자 coin의 컬럼 값을 구입한 가격만큼 감하고(update),
				 * 	  point는 더한다.
				 * 4. 주문한 제품의 잔고량은 주문량만큼 감해야하고(update)
				 * 5. 장바구니에서 주문한 것이라면 장바구니비우기(jsp_cart 테이블의 status값을 0으로 변경하는 update)를 해주는 
				 * 	  productdao 객체의 메소드를 호출 해야한다. 
				 * 6. 주문완료 메일 보내기*/
				ProductDAO productdao = new ProductDAO();
				String odrcode = productdao.getNumOfOrder();
				//주문코드 형식 : s+날짜+sequence ==> s20180430-1 , s20180430-2 , s20180430-3 , s20180501-4 , s20180501-5 , s20180501-6
				int n = productdao.addOrder(odrcode,member.getUserid(), sumtotalprice, sumtotalpoint,pnumArr,oqtyArr,salepriceArr,cartnoArr);
				System.out.println(n);
				String msg="",loc="";
				if(n!=0) {

					// **** 주문이 완료되었을시 
					//      세션에 저장되어져 있는 loginuser 정보를 갱신하고
					//      이어서 주문이 완료되었다라는 email 보내기  **** //
					if(n==1) {
						HttpSession session = req.getSession();
						session.removeAttribute("member");
						MemberDAO memberdao = new MemberDAO();
						member = memberdao.getMemberByIdx(member.getIdx());
						member.setCoin(member.getCoin()-Integer.parseInt(sumtotalprice));
						member.setPoint(member.getPoint()-Integer.parseInt(sumtotalpoint));
						session.setAttribute("member", member);
						////////// email 보내기 시작 ///////////
						GoogleMail mail = new GoogleMail();
						
						int length = pnumArr.length;
						StringBuilder sb = new StringBuilder();
						for(int i=0; i<length; i++) {
							sb.append("\'"+pnumArr[i]+"\',");
							/*
							   jsp_product 테이블에서 select 시
							   where 절에 in() 속에 제품번호가 들어가므로
							    홑따옴표(')가 필요한 경우에는 위와같이 해주면 된다.
							 */
						}// end of for-----------------
						
						String pnumes = sb.toString().trim();
						// '3','4','7',
						
						pnumes = pnumes.substring(0, pnumes.length()-1);
						// 맨뒤에 콤마(,)를 제거하기 위함.
						// '3','4','7'
						
					//	System.out.println("~~~~확인용 주문한 제품번호 : " + pnumes);
						// ~~~~확인용 주문한 제품번호 : '3','4','7'
						
						List<ProductVO> jumunProductList = productdao.getJumunProductList(pnumes); 
						// 주문한 제품번호들에 대한 제품정보를 얻어오는 것.
						
						sb.setLength(0);
						// StringBuilder sb 의 초기화하기 
						
						sb.append("주문코드번호 : <span style='color: blue; font-weight: bold;'>"+odrcode+"</span><br/><br/>");
						sb.append("<주문상품><br/>");
						
						for(int i=0; i<jumunProductList.size(); i++) {
							sb.append(jumunProductList.get(i).getPname()+"&nbsp;"+oqtyArr[i]+"개&nbsp;&nbsp;");
							sb.append("<img src='http://192.168.50.53:9090/MyMVC/images/"+jumunProductList.get(i).getPimage1()+"'/>");  
							sb.append("<br/>");
						}// end of for----------------------
						
						sb.append("<br/>이용해 주셔서 감사합니다.");
						
						String emailContents = sb.toString();
						
						mail.sendmail_OrderFinish(member.getEmail(),member.getName(), emailContents);
				        ////////// email 보내기 종료 ///////////
					}
					msg = "주문 접수 완료.";
					loc = "orderList.do";
				}
				else {
					msg = "주문 접수 실패!!!!";
					loc = "javascript:history.back();";
				}
				req.setAttribute("msg", msg);
				req.setAttribute("loc", loc);
				super.setViewPage("/WEB-INF/msg.jsp");
			}
		}
	}
	/*private String getOdrcode() {
		//주문코드 생성하기
		Date now = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
		String today = dateformat.format(now);
		System.out.println(today);
		return "s"+today;
	}*/
}
 