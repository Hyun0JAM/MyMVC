package ajax.controller.chap2;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ajax.model.AjaxDAO;
import ajax.model.InterAjaxDAO;
import common.controller.AbstractController;

public class ImageHTMLAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		InterAjaxDAO adao = new AjaxDAO();
		List<HashMap<String,String>> imgList = adao.getImages();
		req.setAttribute("imgList", imgList);
		super.setViewPage("/ajaxstudy/chap2/imgInfo.jsp");
	}

}
