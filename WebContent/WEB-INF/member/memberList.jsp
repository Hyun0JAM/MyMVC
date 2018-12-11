<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../header.jsp" />
<style type="text/css">
	.th {text-align: center;}
    .td {text-align: center;}
    .namestyle {background-color: cyan;  /* cyan; 은 하늘색 */
                font-weight: bold;
                font-size: 12pt;
                color: blue;
                cursor: pointer; }
</style>
<script type="text/javascript">
    $(document).ready(function() {
    	
    	$("#sizePerPage").bind("change", function(){
    	//	var val = $(this).val();
    	//	alert("확인용 : " + val);
    		var frm = document.memberFrm;
    		frm.method = "GET";
    		frm.action = "memberList.do";
    		frm.submit();
    	});
    
		$("#sizePerPage").val("${requestScope.sizePerPage}");
	
		$("#period").bind("change", function(){
			var frm = document.memberFrm;
    		frm.method = "GET";
    		frm.action = "memberList.do";
    		frm.submit();
		});
		
		$("#period").val("${requestScope.period}");
		$("#searchType").val("${searchType}");
		$("#searchWord").val("${searchWord}");
		
		$(".name").hover(function(){$(this).addClass("namestyle");}, function(){ $(this).removeClass("namestyle"); });
		$(".edit").hover(function(){ $(this).addClass("namestyle");},  function(){$(this).removeClass("namestyle"); });
		$(".del").hover(function(){$(this).addClass("namestyle");},function(){$(this).removeClass("namestyle");});
		
		// 검색어에 엔터를 입력한 경우
		$("#searchWord").keydown(function(event){
			if(event.keyCode == 13) {
				// 엔터를 했을 경우
				 goSearch();
				//alert("엔터했군요");
			}
		});
		
    }); // end of $(document).ready()--------------
    
    function goSearch() {
    	var searchWord = $("#searchWord").val().trim();
    	if(searchWord == "") {
    		alert("검색어를 입력하세요!!");
    		return;
    	}
    	else {
    		var frm = document.memberFrm;
    		frm.method = "GET";
    		frm.action = "memberList.do";
    		frm.submit();
    	}
    }// end of function goSearch()-----------------
    function goEnable(idx) {
    	var bool = confirm(idx+" 번 회원을 정말로 활성화하시겠습니까?");
    	if(bool) {
    		var frm = document.idxFrm;
    		frm.idx.value = idx;
    		frm.method = "POST";
    		frm.action = "memberEnable.do";
    		frm.submit();
    	}
    }// end of function goEdit(idx)---------------
    function goDel(idx) {
    	var bool = confirm(idx+" 번 회원을 정말로 삭제하시겠습니까?");
    	if(bool) {
    		var frm = document.idxFrm;
    		frm.idx.value = idx;
    		frm.method = "POST";
    		frm.action = "memberDelete.do";
    		frm.submit();
    	}
    	
    }// end of function goDel(idx, goBackURL)----------------
    function goRecovery(idx){
    	var bool = confirm(idx+" 번 회원을 복원하시겠습니까?");
    	if(bool) {
    		var frm = document.idxFrm;
    		frm.idx.value = idx;
    		frm.method = "POST";
    		frm.action = "memberRecovery.do";
    		frm.submit();
    	}
    }
    function goDetail(idx){
    	var frm = document.idxFrm;
    	frm.idx.value = idx;
    	frm.method = "POST";
    	frm.action = "memberDetail.do";
    	frm.submit();
    }
</script>
<div class="row">
	<div class="col-md-12">
		<%-- <%= currentURL %><br/> --%>
		<h2 style="margin-bottom: 40px;">::: 회원전체 정보보기 :::</h2>
 	    <form name="memberFrm">
		    <select id="searchType" name="searchType">
		    	<option value="name">회원명</option>
		    	<option value="userid">아이디</option>
		    	<option value="email">이메일</option>
		    </select>
		    <input type="text" id="searchWord" name="searchWord" size="25" class="box" style="margin-left: 10px; margin-right: 10px;" />
		    <button type="button" onClick="goSearch();">검색</button>
			<div style="margin-top: 20px; margin-bottom: 20px;"> 
				<div style="display: inline;">
					<span style="color: red; font-weight: bold; font-size: 12pt;">페이지당 회원명수-</span>
					<select id="sizePerPage" name="sizePerPage">
						<option value="10">10</option>
						<option value="5">5</option>
						<option value="3">3</option>
					</select>
				</div>
				<div style="display: inline; margin-left: 20px;">
					<select id="period" name="period">
						<option value="-1">전체</option>
						<option value="3">최근 3일이내</option>
						<option value="10">최근 10일이내</option>
						<option value="30">최근 30일이내</option>
						<option value="60">최근 60일이내</option>
					</select>
				</div>
			</div>	
	 	</form>
		<table class="outline">
			<thead>
				<tr>
					<th class="th">회원번호</th>
					<th class="th">회원명</th>
					<th class="th">아이디</th>
					<th class="th">이메일</th>
					<th class="th">휴대폰</th>
					<th class="th">가입일자</th>
					<c:if test="${(sessionScope.member).userid=='admin'}">
						<th class="th">휴면해제&nbsp;|&nbsp;삭제</th>
					</c:if>
				</tr>
			</thead>
		 	<tbody>
			<%-- === 가입된 회원이 존재하는 경우 === --%>
			<c:if test="${memberList != null || not empty memberList}">
				<c:forEach var="membervo" items="${memberList}">
					<c:if test="${membervo.status == 0}">
						<tr style="background-color: pink;">
					</c:if>
					<c:if test="${membervo.status == 1 && membervo.humyun}"> 
						<tr style="background-color:yellow;">
					</c:if>
					<c:if test="${membervo.status == 1 && !membervo.humyun}"> 
						<tr>
					</c:if>
						    <td class="td">${membervo.idx}</td>
							<td class="td name" onClick="goDetail('${membervo.idx}');">${membervo.name}</td>
							<%--  자바스크립트에서 페이지이동은 location.href="이동해야할 페이지명"; 으로 한다. --%>
							<td class="td">${membervo.userid}</td>
							<td class="td">${membervo.email}</td>
							<td class="td">${membervo.allHp}</td>
							<td class="td">${membervo.registerday}</td>
							<c:if test="${(sessionScope.member).userid=='admin'}">
								<td class="td">
									<c:if test="${membervo.humyun }">
										<span class="edit" style="cursor: pointer" onClick="goEnable('${membervo.idx}');">휴면해제</span>&nbsp;
									</c:if>
									<c:if test="${membervo.status==0 }">
										<span class="del" style="cursor: pointer" onClick="goRecovery('${membervo.idx}')">복원</span>&nbsp;
									</c:if>
									<c:if test="${membervo.status==1 }">
										<span class="del" style="cursor: pointer" onClick="goDel('${membervo.idx}')">삭제</span>
									</c:if>
								</td> 
							</c:if>
						</tr>
				</c:forEach>
			</c:if>
			<%-- === 가입된 회원이 존재하지 않는 경우 === --%>  
			<c:if test="${memberList == null || empty memberList}">
				<tr>
					<td colspan="7" style="text-align: center; color: red;">가입된 회원이 없습니다.</td>
				</tr>
			</c:if>
			</tbody>
			<thead> 
			  <tr>
				<th colspan="4" class="th">
					<%-- === 페이지바 넣어주기 === --%>
					${pageBar} 
				</th>
					
				<th colspan="3" class="th">
				   현재[<span style="color: red;">${currentShowPageNo}</span>]페이지 / 총[${totalPage}]페이지 &nbsp; 
				  회원수 : 총 ${totalMemberCount}명 
				</th>
			  </tr>
			</thead>
		</table>
		<%-- *** 특정 회원의 정보를 조회 및 삭제하기 위한 폼 생성하기 *** --%>
		<form name="idxFrm">
			<input type="hidden" name="idx" />
			<input type="hidden" name="goBackURL" />
		</form>
	</div>
</div>
<jsp:include page="../footer.jsp" />   

