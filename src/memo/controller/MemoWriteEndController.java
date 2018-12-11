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
		if(!"POST".equalsIgnoreCase(method)) {//post������� ���°��� �ƴ϶��
			req.setAttribute("msg", "�߸��� ��η� ��� �Խ��ϴ�.");
			req.setAttribute("loc", "javascript:history.back();"); //������
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		else { //post������� ��� �Դٸ�
			HttpSession session = req.getSession();
			MemberVO loginuser = (MemberVO)session.getAttribute("member");
			String msg = req.getParameter("msg");
			String cip = req.getRemoteAddr(); //Ŭ���̾�Ʈ��ip�ּ� �˾ƿ��� (������ ����� ip)

			MemoVO memovo = new MemoVO();
			memovo.setFk_userid(loginuser.getUserid());
			memovo.setName(loginuser.getName());
			memovo.setMsg(msg);
			memovo.setCip(cip);
			int n =memodao.memoInsert(memovo);
			
			if(n==1) {
				//�޸𾲱Ⱑ ���������� insert �Ǿ��ٸ�
				req.setAttribute("msg", "�޸��Է� ����!!");
				req.setAttribute("loc", "memoList.do");
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/msg.jsp");
				return;
			}
			else {
				//�޸𾲱� insert�� ���� �ߴٸ�,
				req.setAttribute("msg", "�޸��Է� ����!!");
				req.setAttribute("loc", "javascript:history.back();");
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/msg.jsp");
				return;
			}
		}
	}// end of excute
}
