<%@page import="my.util.MyKey"%>
<%@page import="jdbc.util.AES256"%>
<%@page import="member.model.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	MemberVO loginuser = (MemberVO)session.getAttribute("member");
%>
<style type="text/css">
table#loginTbl{
	width: 96%;
	border: 1px solid gray;
	border-collapse:collapse;
}
#th{
	background-color: silver;
}
table#loginTbl td{
	border: 1px solid gray;
}
td{
	height:20px;
}
table{
	border:1px solid gray;
	width: 95%;
	height:130px;
	margin:0 auto;
}
#logoutbtn{
	border: 1px solid gray;
	background-color: lightgray;
	border-radius: 3px;
	color:black;
	font-weight:bold;
	font-size:12px;
	padding: 5px 30px;
	margin-bottom: 2%;
}
</style>
<script type="text/javascript">
	$(document).ready(function(){
		$("#btnSubmit").click(function(){
			goLogin();
		});
		$("#loginPwd").keydown(function(event){
			if(event.keyCode==13){ //엔터를 했을경우
				goLogin();
			}
		});
	});
	
	function goLogin(){
		var loginUserid = $("#loginUserid").val().trim();
		var loginPwd = $("#loginPwd").val().trim();
		
		if(loginUserid==""){
			alert("아이디를 입력하세요");
			$("#loginUserid").val("");
			$("#loginUserid").focus();
			return;
		}
		else if(loginPwd==""){
			alert("패스워드를 입력하세요");
			$("#loginPwd").val("");
			$("#loginPwd").focus();
			return;
		}
		var frm = document.loginFrm;
		frm.method="POST";
		frm.action="loginEnd.do";
		frm.submit();
	}
	function goLogout(){
		location.href = "<%=request.getContextPath()%>/logout.do";
		alert("로그아웃 되었습니다.");
	}
	function goEditPersonal(idx){
		var frm = document.loginedFrm;
		window.open("memberEdit.do","memberEdit","left=500px,top=100px,width=1000px,height=600px");
		frm.target = "memberEdit";
		frm.submit();
		self.close();
	}
	// === PaymentGateWqy(결제)관련 ===
	function goCoinPurchaseTypeChoice(idx){
		//코인구매 선택 팝업창 띄우기
		var url = "coinPurchaseTypeChoice.do?idx="+idx;
		window.open(url,"coinPurchaseTypeChoice","left=350px,top=100px,width=650px,height=470px");
	}
	function goCoinPurchaseEnd(idx, coinmoney){
		//alert("idx : "+idx+", 충전금액 : "+coinmoney);
		// 아임포트 결제창 띄우기
		var url = "coinPurchaseTypeChoiceEnd.do?idx="+idx+"&coinmoney="+coinmoney;
		window.open(url,"coinPurchaseTypeChoice","left=350px,top=100px,width=820px,height=600px");
	}
	function goCoinUpdate(idx,coinmoney){
		var frm = document.coinUpdateFrm;
		frm.idx.value = idx;
		frm.action="<%=request.getContextPath()%>/coinAddUpdate.do";
		frm.method="POST";
		frm.submit();
	}
</script>
<!-- 로그인 하기 이전 -->
<%
	/*
	로그인 하려고 할때 WAS(톰캣서버)는 
	사용자 컴퓨터 웹브라우저로 부터 전송받은 쿠키를 검사해서
	그 쿠키의 사용유효시간이 0초 짜리가 아니라면
	그 쿠키를 가져와서 웹브라우저에 적용시키도록 해준다.
	우리는 쿠키의 키 값이 "saveid" 가 있으면
	로그인 ID 텍스트박스에 아이디 값을 자동적으로 올려주면 된다.
	 
	쿠키는 쿠키의 이름별로 여러개 저장되어 있으므로
	쿠키를 가져올때는 배열타입으로 가져와서
	가져온 쿠키배열에서 개발자가 원하는 쿠키의 이름과 일치하는것을
	뽑기 위해서는 쿠키 이름을 하나하나씩 비교하는 것 밖에 없다.
	*/
	if(loginuser==null){ 
		Cookie[] cookieArr = request.getCookies();
		String cookieKey ="";
		String cookieValue="";
		if(cookieArr!=null){// 클라이언트 컴퓨터에서 보내온 쿠키가 있을경우
			for(Cookie cook : cookieArr){
				cookieKey = cook.getName();
				if("saveid".equals(cookieKey)){
					cookieValue = cook.getValue();
					break;
				}
			}
		}
		else{cookieValue="";}
%>
<form name="loginFrm">
   <table id="loginTbl" style="margin: 0 auto; margin-top:3%;">
      <thead>
         <tr>
            <th colspan="2" id="th" style="text-align: center;">LOGIN</th>
         </tr>
      </thead>
      <tbody>
         <tr>
            <td style="width:30%; border-bottom:hidden; border-right:hidden; padding:10px;">ID</td>
            <td style="width:70%; border-bottom:hidden; border-left:hidden; padding:10px;">
            	<input type="text" id="loginUserid" name="userid" size="13" class="box" value="<%=cookieValue %>" />
            </td>
         </tr>
         
         <tr>
            <td style="width:30%; border-bottom:hidden; border-right:hidden; padding:10px;">암호</td>
            <td style="width:70%; border-bottom:hidden; border-left:hidden; padding:10px;">
            	<input type="password" id="loginPwd" name="pwd" size="13" class="box" />
            </td>
         </tr>
         <%-- === 아이디 찾기 , 비밀번호 찾기 === --%>
		<tr>
			<td colspan="2" align="center">
				<a style="cursor: pointer;" data-toggle="modal" data-target="#userIdfind" data-dismiss="modal">아이디찾기</a> /
				<a style="cursor: pointer;" data-toggle="modal" data-target="#passwdFind" data-dismiss="modal">비밀번호찾기</a> 
			</td>
		</tr>
         <tr>
         	<td colspan="2" align="center" style="padding:10px;">
         		<%if("".equals(cookieValue)){ %>
         		<input type="checkbox" id="saveid" name="saveid" style="vertical-align: middle;"><label for="saveid" style="vertical-align: middle; margin-right:20px;">아이디저장</label>
         		<%}else{ %>
         		<input type="checkbox" id="saveid" name="saveid" style="vertical-align: middle;" checked><label for="saveid" style="vertical-align: middle; margin-right:20px;">아이디저장</label>
         		<%} %>
         		<button type="button" id="btnSubmit" style="width:67px; height:27px; background-image:url('<%=request.getContextPath()%>/images/login.png'); vertical-align: middle; border:none;"></button>
         	</td>
         </tr>
      </tbody>
   </table>
</form>
<% } else{ %>
<form name="loginedFrm" method="POST" >
	<table>
		<tr style="text-align:center;">
			<td colspan="2" style="background-color:silver; border-top: 1px solid gray;">
				<span style="color: black; font-weight: bold;">${(sessionScope.member).name}</span>
			  	  [<span style="color: red; font-weight: bold;">${(sessionScope.member).userid}</span>]님
			  	  <br/><br/>
			  	  <input type="hidden" name="idx" id="idx" value="${(sessionScope.member).idx}" />
			  	  <input type="hidden" name="bool" id="bool" value="true" />
			  	  <div align="left" style="padding-left: 20px; line-height: 150%;">
			  	      <span style="font-weight: bold;">코인액 :</span>&nbsp;&nbsp;<fmt:formatNumber value="${(sessionScope.member).coin}" pattern="###,###" /> 원
			  		  <br/>   
			  	      <span style="font-weight: bold;">포인트 :</span>&nbsp;&nbsp;<fmt:formatNumber value="${(sessionScope.member).point}" pattern="###,###" /> POINT
			  	  </div>
			  	  <br/>로그인 중...<br/><br/>
			  	  [<a href="javascript:goEditPersonal('${(sessionScope.member).idx}');">나의정보</a>]&nbsp;&nbsp;
			  	  [<a href="javascript:goCoinPurchaseTypeChoice('${(sessionScope.member).idx}');">코인충전</a>] 
			  	  <br/><br/>
			  	  <button type="button" id="logoutbtn" onClick="goLogout();">로그아웃</button> 
			</td>
		</tr>
	</table>
</form>
<%} %>
 <%-- ****** 아이디 찾기 Modal ****** --%>
  <div class="modal fade" id="userIdfind" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close myclose" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">아이디 찾기</h4>
        </div>
        <div class="modal-body" style="height: 300px; width: 100%;">
          <div id="idFind">
          	<iframe style="border: none; width: 100%; height: 280px;" src="<%= request.getContextPath() %>/idFind.do">
          	</iframe>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default myclose" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>   


  <%-- ****** 비밀번호 찾기 Modal ****** --%>
  <div class="modal fade" id="passwdFind" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close myclose" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">비밀번호 찾기</h4>
        </div>
        <div class="modal-body">
          <div id="pwFind">
          	<iframe style="border: none; width: 100%; height: 350px;" src="<%= request.getContextPath() %>/pwdFind.do">  
          	</iframe>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default myclose" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
  
  <%-- PG에 코인금액을 카드로 결제 후 DB상의 사용자의 코인액을 변경해주는 폼 --%>
<form name="coinUpdateFrm">
  	<input type="hidden" name="idx">
</form>
