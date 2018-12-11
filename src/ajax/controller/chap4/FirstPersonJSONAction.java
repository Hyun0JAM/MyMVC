package ajax.controller.chap4;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import common.controller.AbstractController;

public class FirstPersonJSONAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HashMap<String,String> hashperson = new HashMap<String,String>();
		hashperson.put("NAME", "이순신");
		hashperson.put("AGE", "27");
		hashperson.put("PHONE", "010-345-6789");
		hashperson.put("EMAIL", "leess@navar.com");
		hashperson.put("ADDR", "서울시 중구 남대문로 24");
		JSONObject jsonperson = new JSONObject();
		jsonperson.put("NAME", hashperson.get("NAME"));
		jsonperson.put("AGE", hashperson.get("AGE"));
		jsonperson.put("PHONE", hashperson.get("PHONE"));
		jsonperson.put("EMAIL", hashperson.get("EMAIL"));
		jsonperson.put("ADDR", hashperson.get("ADDR"));
		String str_jsonPerson = jsonperson.toJSONString();
		//json객체를 String 형태로 변환한것
		//System.out.println("확인용 : "+str_jsonPerson);
		req.setAttribute("str_jsonPerson", str_jsonPerson);
		super.setViewPage("/ajaxstudy/chap4/1PersonJSON.jsp");
	}
}
