<%@page import="member.model.MemberVO"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
List<HashMap<String,String>> memolist = (List<HashMap<String,String>>)request.getAttribute("memolist");
%>
<jsp:include page="../header.jsp" />
	<div style="margin-top:3%">
	<table class="outline">
		<thead>
			<tr>
				<th width="10%" style="text-align:center;">글번호</th>
				<th width="10%" style="text-align:center;">작성자</th>
				<th width="50%" style="text-align:center;">내용</th>
				<th width="20%" style="text-align:center;">작성일자</th>
				<th width="10%" style="text-align:center;">ip Address</th>
			</tr>
		</thead>
		<tbody>
			<%
			if(memolist==null){
				%>
				<tr>
					<td colspan="5">작성된 글이 없습니다.</td>
				</tr><%
			}else{
				for(HashMap<String,String> map :memolist){ %>
			<tr>
				<td style="text-align:center;"><%=map.get("idx") %></td>
				<td style="text-align:center;"><%=map.get("name") %></td>
				<td style="text-align:center;"><%=map.get("msg").replaceAll("<", "&lt;") %></td>
				<%-- HTML 에서 &nbsp; 공백     &lt; 부등호(<)  &gt; 부등호(>)    &amp; &   &quot;  " 이다.  --%>
				<td style="text-align:center;"><%=map.get("writedate") %></td>
				<td style="text-align:center;"><%=map.get("cip") %></td>
			</tr>
			<%}  }%>
		</tbody>
	</table>
	</div>
<jsp:include page="../footer.jsp" />