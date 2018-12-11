package test.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;

public class Test2Controller extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		req.setAttribute("result", "Test2Controller에서 넘겨준 값은 <span style='color:red'>훈민정음 창시자</span>입니다");
		req.setAttribute("name", "세종대왕");
		super.setRedirect(false);
		super.setViewPage("/test/test1.jsp");
		/*
		 	확장자가 .java와 .xml인 파일에서 view단 페이지의 경로를 나타낼대 맨앞의 /는
		 	http://localhost:9090/MyMVC/까지
		 	확장자가 .html와 .jsp인 파일에서 view단 페이지의 경로를 나타낼대 맨앞의 /는
		 	http://localhost:9090/까지 생략
		 */
	}
	
}
