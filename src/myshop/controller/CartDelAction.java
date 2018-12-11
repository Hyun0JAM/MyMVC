package myshop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import myshop.model.ProductDAO;

public class CartDelAction extends AbstractController {
	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String cartno = req.getParameter("cartno");
		ProductDAO productdao = new ProductDAO();
		int n=productdao.updateDeleteCart(cartno,"0");
		String msg="";
		if(n==1) msg="삭제가 완료되었습니다.";
		else msg="삭제에 실패하였습니다.";
		req.setAttribute("msg", msg);
		req.setAttribute("loc", "myCartList.do");
		super.setViewPage("/WEB-INF/msg.jsp");
	}
}
