package myshop.controller;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import common.controller.AbstractController;
import member.model.MemberVO;
import myshop.model.InterProductDAO;
import myshop.model.ProductDAO;

public class DislikeAddAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String userid = req.getParameter("userid");
		String pnum = req.getParameter("pnum");
		System.out.println(userid);
		System.out.println(pnum);
		JSONObject jobj = new JSONObject();
		if(userid==null||"".equals(userid)) {
			jobj.put("msg", "로그인을 하신후  싫어요를 클릭하세요.");
		}
		else {
			try {
				InterProductDAO pdao = new ProductDAO();
				int n = pdao.insertDislike(userid, pnum);
				if(n!=0) jobj.put("msg", "해당 상품에 싫어요 버튼을 클릭했습니다.");
			} catch(SQLIntegrityConstraintViolationException e) {
				jobj.put("msg", "이미 싫어요를 클릭하셨습니다.");
			}
		}
		String str_jobj = jobj.toString();
		req.setAttribute("str_jobj", str_jobj);
		super.setViewPage("/WEB-INF/myshop/dislikeAdd.jsp");
	}
}
