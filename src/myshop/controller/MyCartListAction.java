package myshop.controller;

import java.util.List;
import javax.servlet.http.*;
import common.controller.AbstractController;
import member.model.MemberVO;
import myshop.model.CartVO;
import myshop.model.ProductDAO;

public class MyCartListAction extends AbstractController {
	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		super.getCategoryList(req);
		MemberVO member = super.getLoginUser(req);
		if(member!=null && "admin".equals(member.getUserid())) {
			req.setAttribute("msg", "관리자는 장바구니 목록을 변경 할 수 없습니다.");
			req.setAttribute("loc", "javascript:history.back();");
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		else if(member!=null && !"admin".equals(member.getUserid())) {
			ProductDAO productdao = new ProductDAO();
			List<CartVO> cartlist = productdao.getCartList(member.getUserid());
			req.setAttribute("cartList", cartlist);
			super.setViewPage("/WEB-INF/myshop/myCartList.jsp");
		}
		else {
			req.setAttribute("msg", "로그인 부터 진행해 주세요.");
			req.setAttribute("loc", "index.do");
			super.setViewPage("/WEB-INF/msg.jsp");
		}
	}
}
