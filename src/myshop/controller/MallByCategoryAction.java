package myshop.controller;

import java.util.*;
import javax.servlet.http.*;

import common.controller.AbstractController;
import myshop.model.ProductDAO;

public class MallByCategoryAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// *** 카테고리 목록을 가져와서 왼쪽 로그인 폼 아래에 보여주도록 한다. *** // 
		super.getCategoryList(req);
		String code = req.getParameter("code");
		ProductDAO pdao = new ProductDAO();
		List<HashMap<String,String>> prodByCategoryList = pdao.getProductsByCategory(code);
		if(prodByCategoryList==null) {
			req.setAttribute("str_pnum", code);
			super.setViewPage("/WEB-INF/myshop/errorNoProduct.jsp");
			return;
		}
		req.setAttribute("prodByCategoryList", prodByCategoryList); 
		req.setAttribute("cname", prodByCategoryList.get(0).get("cname"));
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/myshop/mallByCategory.jsp");  
	}// end of void execute(HttpServletRequest req, HttpServletResponse res)--------------------------------- 
}
