<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../header.jsp" />
<style type="text/css">
  .th, .td {border: 0px solid gray;}
  a:hover {text-decoration: none;}
</style>
<h2>::: 쇼핑몰 상품 :::</h2>    
<br/>
<!-- HIT 상품 디스플레이 하기 -->
<table style="width: 90%; border: 0px solid gray; margin-bottom: 30px;" >
<thead>
	<tr>
		<th colspan="4" class="th" style="font-size: 12pt; background-color: #e1e1d0; height: 30px; text-align:center;">- ${cname} -</th>
	</tr>
	<tr>
		<th colspan="4" class="th">&nbsp;</th>
	</tr>
</thead>
<tbody>
<c:if test="${prodByCategoryList.get(0).pname == null}">
	<tr>
		<td colspan="4" class="td" align="center">현재 상품 준비중....</td>
	</tr>
</c:if>	
<c:if test="${prodByCategoryList.get(0).pname != null}">
	<tr>
		<c:forEach var="prodvo" items="${prodByCategoryList}" varStatus="status" >
		   <%--
		         varStatus 는 반복문의 상태정보를 알려주는 attribute(어트리뷰트) 이다.
		         status.index 는 0부터 시작하여 1씩 증가한다.
		         status.count 는 1부터 시작하여 1씩 증가한다. status.count 이 반복횟수를 알려주는 것이다. 
		   --%>  
		   <td class="td" align="center">
			 <a href="<%= request.getContextPath() %>/prodView.do?pnum=${prodvo.pnum}">
			   <img width="120px;" height="130px;" src="images/${prodvo.pimage1}">
			 </a>
			 <br/>
			 <br/><span style="background-color: navy; color: white;">${prodvo.pspec}</span> 
			 <br/>${prodvo.pname}
			 <br/><span style="text-decoration: line-through;"><fmt:formatNumber value="${prodvo.price}" pattern="###,###" />원</span>
			 <br/><span style="color: red; font-weight: bold;"><fmt:formatNumber value="${prodvo.saleprice}" pattern="###,###" />원</span>
			 <br/><span style="color: blue; font-weight: bold;">[${prodvo.percent}% 할인]</span>
			 <br/><span style="color: orange;">${prodvo.point} POINT</span>
		   </td> 
		   <c:if test="${ (status.count)%4 == 0 }">
		      </tr>
		        <tr><td colspan="4" style="line-height: 30px;">&nbsp;</td></tr>
		      <tr>
		   </c:if>
		</c:forEach>
	</tr>
</c:if>
</tbody>
</table>
<jsp:include page="../footer.jsp" />
