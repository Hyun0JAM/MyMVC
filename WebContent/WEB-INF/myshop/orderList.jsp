<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../header.jsp" />
<style type="text/css" >
   table#tblOrderList {width: 90%;
                      border: solid gray 1px;
                      margin-top: 20px;
                      margin-left: 10px;
                      margin-bottom: 20px;}
                      
   table#tblOrderList th {border: solid gray 1px;}
   table#tblOrderList td {border: dotted gray 1px;} 

   .delcss {background-color: cyan;
            font-weight: bold;
            color: red;}
     	   
   .ordershoppingcss {background-color: cyan;
     	         font-weight: bold;
     	         color: blue;}  	   
</style>
<script>
$(document).ready(function(){
	$(".del").hover(function(){
		$(this).addClass("delcss");	
	}, 
	function(){
	    $(this).removeClass("delcss");
	});
	$("#sizePerPage").bind("change",function(){
		var frm = document.frmDeliver;
		frm.method="GET";
		frm.action="orderList.do";
		frm.submit();
	});
	$("#sizePerPage").val("${sizePerPage}");
	
	$("#btnDeliverStart").click(function(){
		$(".odrcode").prop("disabled",true); // 비활성화
		var flag = false;
		$(".deliverStartPnum").each(function(){ // 선택자 : 배송시작 체크박스
			var bool = $(this).is(':checked');
			if(bool){ // 체크되었을때
				flag = true;
				$(this).next().next().prop("disabled",false); // 체크된것만 활성화
			}
		});
		if(!flag){	
			alert("하나이상의 제품을 선택 해 주세요.");
			return;
		}
		else{
			var frm = document.frmDeliver;
			frm.method="POST";
			frm.action="deliverStart.do";
			frm.submit();
		}
	});
	$("#btnDeliverEnd").click(function(){
		$(".odrcode").prop("disabled",true); // 비활성화
		var flag = false;
		$(".deliverEndPnum").each(function(){ // 선택자 : 배송시작 체크박스
			var bool = $(this).is(':checked');
			if(bool){ // 체크되었을때
				flag = true;
				$(this).next().next().prop("disabled",false); // 체크된것만 활성화
			}
		});
		if(!flag){	
			alert("하나이상의 제품을 선택 해 주세요.");
			return;
		}
		else{
			var frm = document.frmDeliver;
			frm.method="POST";
			frm.action="deliverEnd.do";
			frm.submit();
		}
	});
});

function allCheckBoxStart(){
	var bool = $("#allCheckStart").is(":checked");
	$(".deliverStartPnum").prop("checked",bool);
}
function allCheckBoxEnd(){
	var bool = $("#allCheckEnd").is(":checked");
	$(".deliverEndPnum").prop("checked",bool);
}
function openMember(odrcode){
	window.open("memberInfo.do?odrcode="+odrcode,"openMember","top=50px,left=100px,height=520px,width=600px");
}
</script>
<c:set var="userid" value="${member.userid }" />
<c:if test='${userid eq "admin" }'>
	<h2 style="margin-bottom: 40px;">::: ${(sessionScope.member).name} 님[ ${(sessionScope.member).userid} ] 주문 목록 :::</h2>	
</c:if>
<c:if test='${userid ne "admin" }'>
	<h2 style="margin-bottom: 40px;">::: ${member.name} 님[ ${userid} ] 주문 목록 :::</h2>	
</c:if>

  <%-- 장바구니에 담긴 제품목록을 보여주고서 
        실제 주문을 하도록 form 생성한다. --%>
<form name="frmDeliver">
<table id="tblOrderList" style="width: 95%; margin:0 autol">

	<tr>
		<th colspan="4" style="text-align: center; font-size: 14pt; font-weight: bold; border-right-style: none;"> 주문내역 보기 </th>
		<th colspan="3" style="text-align: center; border-left-style: none;">
			<span style="color: red; font-weight: bold;">페이지당 갯수-</span>
			<select id="sizePerPage" name="sizePerPage">
				<option value="10">10</option>
				<option value="5">5</option>
				<option value="3">3</option>
			</select>
	    </th>
	</tr>

  <c:if test='${userid eq "admin" }'>
	<tr>	
		<td colspan="7" align="right" > 
			<input type="checkbox" id="allCheckStart" onClick="allCheckBoxStart();"><label for="allCheckStart"><span style="color: green; font-weight: bold; font-size: 9pt;">전체선택(배송시작)</span></label>&nbsp;
			<input type="button" name="btnDeliverStart" id="btnDeliverStart" value="배송시작"  style="width:80px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			
			<input type="checkbox" id="allCheckEnd" onClick="allCheckBoxEnd();"><label for="allCheckEnd"><span style="color: red; font-weight: bold; font-size: 9pt;">전체선택(배송완료)</span></label>&nbsp;
			<input type="button" name="btnDeliverEnd" id="btnDeliverEnd" value="배송완료" style="width:80px;">
		</td>
	</tr>
  </c:if>
		
    <tr bgcolor="#cfcfcf">
		<td align="center" width="15%">주문코드(전표)</td>
		<td align="center" width="15%">주문일자</td>
		<td align="center" width="30%">제품정보</td> <%-- 제품번호,제품명,제품이미지1,판매정가,판매세일가,포인트 --%>
		<td align="center" width="10%">수량</td>
		<td align="center" width="10%">금액</td>   
		<td align="center" width="10%">포인트</td>
		<td align="center" width="10%">배송상태</td>
    </tr>
	<c:if test="${orderList==null || empty orderList}" > 
	  <tr>
		  <td colspan="7" align="center">
		  <span style="color: red; font-weight: bold;">주문내역이 없습니다.</span>
	  </tr>
	</c:if>

	<%-- ============================================ --%>
	<c:if test="${orderList != null && not empty orderList }">
		<c:forEach var="odrmap" items="${orderList}" varStatus="status">
			<%--
				 varStatus 는 반복문의 상태정보를 알려주는 애트리뷰트이다.
				 status.index : 0 부터 시작한다.
				 status.count : 반복문 횟수를 알려주는 것이다.
			 --%>
			<tr>
				<td align="center"> <%-- 주문코드(전표) 출력하기. 
				      만약에 관리자로 들어와서 주문내역을 볼 경우 해당 주문코드(전표)를 클릭하면 
				      주문코드(전표)를 소유한 회원정보를 조회하도록 한다.  --%>
				<c:if test='${userid eq "admin" }'>
					<a href="#" onClick="openMember('${odrmap.odrcode}')">${odrmap.odrcode}</a>
				</c:if>
				<c:if test='${userid ne "admin" }'>
					${odrmap.odrcode}
				</c:if>	 
				</td>
				<td align="center"> <%-- 주문일자 --%>
					${odrmap.odrdate}
				</td>
				<td align="center">  <%-- === 제품정보 넣기 === --%>
					<img src="images/${odrmap.pimage1} " width="130" height="100" />  <%-- 제품이미지1 --%>
					<br/>제품번호 : ${odrmap.fk_pnum}  <%-- 제품번호 --%>
					<br/>제품명 : ${odrmap.pname}      <%-- 제품명 --%>
					<br/>판매정가 : <span style="text-decoration: line-through;"><fmt:formatNumber value="${odrmap.price}" pattern="###,###" /> 원</span>   <%-- 제품개당 판매정가 --%>
					<br/><span style="color: red; font-weight: bold;">판매가 : <fmt:formatNumber value="${odrmap.saleprice}" pattern="###,###" /> 원</span>  <%-- 제품개당 판매세일가 --%> 
					<br/><span style="color: red; font-weight: bold;">포인트 : <fmt:formatNumber value="${odrmap.point}" pattern="###,###" /> 포인트</span>   <%-- 제품개당 포인트 --%>
				</td>
				<td align="center">    <%-- 수량 --%>
					 ${odrmap.oqty} 개
				</td>
				<td align="center">    <%-- 금액 --%>
				     <c:set var="su" value="${odrmap.oqty}" />
				     <c:set var="danga" value="${odrmap.saleprice}" />
				     <c:set var="totalmoney" value="${su * danga}" />
				     
					 <fmt:formatNumber value="${totalmoney}" pattern="###,###" /> 원
				</td>
				<td align="center">    <%-- 포인트 --%>
				     <c:set var="point" value="${odrmap.point}" />
				     <c:set var="totalpoint" value="${su * point}" />
					 <fmt:formatNumber value="${totalpoint}" pattern="###,###" /> 포인트
				</td>
				<td align="center"> <%-- 배송상태 --%>
				
					<c:choose>
						<c:when test="${odrmap.deliverstatus == '주문완료'}">
							주문완료<br/>
						</c:when>
						<c:when test="${odrmap.deliverstatus == '배송시작'}">
							<span style="color: green; font-weight: bold; font-size: 12pt;">배송시작</span><br/>
						</c:when>
						<c:when test="${odrmap.deliverstatus == '배송완료'}">
							<span style="color: red; font-weight: bold; font-size: 12pt;">배송완료</span><br/>
						</c:when>
					</c:choose>
	
					<c:if test='${userid eq "admin" }'>
						<br/><br/>
						<c:if test="${odrmap.deliverstatus == '주문완료'}">
							<input type="checkbox" class="deliverStartPnum" name="deliverStartPnum" id="chkDeliverStart${status.index}" value="${odrmap.fk_pnum}"><label for="chkDeliverStart${status.index}">배송시작</label> 
							<input type="text" class="odrcode" name="odrcode" value="${odrmap.odrcode}"  />
						</c:if>
						<br/>
						<c:if test="${odrmap.deliverstatus == '주문완료' or odrmap.deliverstatus == '배송시작'}">
							<input type="checkbox" class="deliverEndPnum" name="deliverEndPnum" id="chkDeliverEnd${status.index}" value="${odrmap.fk_pnum}"><label for="chkDeliverEnd${status.index}">배송완료</label>
							<input type="hidden" class="odrcode" name="odrcode" value="${odrmap.odrcode}"  />
							<input type="hidden" class="hidden_fk_userid" name="fk_userid" value="${odrmap.fk_userid}">
							<input type="hidden" class="hidden_pname" name="pname" value="${odrmap.pname }">
						</c:if>
					</c:if>
				</td>
			</tr>
		</c:forEach>
			<tr>
				<th colspan="7" style="text-align: center;">${pagebar}</th>
			</tr>
		</c:if>
		<%-- ============================================================================ --%>	
</table>
</form>         
<jsp:include page="../footer.jsp" />