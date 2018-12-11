<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<jsp:include page="../header.jsp" />
<style type="text/css" >
 table#tblCartList {width: 90%;
                    border: solid gray 1px;
                    margin-top: 20px;
                    margin-left: 10px;
                    margin-bottom: 20px;}
 table#tblCartList th {border: solid gray 1px;}
 table#tblCartList td {border: dotted gray 1px;} 
.delcss {background-color: cyan;
          font-weight: bold;
          color: red;}
.ordershoppingcss {background-color: cyan;
               font-weight: bold;
               color: blue;}       
</style>
<script type="text/javascript">
$(document).ready(function() {
	   $(".spinner").spinner( {
		   spin: function(event, ui) {
			   if(ui.value > 100) {
				   $(this).spinner("value", 0);
				   return false;
			   }
			   else if(ui.value < 0) {
				   $(this).spinner("value", 10);
				   return false;
			   }
		   }
	   } );// end of $("#spinner").spinner({});----------------
	   $(".del").hover(
			   function(){$(this).addClass("delcss");},
			   function(){$(this).removeClass("delcss");});
});// end of $(document).ready();------------------------------

function goOqtyEdit(cartnoID,oqtyID) {
	var cartno = $("#"+cartnoID).val();
	var oqty = $("#"+oqtyID).val();
	var regExp = /^[0-9]+$/g;
	var bool = regExp.test(oqty);
	if(!bool){
		alert("수정하시려는 수량은 0개 이상 이어야 합니다.");
		location.href="myCartList.do";
		return;
	}
	var frm = document.updateOqtyFrm;
	frm.cartno.value = cartno;
	frm.oqty.value = oqty;
	frm.method = "POST"; 
	frm.action = "cartEdit.do";
	frm.submit();
}// end of goOqtyEdit(oqtyID, cartnoID)---------------------
function goDel(cartno) {      
	var frm = document.updateOqtyFrm;
	frm.cartno.value= cartno;
	frm.method="POST";
	frm.action="cartDel.do";
	frm.submit();
}// end of goDel(cartno)--------------   
// 체크박스 모두선택 및 모두해제 되기 위한 함수
function allCheckBox() {   
	var bool = $("#allCheckOrNone").is(':checked'); // 체크되어있다면 true 아니면 false
	$(".check").prop('checked',bool);
}// end of allCheckBox()------------

// **** 주문하기 *****
function goOrder(oqtyID,pqtyID) {
	var flag = false;
	$(".check").each(function(){
		if($(this).is(':checked')==true){
			flag = true;
			return false;
		}
	});
	if(flag==false){
		alert("주문 할 상품을 선택하세요");
		return;
	}
	var YN = confirm("선택한 제품을 주문 하시겠습니까?");
	if(YN==false){
		alert("주문을 취소 하셨습니다.");
		return;
	}
	var frm = document.orderFrm;
	var sumtotalprice = 0;
	var sumtotalpoint = 0;
	var cnt =0;
	var checkflag=false;
	$(".check").each(function(){
		if($(this).is(':checked')){
			var totalprice = $(this).parent().parent().find("#totalprice").text();
			var totalpoint = $(this).parent().parent().find("#totalpoint").text();
			var priceArr = totalprice.split(',');
			var pointArr = totalpoint.split(',');
			var priceStr = priceArr.join('');
			var pointStr = pointArr.join('');
			sumtotalprice = sumtotalprice + parseInt(priceStr);
			sumtotalpoint = sumtotalpoint + parseInt(pointStr);
			var oqty = $("#oqty"+cnt).val();
			var pqty = $("#pqty"+cnt).val();
			if(oqty>pqty) checkflag=true;
		}
		cnt++;
	});
	cnt =0;
	$(".check").each(function(){
		if(!$(this).is(':checked')){// 체크가 안되었다면
			$(this).parent().parent().find(":input").attr("disabled",true);
		}
		cnt++;
	});
	var coin = ${member.coin};
	if(coin <sumtotalprice){ 
		alert("코인액이 부족합니다.");
		return;
	}
	else if(checkflag){
		alert("재고가 부족합니다.");
		return;
	}
	else{
		frm.sumtotalprice.value=sumtotalprice;
		frm.sumtotalpoint.value=sumtotalpoint;
		frm.method="POST";
		frm.action="orderAdd.do";
		frm.submit();	
	}
}// end of goOrder()-----------------------------

</script>
<h2>::: ${(sessionScope.member).name} 님[ ${(sessionScope.member).userid} ] 장바구니 목록 :::</h2>   
  <%-- 장바구니에 담긴 제품목록을 보여주고서 
       실제 주문을 하도록 form 생성한다. --%>
 <form name="orderFrm">
   <table id="tblCartList" >
    <thead>
      <tr>
       <th style="border-right-style: none;">
           <input type="checkbox" id="allCheckOrNone" onClick="allCheckBox();" />
           <span style="font-size: 10pt;"><label for="allCheckOrNone">전체선택</label></span>
       </th>
	        <th colspan="5" style="border-left-style: none; font-size: 12pt; text-align: center;">
	           	주문하실 제품을 선택하신후 주문하기를 클릭하세요
	        </th>
        </tr>
        <tr style="background-color: #cfcfcf;">
        	<th style="width:10%; text-align: center; height: 30px;">제품번호</th>
        	<th style="width:23%; text-align: center;">제품명</th>
            <th style="width:17%; text-align: center;">수량</th>
            <th style="width:20%; text-align: center;">판매가/포인트(개당)</th>
            <th style="width:20%; text-align: center;">총액</th>
            <th style="width:10%; text-align: center;">삭제</th>
        </tr>   
    </thead>
    <tbody>
    <c:if test="${cartList == null || empty cartList}">
    <tr>
        <td colspan="6" align="center">
            <span style="color: red; font-weight: bold;">
          	      장바구니에 담긴 상품이 없습니다.
        	</span>
    	</td>   
    </tr>
    </c:if>   
	<c:if test="${cartList != null && not empty cartList}">
      	<c:set var="cartTotalPrice" value="0"/>
      	<c:set var="cartTotalPoint" value="0"/>
        <c:forEach var="cart" items="${cartList}" varStatus="status">
        <%-- 총금액 누적하기 --%>
        <c:set var="cartTotalPrice" value="${cartTotalPrice + cart.item.totalPrice }"/>
        <c:set var="cartTotalPoint" value="${cartTotalPoint + cart.item.totalPoint }"/>
        <%-- vatStatus 는 반복문의 상태정보를 알려주는 어트리뷰트이다.
             status.index => 0부터 시작한다.
             status.count => 1부터 시작한다. 반복문의 횟수를 알려주는 것이다.--%>
         	<tr>
                <td align="center"> <%-- 체크박스 및 제품번호 --%>
                	<input type="checkbox" class="check" name="pnum" id="pnum" value="${cart.pnum}"/> &nbsp;
                    <label for="pnum${status.index}">${cart.pnum}</label>
                </td>
                <td align="center"> <%-- 제품이미지1 및 제품명 --%>
                    <img src="images/${cart.item.pimage1}" width="200px" height="130px" />
                    <br/>${cart.item.pname}
                </td>
                <td align="center"> <%-- 수량 --%>
                <%-- foreach문에서 id를 고유하게 사용하기 위해 ${status.index}를 붙인다. --%>
                	<input type="text" id="oqty${status.index}" name="oqty" class="spinner" value="${cart.oqty }" style="width:30px;"> 개
                	<input type="hidden" id="cartno${status.index}" name="cartno" value="${cart.cartno }">
                	<input type="hidden" id="pqty${status.index}" value="${cart.item.pqty }" />
                	<button type="button" onClick="goOqtyEdit('cartno${status.index }','oqty${status.index }');">수정</button>
                </td>
                <td align="center">
                	<fmt:formatNumber value="${cart.item.saleprice }" pattern="###,###" /> 원 /
                	<input type="hidden" name="saleprice" id="saleprice${status.index}" value="${cart.item.saleprice }" />
                 	<br/><span style="color:green; font-weight:bold;"><fmt:formatNumber value="${cart.item.point }" pattern="###,###" /> POINT</span>
                </td>
                <td align="center">
                 	<span id="totalprice" style="font-weight: bold;" ><fmt:formatNumber value="${cart.item.totalPrice}" pattern="###,###" /></span>원/
                 	<br/>
                 	<span id="totalpoint" style="font-weight: bold; color:green;">${cart.item.totalPoint}</span>POINT
                </td>
                <td align="center">
                	<span class="del" style="cursor:pointer;" onClick="goDel('${cart.cartno}')">삭제</span>
                </td>
            </tr>
    	</c:forEach>
	</c:if>   
    	<tr>
           	<td colspan="3" align="right">
              	<span style="font-weight: bold;">장바구니 총액 : <fmt:formatNumber value="${cartTotalPrice }" pattern="###,###" /></span>원
              	<input type="hidden" name="sumtotalprice">
              	<br/>
              	<span style="font-weight: bold; margin-right: 7px;">총 포인트 : ${cartTotalPoint }  </span> POINT 
            	<input type="hidden" name="sumtotalpoint">
            </td>
           	<td colspan="3" align="center">
              	<span class="ordershopping" style="cursor: pointer;" onClick="goOrder();">[주문하기]</span>&nbsp;&nbsp;
            	<span class="ordershopping" style="cursor: pointer;" onClick="javascript:location.href='<%= request.getContextPath() %>/mallHome.do'">[계속쇼핑]</span>
        	</td>
    	</tr>
    </tbody>
	</table> 
</form>    
<%-- 장바구니에 담긴 제품수량을 수정하는 form --%>
<form name="updateOqtyFrm">
	<input type="hidden" name="cartno" />
	<input type="hidden" name="oqty" />
	<input type="hidden" name="pqty">
</form>
<%-- 장바구니에 담긴 제품을 삭제하는 form --%>
<form name="deleteFrm">
</form>
<form name="orderCheckFrm">
	<input type="hidden" name="oqty" />
	<input type="hidden" name="pqty" />
</form>
<jsp:include page="../footer.jsp" />
    