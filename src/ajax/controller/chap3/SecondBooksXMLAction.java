package ajax.controller.chap3;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ajax.model.AjaxDAO;
import ajax.model.InterAjaxDAO;
import common.controller.AbstractController;

public class SecondBooksXMLAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		InterAjaxDAO adao = new AjaxDAO();
		List<HashMap<String,String>> booklist = adao.getAllBooks();
		req.setAttribute("booklist", booklist);
		super.setViewPage("/ajaxstudy/chap3/2booksXML.jsp");
	}
}
