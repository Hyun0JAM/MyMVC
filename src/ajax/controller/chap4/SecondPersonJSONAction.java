package ajax.controller.chap4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import common.controller.AbstractController;

public class SecondPersonJSONAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HashMap<String,Object> hashperson1 = new HashMap<String,Object>();
		hashperson1.put("NAME", "이순신");
		hashperson1.put("AGE", 27);
		hashperson1.put("PHONE", "010-345-6789");
		hashperson1.put("EMAIL", "leess@navar.com");
		hashperson1.put("ADDR", "서울시 중구 남대문로 24");
		HashMap<String,Object> hashperson2 = new HashMap<String,Object>();
		hashperson2.put("NAME", "이순신");
		hashperson2.put("AGE", 40);
		hashperson2.put("PHONE", "010-123-4567");
		hashperson2.put("EMAIL", "leess@navar.com");
		hashperson2.put("ADDR", "서울시 중구 남대문로 24");
		HashMap<String,Object> hashperson3 = new HashMap<String,Object>();
		hashperson3.put("NAME", "엄정화");
		hashperson3.put("AGE", 25);
		hashperson3.put("PHONE", "010-987-6543");
		hashperson3.put("EMAIL", "eom@navar.com");
		hashperson3.put("ADDR", "서울시 강북구 수유동 123");
		List<HashMap<String,Object>> hashList = new ArrayList<HashMap<String,Object>>();
		hashList.add(hashperson1);
		hashList.add(hashperson2);
		hashList.add(hashperson3);
		JSONArray jsonArr = new JSONArray(); // List와 비슷
		for(HashMap<String,Object> map:hashList) {
			JSONObject jsonperson = new JSONObject();
			jsonperson.put("NAME", map.get("NAME"));
			jsonperson.put("AGE", map.get("AGE"));
			jsonperson.put("PHONE", map.get("PHONE"));
			jsonperson.put("EMAIL", map.get("EMAIL"));
			jsonperson.put("ADDR", map.get("ADDR"));
			jsonArr.add(jsonperson);
		}
		//json객체를 String 형태로 변환한것
		System.out.println("확인용 : "+jsonArr.toString());
		//확인용 : [{"PHONE":"010-345-6789","EMAIL":"leess@navar.com","NAME":"이순신","AGE":27,"ADDR":"서울시 중구 남대문로 24"},{"PHONE":"010-123-4567","EMAIL":"leess@navar.com","NAME":"이순신","AGE":40,"ADDR":"서울시 중구 남대문로 24"},{"PHONE":"010-987-6543","EMAIL":"eom@navar.com","NAME":"엄정화","AGE":25,"ADDR":"서울시 강북구 수유동 123"}]
		req.setAttribute("jsonArr", jsonArr.toString());
		super.setViewPage("/ajaxstudy/chap4/2PersonArrJSON.jsp");
	}

}
