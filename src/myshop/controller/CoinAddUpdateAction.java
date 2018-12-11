package myshop.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.MemberDAO;
import member.model.MemberVO;

public class CoinAddUpdateAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		MemberVO loginuser = super.getLoginUser(req);
		String idx = req.getParameter("idx");
		if(loginuser==null) return; // 로그인 안된상태라면 코인충전 값 업데이트 불가
		if(!String.valueOf(loginuser.getIdx()).equals(idx)) {
			req.setAttribute("msg", "비정상적인 경로로 들어왔습니다.");
			req.setAttribute("loc", "javascript:history.back();");
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		else {
			HttpSession session = req.getSession();
			@SuppressWarnings("unchecked")
			HashMap<String,String> coinmap = (HashMap<String,String>)session.getAttribute("coinmap");
			String coinmap_idx = coinmap.get("idx");
			System.out.println(coinmap_idx);
			int n=0;
			if(coinmap_idx.equals(idx)) {
				MemberDAO memberdao = new MemberDAO();
				int coinmap_coinmoney = Integer.parseInt(coinmap.get("coinmoney"));
				n = memberdao.coinAddUpdate(idx,coinmap_coinmoney);
			}
			String msg="";
			if(n==1) {
				msg = loginuser.getUserid()+"["+loginuser.getName()+"]님의 코인 "
						+ coinmap.get("coinmoney")+"원 결제가 성공적으로 완료되었습니다.";
				loginuser.setCoin(loginuser.getCoin()+Integer.parseInt(coinmap.get("coinmoney")));
				loginuser.setPoint(loginuser.getPoint()+Integer.parseInt(coinmap.get("coinmoney"))/100);
				session.setAttribute("member", loginuser);
			}
			else {
				msg = loginuser.getUserid()+"["+loginuser.getName()+"]님의 코인 "
				    + coinmap.get("coinmoney")+"원 결제가 실패되었습니다.";
			}	
			req.setAttribute("msg", msg);
			req.setAttribute("loc", "index.do");
			session.removeAttribute("coinmap"); // 세션에 저장된 코인맵 삭제하기
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
	}
}
