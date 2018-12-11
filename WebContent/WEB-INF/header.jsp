<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String ctxPath = request.getContextPath();
    //    /MyWeb
%>
<!DOCTYPE html>
<html>
<head>
<title>:::HOMEPAGE:::</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/jquery-ui-1.11.4.custom/jquery-ui.min.css" /> 
<script type="text/javascript" src="<%= request.getContextPath() %>/jquery-ui-1.11.4.custom/jquery-ui.min.js"></script> 
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%= ctxPath %>/css/style.css" />
</head>
<body>

<div id="mycontainer">

	<div id="headerImg">
		<div class="row">
			<div class="col-md-1">
				<a href="http://www.samsung.com">
				<img src="<%= ctxPath %>/images/logo1.png"/></a>
			</div>
			<div class="col-md-2">
				<img src="<%= ctxPath %>/images/logo2.png"/>
			</div>
		</div>
	</div>
	
	<div id="headerLink">
		<div class="row">
			<div class="col-md-2">
				<a href="<%= ctxPath %>/index.do">HOME</a>
			</div>
			<div class="col-md-2">
				<a href="<%= ctxPath %>/memberRegister.do">회원가입</a>
			</div>
			<c:if test="${sessionScope.member!=null}"> <%-- 세션 영역에 저장된 데이터를 가져오는 방법 --%>
			<div class="col-md-2">
				<a href="<%= ctxPath %>/memberList.do">회원목록</a>
			</div>
			<div class="col-md-2">
				<a href="<%= ctxPath %>/memo.do">한줄메모쓰기</a>
			</div>
			<div class="col-md-2">
				<a href="<%= ctxPath %>/memoList.do">메모조회(HashMap)</a>
			</div>
			<div class="col-md-2">
				<a href="<%= ctxPath %>/myMemoList.do">나의메모조회(VO)</a>
			</div>
			</c:if>
			<div class ="col-md-2">
		     	<a href="<%= ctxPath %>/mallHome.do"> 쇼핑몰홈</a>   	
			</div>
			<c:if test="${sessionScope.member != null && (sessionScope.member).userid == 'admin'}"> 
            <div class ="col-md-2">
                    <a href="<%= ctxPath %>/admin/productRegister.do"> 제품등록</a>   
            </div>
         	</c:if>
         	<c:if test="${sessionScope.member != null}">
         	<div class="col-md-2">
				<a href="<%= ctxPath %>/myCartList.do">장바구니</a>
			</div>
			<div class="col-md-2">
				<a href="<%= ctxPath %>/orderList.do">주문조회</a>
			</div>
			</c:if>
			<div class="col-md-2">
				<a data-toggle="modal" data-target="searchStore" data-dismiss="modal">매장찾기</a>
				<%-- searchStore 에 대한 내용은 footer.jsp에 있음 --%>
			</div>
			<div class="col-md-1">
			   <a href="<%= ctxPath%>/sessionTest.do">세션테스트</a>
			</div>
		</div>
	</div>
	
	<div id="sideinfo">
      <div class="row">
         <div class="col-md-12" style="height: 50px; text-align: left; padding: 0px 20px; margin-top: 20px;">
            <%@ include file="/WEB-INF/login/login.jsp" %>
         </div>
      </div>
      <!-- <div class="row">
         <div class="col-md-12" id="sideconent" style="text-align: left; padding: 20px;">
         </div>
      </div>  -->  
      <div class="row">
			<div class="col-md-12" style="height: 50px; text-align: left; padding: 20px; margin-left:10px; margin-top: 250px; ">
				<%@ include file="/WEB-INF/myshop/categoryList.jsp" %>
			</div>
		</div>	
   </div>
	
	<div id="content" align="center">