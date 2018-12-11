package ajax.controller.chap4;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;

public class SecondPersonAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		super.setViewPage("/ajaxstudy/chap4/2PersonArrAjax.jsp");

	}

}
