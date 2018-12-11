package myshop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import myshop.model.InterProductDAO;
import myshop.model.ProductDAO;
import myshop.model.ProductVO;

public class MallDispalyXMLAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String start = req.getParameter("start");
		String len = req.getParameter("len");
		String pspec = req.getParameter("pspec");
		
		if(start==null|| start.trim().isEmpty()) {
			start="1";
		}
		if(len==null|| len.trim().isEmpty()) {
			len="8";
		}
		if(pspec==null|| pspec.trim().isEmpty()) {
			pspec="HIT";
		}
		int startRno = Integer.parseInt(start);
		int endRno = startRno+Integer.parseInt(len)-1;
		InterProductDAO pdao = new ProductDAO();
		List<ProductVO> productList = pdao.getProductByPspec(pspec,startRno,endRno);
		req.setAttribute("productList", productList);
		super.setViewPage("/WEB-INF/myshop/mallDisplayXML.jsp");
	}

}
