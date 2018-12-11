package myshop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import myshop.model.ProductDAO;

public class MallHomeAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		super.getCategoryList(req);
		
		// 1. HIT 상품 가져오기
		ProductDAO productdao = new ProductDAO();
		//List<ProductVO> hitList = productdao.selectByPspec("HIT");
		//Ajax를 사용하여 상품목록의 페이징 처리를 더보기 방식으로 한것임
		//int totalHITCount = productdao.totalSpecCount("HIT");
		// 페이징 처리 안 한것. 
		//req.setAttribute("totalHITCount", totalHITCount);
		//req.setAttribute("hitList", hitList);
		//super.setViewPage("/WEB-INF/myshop/mallHome.jsp");
		// 2. NEW 상품 가져오기 
		int totalNewCount = productdao.totalSpecCount("NEW");
		req.setAttribute("totalNewCount", totalNewCount);
		super.setViewPage("/WEB-INF/myshop/mallHomeAjax.jsp");
	}// end of void execute(HttpServletRequest req, HttpServletResponse res)--------------
}
