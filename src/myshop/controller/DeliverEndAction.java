package myshop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.MemberVO;
import myshop.model.InterProductDAO;
import myshop.model.ProductDAO;

public class DeliverEndAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String method = req.getMethod();
		if(!"POST".equalsIgnoreCase(method)) {
			req.setAttribute("msg", "잘못된 접근입니다.");
			req.setAttribute("loc", "index.do");
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		MemberVO loginuser = super.getLoginUser(req);
		if(loginuser==null) return;
		else {
			if(!"admin".equals(loginuser.getUserid())) {
				req.setAttribute("msg", "관리자만 접근 가능합니다.");
				req.setAttribute("loc", "index.do");
				super.setViewPage("/WEB-INF/msg.jsp");
				return;
			}
			String[] odrcodeArr = req.getParameterValues("odrcode");
			String[] pnumArr = req.getParameterValues("deliverEndPnum");
			InterProductDAO productdao = new ProductDAO();// 인터페이스의 메소드만 사용하겠다
			StringBuilder sb = new StringBuilder();
			//'s20181122-18'.'s20181122-19'
			//s20181122-1은 전표 뒤에붙은 8은 제품번호
			//이것은 오라클에서 영수증번호 ||제품번호 로 하겠다는 말이다.
			for(int i=0;i<odrcodeArr.length;i++) {
				sb.append("\'"+odrcodeArr[i]);
				sb.append("/"+pnumArr[i]+"\',");
			}
			String odrcodePnum = sb.toString();
			// 맨뒤의 , 제거하기
			odrcodePnum = odrcodePnum.substring(0,odrcodePnum.length()-1);
			int n=productdao.updateDeliverEnd(odrcodePnum,odrcodeArr.length);
			String msg="";
			if(n!=0) msg = "선택하신 제품들은 배송완료로 변경 되었습니다.";
			else msg="선택한 제품 배송완료로 변경 실패!!";
			req.setAttribute("msg", msg);
			req.setAttribute("loc", "orderList.do");
			super.setViewPage("/WEB-INF/msg.jsp");
		}
	}
}
