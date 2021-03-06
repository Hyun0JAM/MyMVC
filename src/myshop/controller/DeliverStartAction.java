package myshop.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.controller.GoogleMail;
import member.model.MemberVO;
import myshop.model.InterProductDAO;
import myshop.model.ProductDAO;

public class DeliverStartAction extends AbstractController {

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
			String[] pnumArr = req.getParameterValues("deliverStartPnum");
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
			int n=productdao.updateDeliverStart(odrcodePnum,odrcodeArr.length);
			String msg="";
			if(n!=0) {
				//배송 완료 메일 보내기
				GoogleMail mail = new GoogleMail();
				List<MemberVO> memberlist = new ArrayList<MemberVO>();
				for(int i=0;i<odrcodeArr.length;i++) {
					MemberVO member = productdao.getOrderMemberOne(odrcodeArr[i]);
					memberlist.add(member);
				} 
				List<HashMap<String,String>> sendemail = productdao.distinctOrder(memberlist);
				for(int i=0;i<sendemail.size();i++) {	
					String content = "주문번호 : ["+odrcodeArr[i]+"] <br/> "
							   + "상품 ["+productdao.getProductOneByPnum(Integer.parseInt(pnumArr[i])).getPname()+"]가 상품이 배송시작되었습니다.";
					mail.sendmail_DeliverStart(sendemail.get(i).get("email"),sendemail.get(i).get("name"),content);
				}
				msg = "선택하신 제품들은 배송시작으로 변경 되었습니다.";
			}
			else msg="선택한 제품 배송시작으로 변경 실패!!";
			req.setAttribute("msg", msg);
			req.setAttribute("loc", "orderList.do");
			super.setViewPage("/WEB-INF/msg.jsp");
		}
	}
}
