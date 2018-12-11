package myshop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.MemberVO;
import myshop.model.ProductDAO;

public class CartEditAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String method = req.getMethod();
		System.out.println("check");
		if(!"POST".equalsIgnoreCase(method)) {
			req.setAttribute("msg", "비정상 적인 경로로 들어 왔습니다.");
			req.setAttribute("loc", "javascript:history.back();");
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		MemberVO member = super.getLoginUser(req);
		if(member!=null && "admin".equals(member.getUserid())) {
			req.setAttribute("msg", "관리자는 장바구니 목록을 변경 할 수 없습니다.");
			req.setAttribute("loc", "javascript:history.back();");
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		else if(member!=null && !"admin".equals(member.getUserid())) {
			String cartno = req.getParameter("cartno");
			String oqty = req.getParameter("oqty");
			ProductDAO productdao = new ProductDAO();
			int n = productdao.updateDeleteCart(cartno,oqty);
			String msg = (n>0)?"장바구니에 제품수량 변경 성공!!":"장바구니에 제품수량 변경 실패!!";
			String loc = (n>0)?"myCartList.do":"javascript:history.back();";
			req.setAttribute("msg", msg);
			req.setAttribute("loc", loc);
			super.setViewPage("/WEB-INF/msg.jsp");
		}
	}

}
