<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../header.jsp" />
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" />
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>
<script type="text/javascript">
   $(document).ready(function() {
	   goLikeDisLikeCountShow();
	   $("#spinner").spinner({
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
	   });// end of $("#spinner").spinner({});----------------
		
   });// end of $(document).ready();------------------------------
   
	function goCart(pnum){ // 주문 량에대한 유효성 검사
		var frm = document.cartOrderFrm;
		var regExp = /^[0-9]+$/; // 숫자만 검사하는 정규 표현식
		var oqty = frm.oqty.value;
		var bool = regExp.test(oqty);
		if(!bool){ // 숫자 이외의 값이 들어온 경우
			alert("주문 갯수는 1개 이상 이어야 합니다.");
			frm.oqty.value="1";
			frm.oqty.focus();
			return;
		}
		else{
			oqty = parseInt(oqty);
			if(oqty<1){
				alert("주문 갯수는 1개 이상 이어야 합니다.");
				frm.oqty.value="1";
				frm.oqty.focus();
				return;
			}
			else{ // 올바르게 주문 한 경우
				 frm.method="POST";
				 frm.action="cartAdd.do";
				 frm.submit();	
			}	   
		}
	}
	function goOrder(pnum){ // 주문 량에대한 유효성 검사
		var frm = document.cartOrderFrm;
		var regExp = /^[0-9]+$/; // 숫자만 검사하는 정규 표현식
		var oqty = frm.oqty.value;
		var bool = regExp.test(oqty);
		if(!bool){ // 숫자 이외의 값이 들어온 경우
			alert("주문 갯수는 1개 이상 이어야 합니다.");
			frm.oqty.value="1";
			frm.oqty.focus();
			return;
		}
		else{
			oqty = parseInt(oqty);
			if(oqty<1){
				alert("주문 갯수는 1개 이상 이어야 합니다.");
				frm.oqty.value="1";
				frm.oqty.focus();
				return;
			}
			else{ // 올바르게 주문 한 경우
				var pqty = frm.pqty.value;
				var sumtotalprice = oqty*parseInt("${product.saleprice}");
				var sumtotalpoint = oqty*parseInt("${product.point}");
				if(oqty>pqty){
					alert("현재 상품의 재고가 부족합니다.");
					return;
				}
				else if(10<sumtotalprice){
					alert("코인액이 부족합니다.");
					return;
				}
				else{

					frm.sumtotalprice.value = sumtotalprice;
					frm.sumtotalpoint.value = sumtotalpoint; 
					frm.method="POST";
					frm.action="orderAdd.do";
					frm.submit();	
				}
			}	   
		}
	}
	function goLikeDisLikeCountShow(){
		var form_data = {"pnum":"${product.pnum}"};
		$.ajax({
			url:"likeDislikeCntShow.do",
			type:"GET",
			data:form_data,
			dataType:"JSON",
			success:function(json){
				var likeCnt = json.likecnt;
				var dislikeCnt = json.dislikecnt;
				$("#likeCnt").html(likeCnt);
				$("#dislikeCnt").html(dislikeCnt);
			},
			error:function(){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
		});
	}
	function goLikeAdd(pnum){
		var form_data = {"userid":"${member.userid}","pnum":pnum};
		$.ajax({
			url:"likeAdd.do",
			type:"POST",
			data:form_data,
			dataType:"JSON",
			success:function(json){
				swal(json.msg);
				goLikeDisLikeCountShow();
			},
			error:function(){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
		});	
	}
	function goDisikeAdd(pnum){
		var form_data = {"userid":"${member.userid}","pnum":pnum};
		$.ajax({
			url:"dislikeAdd.do",
			type:"POST",
			data:form_data,
			dataType:"JSON",
			success:function(json){
				swal(json.msg);
				goLikeDisLikeCountShow();
			},
			error:function(){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
		});	
	}
</script>
<style>
.line{
	margin-top: 20px;
	margin-bottom: 20px;}
li{ color: black;
	margin-top: 5px;}
</style>
<div style="width:95%;">
	<div class="row">
		<div class="col-md-12 line">
			<h2 style="font-size:15pt;font-weight:bold;">:::제품상세정보:::</h2>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4 line" >
			<img src="images/${product.pimage1 }" style="width: 400px;">
		</div>
		<div class="col-md-8 line" style=" text-align:left;">
			<ul style="list-style: none;">
				<li><span style="font-weight:bold; color: red;">HIT</span></li>
				<li>제품번호 : ${product.pnum }</li>
				<li>확인 : ${member.userid }</li>
				<li>제품이름 : ${product.pname }</li>
				<li>할인율 : ${product.percent }% 할인</li>
				<li>제품 정가 : <span style="text-decoration: line-through; color: gray;"><fmt:formatNumber value="${product.price}" pattern="###,###" />원</span></li>
				<li>제품 판매가 : <span style="font-weight:bold; color: blue;"><fmt:formatNumber value="${product.saleprice}" pattern="###,###" />원</span></li>
				<li>포인트 : <span style="font-weight:bold; color: green;">${product.point } POINT</span></li>
				<li>잔고갯수 : <span style="font-weight:bold; color: blue;">${product.pqty }</span> 개</li>
			</ul>
			<form name="cartOrderFrm">
				<ul style="margin-top: 20px; list-style: none;">
					<li>
						<label for="spinner" style="margin-bottom:20px;">주문갯수 : </label> <input id="spinner" name="oqty" value="1" style="width:30px; height:20px; ">
					</li>
					<li>
						<button type="button" class="btn btn-info" onClick="goCart('${product.pnum}');" style="margin: 0 3px;">장바구니</button>
						<button type="button" class="btn btn-warnning" onClick="goOrder('${product.pnum}');" style="margin: 0 3px;">바로구매</button>
					</li>
				</ul>
				<input type="hidden" name="pnum" value="${product.pnum}" />
				<input type="hidden" name="saleprice" value="${product.saleprice}" />
				<input type="hidden" name="sumtotalprice" value="" />
				<input type="hidden" name="sumtotalpoint" value="" />
				<input type="hidden" name="pqty" value="${product.pqty }" />
				<input type="hidden" name="goBackURL" value="${goBackURL}" />
			</form>			
		</div>
	</div>
	<div class="row" style="border: 1px solid lightgray">
	  <div class="col-md-12 line">
	  	<img src="images/${product.pimage2}" style="width: 420px; height: 300px;" />
	  </div>
	   <c:if test="${imgList != null}">
		 <c:forEach var="imgmap" items="${imgList}">
		      <div class="col-md-3 line">
		  		<img src="images/${imgmap.IMGFILENAME}" style="width: 250px; height: 170px;" />
		      </div> 	
		  </c:forEach>
	   </c:if>
	</div>
	<div class="row">
	  <div class="col-md-12 line">
	  	<span style="font-weight: bold; font-size: 14pt; color: white; background-color:black;">제품설명</span>
	  	<p>${product.pcontent }</p>
	  </div>
	</div>
	<div class="row" style="margin-bottom:50px;align:center;">
	  	<div class="col-sm-6 col-lg-3">
	  		<img src="images/like.png" width="100%" onClick="goLikeAdd('${product.pnum}');">
	  	</div>
	  	<div id="likeCnt" class="col-sm-6 col-lg-3"></div>
	  	<div class="col-sm-6 col-lg-3">
	  		<img src="images/dislike.png" width="100%" onClick="goDisikeAdd('${product.pnum}');">
	  	</div>
	  	<div id="dislikeCnt" class="col-sm-6 col-lg-3"></div>
	</div>
</div>
<jsp:include page="../footer.jsp" />