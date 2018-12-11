package member.controller;

import java.util.List;
import javax.servlet.http.*;
import common.controller.AbstractController;
import member.model.MemberDAO;
import member.model.MemberVO;
import my.util.MyUtil;

public class MemberListAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		MemberVO loginuser = super.getLoginUser(req);
		if(loginuser == null) return;
		// 1. MemberDAO 객체 생성
		MemberDAO memberdao = new MemberDAO();
		
		// 2. 검색어 및 날짜구간 받아서 검색해주기
		//    페이징 처리를 위해 페이지당 보여줄 sizePerPage 받아오기 
		String searchType = req.getParameter("searchType");
		String searchWord = req.getParameter("searchWord");
		String period = req.getParameter("period");
		String str_sizePerPage = req.getParameter("sizePerPage");
		
		// ==== 초기화면 설정 값 정하기 ==== //
		if(searchType == null) searchType = "name"; 
		if(searchWord == null) searchWord = ""; 
		if(period == null) period = "-1"; 
		// GET 방식으로 넘어오는 경우이므로 사용자가 장난치는 경우를 대비해야함.
		if(!"name".equals(searchType) &&
		   !"userid".equals(searchType) &&
		   !"email".equals(searchType) ) {
			searchType = "name";
		}
		if(!"-1".equals(period) && !"3".equals(period) &&
		      !"10".equals(period) && !"30".equals(period) && 
		      !"60".equals(period) ) { period = "-1"; }
		if(str_sizePerPage == null) str_sizePerPage = "5";// 초기화면
		int sizePerPage = 0;
		
		try {
			 sizePerPage = Integer.parseInt(str_sizePerPage);
			 if(sizePerPage != 3 && sizePerPage != 5 && 
			    sizePerPage != 10) { sizePerPage = 5;}
		} catch (NumberFormatException e) {
			 sizePerPage = 5;
		}
		// 3. 전체 페이지 갯수 알아오기 
		// -- 총 회원갯수를 알아오기 
		int totalMemberCount = 0;
		if("admin".equals(loginuser.getUserid())) totalMemberCount = memberdao.getAdminTotalCount(searchType, searchWord, Integer.parseInt(period)); 
		else totalMemberCount = memberdao.getTotalCount(searchType, searchWord, Integer.parseInt(period)); 
		
		// -- 총 페이지수 구하기
		int totalPage = (int)Math.ceil((double)totalMemberCount/sizePerPage); 
		/* 57/10 ==> 5.7  ==> 6.0   ==> 6
		   57/5  ==> 11.4 ==> 12.0  ==> 12
		   57/3  ==> 19   ==> 19.0  ==> 19*/
		
		String str_currentShowPageNo = req.getParameter("currentShowPageNo");
		int currentShowPageNo = 0; // 사용자가 보고자 하는 페이지번호
		
		if(str_currentShowPageNo == null) currentShowPageNo = 1;// 초기화면은 현재페이지 번호로 1페이지로 설정한다.
		else {// 사용자가 보고자 하는 페이지번호를 설정한 경우 
			try {
				 currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
				 if(currentShowPageNo < 1 || currentShowPageNo > totalPage) currentShowPageNo = 1;
			} catch (NumberFormatException e) {
				 currentShowPageNo = 1;
			}
		}
		// 4. 검색조건에 맞는 회원정보를 jsp_member 테이블에서
		//    select 한 결과물을 가져와서 List 타입의 변수에 담아둔다.
		List<MemberVO> memberList = null;
		
		if("admin".equals(loginuser.getUserid())) 
			memberList = memberdao.getAllMemberAdmin(sizePerPage, currentShowPageNo, searchType, searchWord, Integer.parseInt(period) );
		else 
			memberList = memberdao.getAllMember(sizePerPage, currentShowPageNo, searchType, searchWord, Integer.parseInt(period) );
		
		// 5. 페이징 처리된 페이지바를 생성하여 뷰단으로 넘긴다.
		String url = "memberList.do";
		int blockSize=10;
		String pageBar = MyUtil.getSearchPageBar(url,currentShowPageNo,sizePerPage,totalPage,blockSize,searchType, searchWord, Integer.parseInt(period));
		
		// 6. 지금까지 작성한 데이터 값들을 view단으로 넘긴다.
		req.setAttribute("searchType", searchType);
		req.setAttribute("searchWord", searchWord);
		req.setAttribute("period", period);
		
		req.setAttribute("sizePerPage", sizePerPage);
		req.setAttribute("currentShowPageNo", currentShowPageNo);
		req.setAttribute("totalMemberCount", totalMemberCount);
		req.setAttribute("totalPage", totalPage);
		req.setAttribute("memberList", memberList);
		
		req.setAttribute("pageBar", pageBar);
		//req.setAttribute("currentURL", arg1);	
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/member/memberList.jsp");
	}
}
