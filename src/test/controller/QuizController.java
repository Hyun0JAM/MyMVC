package test.controller;

import javax.servlet.http.*;
import common.controller.AbstractController;

public class QuizController extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		req.setAttribute("result", "MVC���Ͽ� ���� ���θ� ���ؼ� �� ����� �ϵ��� �սô�.");
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/index.jsp");
	}

}
