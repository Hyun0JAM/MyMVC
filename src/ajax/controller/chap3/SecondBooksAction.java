package ajax.controller.chap3;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;

public class SecondBooksAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		super.setViewPage("/ajaxstudy/chap3/2booksAjax.jsp");
	}

}
