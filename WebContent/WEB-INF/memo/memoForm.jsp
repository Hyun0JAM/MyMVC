<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style type="text/css">
    table#tblMemo, table#tblMemo th {border: 0px solid gray;
	               			 border-collapse: collapse;}
	                   
    table#tblMemo td {border: 0px solid gray;
	              border-collapse: collapse;
	              padding: 10px;}	
	                  
    input {height: 30px;}                                     
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
function goSubmit(){
	var msg = $("#msg").val().trim();
	if(msg == ""){
		alert("메모내용을 입력하세요");
		return;
	}
	var frm = document.memoFrm;
	frm.method="POST";
	frm.action="memoWriteEnd.do";
	frm.submit();
}

</script>
<jsp:include page="../header.jsp" />
<div class="row">
	<div class="col-md-12">
		<form name="memoFrm">
		<table id="tblMemo" style="width: 90%;">
		  <thead>
		  	<tr style="line-height: 80px;">
		  		<th colspan="2" style="text-align: center; font-size: 20pt;">♠♠ 한줄 메모 작성 ♠♠</th>
		  	</tr>
		  </thead>
		  
		  <tbody>
			<tr>	
				<td style="font-weight: bold;">작성자</td>
				<td align="left">
				   <%--  <input type="hidden" name="fk_userid" size="20" value="${(sessionScope.member).userid}" /> --%>					
					<input type="text" size="20" value="${(sessionScope.member).name}" class="box" readonly /> 
				</td>  
			</tr>
			<tr>
				<td style="font-weight: bold;">메모내용</td>
				<td align="left">
					<input type="text" name="msg" id="msg" size="70" maxlength="100" class="box" />
					<button type="button" style="margin-left: 20px;" onClick="goSubmit();" >메모남기기</button>
				</td>	
			</tr>  	
		  </tbody>
		</table>
		</form>
	</div>
</div>
<jsp:include page="../footer.jsp" />