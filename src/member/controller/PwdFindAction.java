package member.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.MemberDAO;

public class PwdFindAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) 
		throws Exception {
		String method = req.getMethod();
		// GET 또는 POST
		
		if("POST".equalsIgnoreCase(method)) {
			// 비밀번호 찾기 모달창에서 찾기 버튼을 클릭했을 경우 
			
			String userid = req.getParameter("userid");
			String email = req.getParameter("email");
			
			MemberDAO memberdao = new MemberDAO();
			
			int n = memberdao.isUserExisets(userid,email);
			if(n==1) { // 회원으로 존재하는경우
				GoogleMail mailsend = new GoogleMail();
				//인증키를 랜덤하도록 생성하도록한다.
				Random rnd = new Random();
				String certificationcode = "";
				
				char randchar = ' ';
				for(int i=0;i<5;i++) {
					// min부터 max사이의 값으로 랜덤한 정수를 얻으려면
					// int rndnum = rnd.nextInt(max-main+1);
					// 영문 소문자 a부터 z 까지 중 랜덤하게 1개를 만든다.
					randchar = (char)(rnd.nextInt('z'-'a'+1)+'a');
					certificationcode += randchar;
				}
				int randnum = 0;
				for(int i=0;i<7;i++) {
					randnum = rnd.nextInt(10);
					certificationcode += randnum;
				}
				// 랜덤하게 생성한 인증코드를 비밀번호 찾기를 하고자하는 사용자의 이메일로 전송시킨다.
				try {
					mailsend.sendmail(email,certificationcode);
					//req.setAttribute("certificationcode", certificationcode);
					HttpSession session = req.getSession();
					session.setAttribute("certificationcode", certificationcode);
					//자바에서 발급한 인증코드를 세션에 저장
				} catch (Exception e) {
					e.printStackTrace();
					n=-1;
					req.setAttribute("sendFailmsg", "메일 전송에 실패했습니다.");
				}
			}
			req.setAttribute("n", n);
			/* n이 0이면 존재하지 않는 userid 또는 email인 경우
			 * n이 1이면 userid와 email이 존재하면서 메일이 정상적으로 전송된 경우
			 */
			req.setAttribute("userid", userid);
			req.setAttribute("email", email);
		}	
		req.setAttribute("method", method);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/login/pwdFind.jsp");
		
	}// end of void execute(HttpServletRequest req, HttpServletResponse res)--------------- 

}
