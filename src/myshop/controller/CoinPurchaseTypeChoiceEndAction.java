package myshop.controller;

import java.util.HashMap;

import javax.servlet.http.*;
import common.controller.AbstractController;
import member.model.MemberVO;

public class CoinPurchaseTypeChoiceEndAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		MemberVO loginuser = super.getLoginUser(req);
		if(loginuser==null) return; // 로그인 안된상태라면 결제 불가능
		String idx = req.getParameter("idx");
		String coinmoney = req.getParameter("coinmoney");
		if(!String.valueOf(loginuser.getIdx()).equals(idx)) {
			req.setAttribute("msg", "비정상적인 경로로 들어왔습니다.");
			req.setAttribute("loc", "javascript:history.back();");
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		else {
			req.setAttribute("name", loginuser.getName());
			req.setAttribute("idx", idx);
			req.setAttribute("coinmoney", coinmoney);
			HashMap<String,String> coinmap = new HashMap<String,String>();
			coinmap.put("idx", idx);
			coinmap.put("coinmoney", coinmoney);
			HttpSession session = req.getSession();
			session.setAttribute("coinmap", coinmap);
			super.setViewPage("/WEB-INF/myshop/paymentGateway.jsp");
		}
	}
}
