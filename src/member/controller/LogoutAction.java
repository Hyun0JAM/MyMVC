package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;

public class LogoutAction extends AbstractController {
	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		session.removeAttribute("member"); // 세션에 저장된 키값 member를 삭제
		super.setViewPage("index.do");
	}
}
