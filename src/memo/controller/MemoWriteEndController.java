package memo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.MemberVO;
import memo.model.MemoDAO;
import memo.model.MemoVO;

public class MemoWriteEndController extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		MemoDAO memodao = new MemoDAO();
		String method = req.getMethod();
		if(!"POST".equalsIgnoreCase(method)) {//post방식으로 들어온것이 아니라면
			req.setAttribute("msg", "잘못된 경로로 들어 왔습니다.");
			req.setAttribute("loc", "javascript:history.back();"); //스냅샷
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		else { //post방식으로 들어 왔다면
			HttpSession session = req.getSession();
			MemberVO loginuser = (MemberVO)session.getAttribute("member");
			String msg = req.getParameter("msg");
			String cip = req.getRemoteAddr(); //클라이언트의ip주소 알아오기 (접속한 상대의 ip)

			MemoVO memovo = new MemoVO();
			memovo.setFk_userid(loginuser.getUserid());
			memovo.setName(loginuser.getName());
			memovo.setMsg(msg);
			memovo.setCip(cip);
			int n =memodao.memoInsert(memovo);
			
			if(n==1) {
				//메모쓰기가 정상적으로 insert 되었다면
				req.setAttribute("msg", "메모입력 성공!!");
				req.setAttribute("loc", "memoList.do");
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/msg.jsp");
				return;
			}
			else {
				//메모쓰기 insert가 실패 했다면,
				req.setAttribute("msg", "메모입력 실패!!");
				req.setAttribute("loc", "javascript:history.back();");
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/msg.jsp");
				return;
			}
		}
	}// end of excute
}
