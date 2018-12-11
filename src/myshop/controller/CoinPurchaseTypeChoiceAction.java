package myshop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.MemberVO;

public class CoinPurchaseTypeChoiceAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		MemberVO loginuser = super.getLoginUser(req);
		if(loginuser==null) {return;}
		String idx = req.getParameter("idx");
		if(!String.valueOf(loginuser.getIdx()).equals(idx)) {
			req.setAttribute("msg", "비정상적인 경로로 들어왔습니다.");
			req.setAttribute("loc", "javascript:history.back();");
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		else {
			req.setAttribute("idx", idx);
			super.setViewPage("/WEB-INF/myshop/coinPurchaseTypeChoice.jsp");
		}
	}
}
