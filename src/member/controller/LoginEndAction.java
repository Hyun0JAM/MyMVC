package member.controller;

import javax.servlet.http.*;

import common.controller.AbstractController;
import member.model.*;

public class LoginEndAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		MemberDAO memberdao = new MemberDAO();
		String userid = req.getParameter("userid");
		String pwd = req.getParameter("pwd");
		String method = req.getMethod();
		if(!"POST".equalsIgnoreCase(method)) {
			req.setAttribute("loc", "javascript:history.back()");
			req.setAttribute("msg", "비정상적인 접근입니다.");
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");	
			return;
		}
		MemberVO member = memberdao.loginMember(userid,pwd);
		if(member==null) {
			req.setAttribute("loc", "javascript:history.back()");
			req.setAttribute("msg", "아이디와 비밀번호를 확인학세요.");
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		}
		else{
			if(member.isHumyun()) {
				req.setAttribute("msg", "1년이상 접속하지 않아 휴면계정입니다.");
				req.setAttribute("loc", "index.do");
				super.setViewPage("/WEB-INF/msg.jsp");
				return;
			}
			// 로그인 되어진 사용자의 정보(loginuser)를 세션(session)에 저장하도록 한다. 
			HttpSession session = req.getSession();
			session.setAttribute("member", member);
			// **** 세션(session) ****
			/*   세션(session)은 WAS(톰캣서버)의 메모리(RAM)의 일부분을 사용하는 저장공간이다. 
			         세션(session)에 저장된 데이터는 소멸하지 않는 이상 모든 파일(*.do, *.jsp)에서 사용할 수 있도록 접근이 가능하다.  */
			String saveid = req.getParameter("saveid");
			Cookie cookie = new Cookie("saveid",member.getUserid());
			if(saveid!=null) cookie.setMaxAge(7*24*60*60);
			else cookie.setMaxAge(0);
			cookie.setPath("/");
			res.addCookie(cookie);
			String returnPage = (String) session.getAttribute("returnPage");
			if(member.isRequirePwdChange()) {
				req.setAttribute("msg", "비밀번호를 변경하신지 6개월이상 지났습니다. 암호를 변경하여 주세요");
				req.setAttribute("loc", "index.do");
				super.setViewPage("/WEB-INF/msg.jsp");
				return;
			}
			else if(returnPage==null) {
				super.setRedirect(true);
				super.setViewPage("index.do");
				return;
			}
			else { // 로그인을 하지 않은 상태에서 장바구니 담기 또는 바로구매하기를 시도하여 returnPage가 null이 아닌경우
				super.setRedirect(true);
				super.setViewPage(returnPage);
				session.removeAttribute("returnPage");
			}
		}
	}
}
