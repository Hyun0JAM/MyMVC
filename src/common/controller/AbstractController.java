package common.controller;

import java.util.List;
import java.sql.SQLException;
import javax.servlet.http.*;
import member.model.MemberVO;
import myshop.model.CategoryVO;
import myshop.model.ProductDAO;

public abstract class AbstractController implements Command {
	//--------------------------------------------------------
	/* �̰��� �츮������ ����̴�.
	 * view�� ������(.jsp ������)�� �̵��� sendRedirect ������� �̵���Ű���� �Ѵٶ�� isRedirect�������� ture�� �Ѵ�.
	 * view�� ������(.jsp ������)�� �̵��� forward ������� �̵���Ű���� �Ѵٶ�� isRedirect�������� false�� �Ѵ�.
	 * */
	/* sendRedirect��� : �ּҰ��� ����ǰ� ĳ�� ����� ����.
	 * forward��� : �����͸� �ѱ� �� �ִ�. url���� �Ⱥ���
	 * */
	private boolean isRedirect =false;
	private String viewPage; // VIEW�� �������� ��θ����� ���Ǿ����� �����̴�.
	//--------------------------------------------------------
	
	public boolean isRedirect() {
		return isRedirect;
	} // ���� Ÿ���� booleanŸ���̶�� get�̾ƴ϶� is�� ��Ÿ����.

	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}

	public String getViewPage() {
		return viewPage;
	}

	public void setViewPage(String viewPage) {
		this.viewPage = viewPage;
	}
	public MemberVO getLoginUser(HttpServletRequest req) {
    	MemberVO loginuser = null; 
    	// 세션에 저장된 값을 얻어온다.
    	HttpSession session = req.getSession();
    	loginuser = (MemberVO)session.getAttribute("member");
    	if(loginuser == null) {
    		String msg = "먼저 로그인 하세요!!";
			String loc = "javascript:history.back();";
			req.setAttribute("msg", msg);
			req.setAttribute("loc", loc);
			isRedirect = false;
			viewPage = "/WEB-INF/msg.jsp";
    	}
    	return loginuser;
    }// end of public static HashMap<String, Object> checkLoginUser(HttpServletRequest req)----------------
	

	public void getCategoryList(HttpServletRequest req) throws SQLException {		
	    // jsp_category 테이블에서
	    // 카테고리코드(code)와 카테고리명(cname)을 가져와서
	    // request 영역에 저장시킨다.
		req.removeAttribute("categoryList");
	    ProductDAO pdao = new ProductDAO();
	    List<CategoryVO> categoryList = pdao.getCategoryList(); 
	    
	    req.setAttribute("categoryList", categoryList);
	}
}
