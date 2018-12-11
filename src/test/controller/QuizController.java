package test.controller;

import javax.servlet.http.*;
import common.controller.AbstractController;

public class QuizController extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		req.setAttribute("result", "MVC패턴에 대해 공부를 잘해서 꼭 취업을 하도록 합시다.");
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/index.jsp");
	}

}
