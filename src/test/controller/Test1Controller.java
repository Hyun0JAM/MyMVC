package test.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;

public class Test1Controller extends AbstractController{
	public void testPrint() {
		System.out.println("����� Test1Controller�� testPrint() �޼ҵ� �Դϴ�.");	
	}

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		req.setAttribute("result", "Test1Controller���� �Ѱ��� ���� <span style='color:red'>�����ֶ��� ��������</span>�Դϴ�");
		req.setAttribute("name", "�̼���");
		super.setRedirect(false);
		super.setViewPage("/test/test1.jsp");
		/*
		 	Ȯ���ڰ� .java�� .xml�� ���Ͽ��� view�� �������� ��θ� ��Ÿ���� �Ǿ��� /��
		 	http://localhost:9090/MyMVC/����
		 	Ȯ���ڰ� .html�� .jsp�� ���Ͽ��� view�� �������� ��θ� ��Ÿ���� �Ǿ��� /��
		 	http://localhost:9090/���� ����
		 */
	}
}
