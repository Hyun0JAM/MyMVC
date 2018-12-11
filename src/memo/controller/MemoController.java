package memo.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.MemberVO;
import my.util.MyUtil;

public class MemoController extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HashMap<String,Object> map = MyUtil.getLoginCheck(req, res);
		MemberVO loginuser = (MemberVO)map.get("loginuser");
		if(loginuser==null) {
			req.setAttribute("msg", map.get("msg"));
			req.setAttribute("loc", map.get("loc"));
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}
		//super.setRedirect(true); �����Ͱ� ���⶧���� ���ʿ����. false�ϰ�� forward���
		super.setViewPage("/WEB-INF/memo/memoForm.jsp");
	}

}
