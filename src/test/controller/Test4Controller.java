package test.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;

public class Test4Controller extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		req.setAttribute("result", "Test2Controller���� �Ѱ��� ���� <span style='color:red'>�п��� �Ƴ�</span>�Դϴ�");
		req.setAttribute("name", "ȫ��ȭ");
		super.setRedirect(false);
		super.setViewPage("/MyMVC/test/test4.jsp");
		/*
		 	Ȯ���ڰ� .java�� .xml�� ���Ͽ��� view�� �������� ��θ� ��Ÿ���� �Ǿ��� /��
		 	http://localhost:9090/MyMVC/����
		 	Ȯ���ڰ� .html�� .jsp�� ���Ͽ��� view�� �������� ��θ� ��Ÿ���� �Ǿ��� /��
		 	http://localhost:9090/���� ����
		 */
	}
}
