package myshop.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.MemberVO;
import my.util.MyUtil;
import myshop.model.ProductDAO;

public class OrderListAction extends AbstractController {
	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		MemberVO member = super.getLoginUser(req);
		if(member==null) return;
		// 관리자가 아닌 일반 사용자로 로그인했을경우에는  자신이 주문한 내역만 조회해 와야 하고
		// 관리자로 접속한 경우 모든 사용자의 주문내역을 조회 해 와야 한다.
		ProductDAO productdao = new ProductDAO();
		String str_sizePerPage = req.getParameter("sizePerPage");
		if(str_sizePerPage==null) str_sizePerPage = "5";
		int sizePerPage =0;
		try {
			 sizePerPage = Integer.parseInt(str_sizePerPage);
			 if(sizePerPage != 3 && sizePerPage != 5 && 
			    sizePerPage != 10) { 
				 sizePerPage = 5;
			 }
		} catch (NumberFormatException e) {
			 sizePerPage = 5;
		}
		int totalcount = productdao.getTotalProductNum(member.getUserid());
		int totalpage = (int)Math.ceil((double)totalcount/sizePerPage);
		String str_currentShowPageNo = req.getParameter("currentShowPageNo");
		int currentShowPageNo = 0;
		if(str_currentShowPageNo==null) currentShowPageNo = 1;
		else { // ����ڰ� ������ �ϴ� ��������ȣ�� ������ ��� 
			try {
				 currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
				 if(currentShowPageNo < 1 || currentShowPageNo > totalpage) currentShowPageNo = 1;
			} catch (NumberFormatException e) {
				 currentShowPageNo = 1;
			}
		}
		String url = "orderList.do"; 
		int blockSize=10;
		List<HashMap<String,String>> hashOrderList = productdao.getOrderList(member.getUserid(),currentShowPageNo,sizePerPage);
		String pageBar = MyUtil.getSearchPageBar(url,currentShowPageNo,sizePerPage,totalpage,blockSize,"","",0);
		req.setAttribute("sizePerPage", sizePerPage);
		req.setAttribute("pagebar",pageBar);
		req.setAttribute("orderList", hashOrderList);
		super.setViewPage("/WEB-INF/myshop/orderList.jsp");
	}
}