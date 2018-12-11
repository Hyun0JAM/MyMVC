package ajax.controller.chap4;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import common.controller.AbstractController;

public class FirstPersonAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		super.setViewPage("/ajaxstudy/chap4/1PersonAjax.jsp");
	}

}
