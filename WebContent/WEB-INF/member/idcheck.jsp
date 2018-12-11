<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String method = request.getMethod();
	String ctxPath = request.getContextPath();
%>
<jsp:useBean id="memberdao" class="member.model.MemberDAO" scope="application" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중복 ID 검사하기</title>
<link rel="stylesheet" type="text/css" href="/MyWeb/css/style.css" />
<script type="text/javascript" src="/MyWeb/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#error").hide();
});
	function goCheck(){
		var userid = $("#userid").val().trim();
		if(userid==""){
			$("#error").show();
			$("#userid").val("");
			$("#userid").focus();
			return;
		}
		else {
			$("#error").hide();
			var frm = document.frmIdcheck;
			frm.method="POST";
			frm.action="idDuplicateCheck.do";
			frm.submit();
		}
	}
	function setUserID(userid){ 
		$(opener.document).find("#userid").val(userid);
		$("#pwd",opener.document).focus();
		self.close();
	}
</script>
</head>
<body>
	<c:if test="${requestScope.method!='POST'}">
	<span style="font-size: 10pt; font-weight: bold;"><%= method %></span>   
    <form name="frmIdcheck">
    	<table style="width: 95%; height: 100px;">
	  		<tr>
	    		<td style="text-align:center">
	    			아이디를 입력하세요<br style="line-height:300%"/>
		        	<input type="text" id="userid" name="userid" size="20" /><br/>
		            <span id="error" style="color: red; font-size: 12pt; font-weight: bold;">아이디를 입력하세요!!!</span><br/>
		            <!-- 확인버튼을 누르면 input태그의 name  -->
		            <button type="button" onClick="goCheck();" style="border:none; padding: 5px 10px;">확인</button>
	            </td>
	        </tr>
        </table>
    </form>
    </c:if>
	<c:if test="${requestScope.method=='POST' && requestScope.bool=='true'}">
    <div align="center">
    	<br style="line-height: 350%;"/>
    	ID로[<span style="color: red; font-weight: bold;"><%=request.getAttribute("userid") %></span>]은 사용가능합니다.
        <br/><br/><br/>
        <button type="button" onClick="setUserID('<%=request.getAttribute("userid") %>');">닫기</button>
    </div>
    </c:if>
	<c:if test="${requestScope.method=='POST'&& requestScope.bool=='false'}">
    	<!-- userid가 중복이므로 사용불가한 아이디라고 알려줘야한다. -->
    	<div align="center">
    		<br style="line-height: 350%;"/>
	     	ID로[<span style="color: red; font-weight: bold;"><%=request.getAttribute("userid") %></span>]는 이미 사용중 입니다.
	        <br/>
	        <form name="frmIdcheck">
		    	<table style="width: 95%; height: 100px;">
			  		<tr>
			    		<td style="text-align:center">
			    			아이디를 입력하세요<br style="line-height:300%"/>
				        	<input type="text" id="userid" name="userid" size="20" /><br/>
				            <span id="error" style="color: red; font-size: 12pt; font-weight: bold;">아이디를 입력하세요!!!</span><br/>
				            <!-- 확인버튼을 누르면 input태그의 name  -->
				            <button type="button" onClick="goCheck();" style="border:none">확인</button>
			            </td>
			        </tr>
		        </table>
		    </form>
    	</div>
    	</c:if>
</body>
</html>