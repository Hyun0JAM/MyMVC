<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String idx = (String)request.getAttribute("str_idx");
%>

<jsp:include page="../header.jsp" />
    
<div class="row">
	<div class="col-md-12" style="margin-top:50px''">
    <span style="color: blue;">${str_pnum}</span>상품이 존재하지 않습니다.<br/>
         상품번호가 올바른지 확인하시고 다시 검색해 주세요. 
	</div>
</div>    
    
<jsp:include page="../footer.jsp" />
    