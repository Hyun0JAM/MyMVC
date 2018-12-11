<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.*" %> 
<jsp:include page="../header.jsp" />
<script type="text/javascript">
$(document).ready(function(){
	$("#sizePerPage").bind("change",function(){
		var frm = document.sizePerPageFrm;
		frm.method="GET";
		frm.action="memoList.do";
		frm.submit();
	});
	$("#sizePerPage").val("${sizePerPage}");
	// 전체선택 체크시 모두체크,모두해제
	$("#allcheck").click(function(){
		$(".check").prop('checked',$(this).is(':checked'));
	});
});

function msgDel(){
	var flag = false;
	$(".check").each(function(){
		if($(this).is(':checked')==true){
			flag = true;
			return false;
		}
	});
	if(flag==false){
		alert("삭제할 글번호를 선택하세요");
		return;
	}
	else{
		var bool = confirm("선택한 글 번호를 정말 삭제 하시겠습니까?");
		if(bool){
			var frm = document.checkFrm;
			frm.method="POST";
			frm.action="memoDelte.do";
			frm.submit();
		}
		else {
			$(".check").prop('checked',false);
			alert("삭제를 취소 하셨습니다.");
		}
	}
}
function msgBlind(){
	var flag = false;
	/* for문에서의 continue; 와 동일한 기능을 하는것이 
	   each(); 내에서는 return true; 이고,
	   for문에서의 break; 와 동일한 기능을 하는것이 
	   each(); 내에서는 return false; 이다.*/
	$(".check").each(function(){
		if($(this).is(':checked')==true){
			flag = true;
			return false;
		}
	});
	if(flag==false){
		alert("비공개할 글번호를 선택하세요");
		return;
	}
	else{
		var bool = confirm("선택한 글 번호를 정말 비공개로 전환 하시겠습니까?");
		if(bool){
			var frm = document.checkFrm;
			frm.method="POST";
			frm.action="memoBlind.do";
			frm.submit();
		}
		else {
			$(".check").prop('checked',false);
			alert("비공개 전환을 취소 하셨습니다.");
		}
	}
}
function msgOpen(){
	var flag = false;
	$(".check").each(function(){
		if($(this).is(':checked')==true){
			flag = true;
			return false;
		}
	});
	if(flag==false){
		alert("공개할 글번호를 선택하세요");
		return;
	}
	else{
		var bool = confirm("선택한 글 번호를 정말 공개로 전환 하시겠습니까?");
		if(bool){
			var frm = document.checkFrm;
			frm.method="POST";
			frm.action="memoOpen.do";
			frm.submit();
		}
		else {
			$(".check").prop('checked',false);
			alert("공개 전환을 취소 하셨습니다.");
		}
	}
}
</script>
<div class="row">
   	<div class="col-md-12">
   	   <h2>::: 나의 한줄 메모장 목록 :::</h2>
   	</div>
   </div>
   
   <div class="row" style="margin-top: 20px; margin-bottom: 20px;">
	   <div class="col-md-5" style="border: 0px solid gray;">
	       <button type="button" class="memobtn" onClick="msgDel();">메모 삭제</button>&nbsp;
	       <button type="button" class="memobtn" onClick="msgBlind();">메모 비공개</button>&nbsp;
	       <button type="button" class="memobtn" onClick="msgOpen();">메모 공개</button>
	   </div>
   	   <div class="col-md-6 col-md-offset-3" style="border: 0px solid gray;">	   
	   	   <form name="sizePerPageFrm">
	   	      <span style="color: red; font-weight: bold;">페이지당 글갯수-</span>
	   	   	  <select name="sizePerPage" id="sizePerPage">
	   	   	  	 <option value="10">10</option>
	   	   	  	 <option value="5">5</option>
	   	   	  	 <option value="3">3</option>
	   	   	  </select>
	   	   </form>
   	   </div>
   </div>
   
   <div class="row">
   <div class="col-md-12" style="border: 0px solid gray;">
   <table style="width: 95%; border-left:none; border-right:none;" class="outline" >
  <thead>
     <tr>
	    <th width="8%" style ="text-align: center;">
		    <input type="checkbox" id="allcheck">
		    <span style="red; font-size: 8pt;"><label for="allcheck">전체선택</label></span>
	    </th>
        <th width="10%" style ="text-align: center;">글번호</th> 
        <th width="10%" style ="text-align: center;">작성자</th>
        <th width="42%" style ="text-align: center;">글내용</th>
        <th width="20%" style ="text-align: center;">작성일자</th>
        <th width="10%" style ="text-align: center;">IP주소</th>
      </tr>
  </thead>
     <tbody>
 <c:if test="${memoList==null}">      
    <tr>
      <td colspan ="5">데이터가 없습니다.</td>
    </tr>
  </c:if> 
  <c:if test="${memoList !=null }">
  	<form name="checkFrm">
     <c:forEach var="memovo" items="${memoList}" >
     	<%-- 공개용 메모 --%>
        <c:if test="${memovo.status==1}">
	        <tr>
	          <td style="text-align: center;"><input type="checkbox" class="check" name="check" value="${memovo.idx}"></td>
	          <td style="text-align: center;">${memovo.idx}</td> <!-- MemoDAO 의 HashMap의 키값 -->
	          <td style="text-align: center;">${memovo.fk_userid}</td>
	          <td style="text-align: center;">${fn:replace(memovo.msg,"<","&lt")}</td>
	          <!-- String 타입, fn:replace(A, B, C) : 문자열 A에서 B에 해당하는 문자를 찾아 C로 변환한다 --> 
	          <!-- HTML 에서 &nbsp; 공백     &lt; 부등호(<)  &gt; 부등호(>) &amp; &   &quot;  " 이다.  -->
	          <td style="text-align: center;">${memovo.writedate}</td>
	          <td style="text-align: center;">${memovo.cip}</td>
	       </tr>
       </c:if>
     	<%-- 비공개용 메모 --%>
        <c:if test="${memovo.status==0}">
	        <tr style="background-color:pink;">
	          <td style="text-align: center;"><input type="checkbox" class="check" name="check" value="${memovo.idx}"></td>
	          <td style="text-align: center;">${memovo.idx}</td> <!-- MemoDAO 의 HashMap의 키값 -->
	          <td style="text-align: center;">${memovo.fk_userid}</td>
	          <td style="text-align: center;">${fn:replace(memovo.msg,"<","&lt")}</td>
	          <!-- String 타입, fn:replace(A, B, C) : 문자열 A에서 B에 해당하는 문자를 찾아 C로 변환한다 --> 
	          <!-- HTML 에서 &nbsp; 공백     &lt; 부등호(<)  &gt; 부등호(>) &amp; &   &quot;  " 이다.  -->
	          <td style="text-align: center;">${memovo.writedate}</td>
	          <td style="text-align: center;">${memovo.cip}</td>
	       </tr>
       </c:if>
    </c:forEach>
    </form>
    <th colspan="6" style="text-align:center;">${pageBar}</th>
 </c:if>
    </tbody>
</table>
</div>
</div>
<jsp:include page="../footer.jsp" />