package myshop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.MemberVO;
import myshop.model.ProductDAO;

public class CartAddAction extends AbstractController {

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
				String pnum = req.getParameter("pnum");
				//String saleprice = req.getParameter("saleprice");
				String oqty = req.getParameter("oqty");
				ProductDAO productdao = new ProductDAO();
				int n = productdao.addCart(member.getUserid(), pnum, oqty);
				String msg="",loc="";
				if(n==1) {
					msg = "장바구니에 추가되었습니다.";
					loc = "myCartList.do";
				}
				else {
					msg = "장바구니 추가 실패!!";
					loc = "javascript:history.back();";
				}
				req.setAttribute("msg", msg);
				req.setAttribute("loc", loc);
				super.setViewPage("/WEB-INF/msg.jsp");
			}
		}
	}
}
