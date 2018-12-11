package myshop.controller;

import java.util.*;
import javax.servlet.http.*;
import common.controller.AbstractController;
import my.util.MyUtil;
import myshop.model.ProductDAO;
import myshop.model.ProductVO;

public class ProdViewAction extends AbstractController {
	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// *** 카테고리 목록을 가져와서 왼쪽 로그인 폼 아래에 보여주도록 한다. *** // 
		super.getCategoryList(req);
		String str_pnum = req.getParameter("pnum");
		try {
			int pnum = Integer.parseInt(str_pnum);
			ProductDAO pdao = new ProductDAO();
			ProductVO product = pdao.getProductOneByPnum(pnum);
			if(product == null) {
				req.setAttribute("str_pnum", str_pnum);
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/myshop/errorNotProduct.jsp");
				return;
			}
			List<HashMap<String,String>> imgList = pdao.getImagesByPnum(pnum);
			req.setAttribute("product", product);
			req.setAttribute("imgList", imgList);
			req.setAttribute("goBackURL", MyUtil.getCurrentURL(req)); 
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/myshop/prodView.jsp");
		} catch (NumberFormatException e) {
			req.setAttribute("str_pnum", str_pnum);
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/myshop/errorNotProduct.jsp");
			return;
		}
	}// end of void execute(HttpServletRequest req, HttpServletResponse res)-------------------
}
