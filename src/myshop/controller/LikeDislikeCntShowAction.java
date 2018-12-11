package myshop.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import common.controller.AbstractController;
import myshop.model.InterProductDAO;
import myshop.model.ProductDAO;

public class LikeDislikeCntShowAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String pnum = req.getParameter("pnum");
		InterProductDAO pdao = new ProductDAO();
		HashMap<String,Integer> cntList =pdao.getLikeDislikeCnt(pnum);
		JSONObject jobj = new JSONObject();
		jobj.put("likecnt", cntList.get("likeCnt"));
		jobj.put("dislikecnt", cntList.get("dislikeCnt"));
		String str_jobj = jobj.toString();
		req.setAttribute("str_jobj", str_jobj);
		super.setViewPage("/WEB-INF/myshop/likeDislikeCntShow.jsp");
	}

}
