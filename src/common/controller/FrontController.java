package common.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

/**
 * Servlet implementation class FrontController
 */
@WebServlet(
		urlPatterns = { "*.do" }, 
		initParams = { 
				@WebInitParam(name = "propertyConfig", value = "C:/myjsp/MyMVC/WebContent/WEB-INF/Command.properties")
		})
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	HashMap<String,Object> cmdMap = new HashMap<String,Object>();
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		/*
		   웹브라우저 주소창에서 *.do 를 하면  FrontController 서블릿이 받는데 
		   맨 처음에 자동적으로 실행되어지는 메소드가 init(ServletConfig config) 이다.
		   그런데 이 메소드는 WAS(톰캣서버)가 구동되어진 후 딱 1번만 수행되어지고, 그 이후에는 수행되지 않는다. 
		 */
		// 초기화 파라미터 데이터 값인 "C:/myjsp/MyMVC/WebContent/WEB-INF/Command.properties" 을 
		// 가져와서 String props 변수에 저장시켰다.
		
		String props = config.getInitParameter("propertyConfig");
		System.out.println("<<확인용>> : "+props);
		
		Properties pr = new Properties(); // Ű���� �������� ��� �÷�Ʈ
		
		FileInputStream fis =null;
		try {
			fis = new FileInputStream(props); // ������ �о�´�.
			pr.load(fis); 
			/*
			  C:/myjsp/MyMVC/WebContent/WEB-INF/Command.properties 파일을 읽어다가 
			  Properties 클래스의 객체인 pr 에 로드시킨다. 
			  그러면 pr 은 읽어온 파일(Command.properties)의 내용에서
			  = 을 기준으로 왼쪽은 키로 보고, 오른쪽은 value 로 인식한다.
			*/
				
			/*	===========================================
				String str_className = pr.getProperty("/test1.do");
				                                   // "/test1.do" 이 key 이다.
				// *** 확인용 *** //
				System.out.println("<<확인용>> key가 /test1.do인 value => " + str_className);
				// <<확인용>> key가 /test1.do인 value => test.controller.Test1Controller 
				
				Class<?> cls = Class.forName(str_className);
				Object obj = cls.newInstance();
				
				cmdMap.put("/test1.do", obj);
			    ===========================================
			*/
			Enumeration<Object> en = pr.keys(); 
			/*  pr.keys(); 은 
			    C:/myjsp/MyMVC/WebContent/WEB-INF/Command.properties 파일의 내용물에서 
			    = 을 기준으로 왼쪽에 있는 모든 key 들만 읽어오는 것이다.
			 */
			while(en.hasMoreElements()) {
				String key_urlcmd = (String)en.nextElement();
				String className = pr.getProperty(key_urlcmd);
				if(className != null) {
					className = className.trim();
					Class<?> cls = Class.forName(className);
					Object obj = cls.newInstance();
					cmdMap.put(key_urlcmd, obj);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("C:/myjsp/MyMVC/WebContent/WEB-INF/Command.properties�� �����ϴ�.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		requestProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		requestProcess(request, response);
	}
	private void requestProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*Test1Controller action = (Test1Controller)cmdMap.get("/test1.do");
		action.testPrint();*/
		
		// 웹브라우저상의 주소입력창에서 
		// http://localhost:9090/MyMVC/test2.do?name=홍길동&addr=서울 
		// 와 같이 입력되었더라면 
				
		//	String url = request.getRequestURL().toString();
		// url은 http://localhost:9090/MyMVC/test2.do 이다.		
		String uri = request.getRequestURI(); // uri�� /MyMVC/test2.do�̴�.
		String ctxPath = request.getContextPath(); // ctxPath�� /MyMVC �̴�.
		String mapKey = uri.substring(ctxPath.length()); // mapKey�� /test2.do�� ����(Ű��)
		AbstractController action = (AbstractController) cmdMap.get(mapKey);
		if(action==null) {
			System.out.println(mapKey+" url 에 매핑된 데이터가 없습니다.");
			return;
		}
		else {
			try {
				action.excute(request, response);
				String viewPage = action.getViewPage();
				boolean bool = action.isRedirect();
				if(bool) {
					// view���������� sendRedirect ������� �̵���Ų��.
					response.sendRedirect(viewPage);
				}
				else {
					RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
					dispatcher.forward(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}




