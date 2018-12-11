package memo.controller;

import java.util.*;
import javax.servlet.http.*;
import common.controller.AbstractController;
import member.model.MemberVO;
import memo.model.MemoDAO;
import memo.model.MemoVO;
import my.util.MyUtil;

public class MemoListAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		MemberVO loginuser = super.getLoginUser(req);
		if(loginuser == null) return;
		MemoDAO memodao = new MemoDAO();
		
		String str_sizePerPage = req.getParameter("sizePerPage");
		String str_currentShowPageNo = req.getParameter("currentShowPageNo");
		
		if(str_sizePerPage == null) str_sizePerPage = "5";// �ʱ�ȭ��
		int sizePerPage = 0;
		try {
			 sizePerPage = Integer.parseInt(str_sizePerPage);
			 if(sizePerPage != 3 && sizePerPage != 5 && 
			    sizePerPage != 10) { 
				 sizePerPage = 5;
			 }
		} catch (NumberFormatException e) {
			 sizePerPage = 5;
		}

		int totalCount = memodao.getTotalCount();
		int totalPage = (int)Math.ceil((double)totalCount/sizePerPage); 
		int currentShowPageNo = 0; // ����ڰ� ������ �ϴ� ��������ȣ
		
		if(str_currentShowPageNo == null) currentShowPageNo = 1;// �ʱ�ȭ���� ���������� ��ȣ�� 1�������� �����Ѵ�.
		else { // ����ڰ� ������ �ϴ� ��������ȣ�� ������ ��� 
			try {
				 currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
				 if(currentShowPageNo < 1 || currentShowPageNo > totalPage) currentShowPageNo = 1;
			} catch (NumberFormatException e) {
				 currentShowPageNo = 1;
			}
		}
		String url = "memoList.do"; 
		int blockSize=10;
		String pageBar = MyUtil.getSearchPageBar(url, currentShowPageNo, sizePerPage, totalPage, blockSize, "", "", 0);
		List<MemoVO> memoList = memodao.getAllMemo(sizePerPage,currentShowPageNo);
		
		req.setAttribute("currentShowPageNo", currentShowPageNo);
		req.setAttribute("sizePerPage", sizePerPage);
		
		req.setAttribute("pageBar", pageBar);
		req.setAttribute("memoList", memoList);
		//super.setViewPage("/WEB-INF/memo/memoList.jsp");
		super.setViewPage("/WEB-INF/memo/memoListJSLT.jsp");
	}
}
