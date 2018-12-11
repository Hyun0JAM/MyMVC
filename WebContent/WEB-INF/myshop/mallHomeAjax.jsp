<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../header.jsp" />
<style type="text/css">
  .th, .td {border: 0px solid gray;}
  a:hover {text-decoration: none;}
</style>
<script>
	$(document).ready(function(){
		//HIT상품 게시물을 더보기위하여 "더보기.."
		//$("#totalNEWCount").hide();
		$("#countNEW").hide();
		displayNewAppend("1");
		$("#btnMoreNEW").click(function(){
			if($(this).text()=="처음으로"){
				$("#displayResultNEW").empty();
				displayNewAppend("1");
				$("#btnMoreNEW").text("...더보기");
			}
			else displayNewAppend($(this).val());
		});
	});
	
	var lenHIT = 8; //HIT상품 더복시 클릭시 보여줄 상품 갯수
	// display할 HIT 제품정보 추가 요청하기
	function displayHitAppend(start){
		var form_data = {"start":start,"len":lenHIT,"pspec":"HIT"};
		$.ajax({
			url:"mallDisplayXML.do",
			type:"GET",
			data:form_data,
			dataType:"XML",
			success:function(xml){
				var rootElement = $(xml).find(":root");
				var productArr = $(rootElement).find("product"); // 주의 productArr은 null이아님
				
				var html ="";
				if(productArr.length==0){
				   html+="<tr><td colspan=\"4\" class=\"td\" align=\"center\">현재상품</td></tr>";
				   $("#displayResult").html(html);
				}
				else{
					html += "<tr>";
					for(var i=0;i<productArr.length;i++){
						var product = $(productArr).eq(i); // 선택된 요소들을 인덱스번호로 찾을수 있는 선택자
						//console.log($(product).prop("tagName"));
						//console.log($(product).html());
						html+= "<td class=\"td\" align=\"center\">"
							+ "<a href=\"/MyMVC/prodView.do?pnum="+$(product).find('pnum').text()+"\">"
							+ "<img width=\"120px;\" height=\"130px;\" src=\"images/$"+$(product).find('pimage1').text()+"\">"
							+ "</a>"
							+ "<br/> 제품명 : " + $(product).find("pname").text()
						    + "<br/> 정가 : <span style='color: red; text-decoration: line-through;'>" + $(product).find("price").text() +"원</span>"
						    + "<br/> 판매가 : <span style='color: red; font-weight: bold;'>" + $(product).find("price").text() +"원</span>" 
						    + "<br/> 할인율 : <span style='color: blue; font-weight: bold;'>[" + $(product).find("percent").text() +"% 할인]</span>"
						    + "<br/> 포인트 : <span style='color: orange;'>" + $(product).find("point").text() + " POINT </span>"
						    + "</td>";
						    if((i+1)%4==0) html+="</tr><tr><td colspan='4' style='line-height:100px;'></td></tr><tr>";
					}
					html+="</tr>"; 
					$("#displayResult").append(html);
					$("#btnMoreHit").val(parseInt(start)+lenHIT);
					$("#countHIT").text(parseInt($("#countHIT").text())+$(productArr).length);
					if($("#totalHITCount").text() == $("#countHIT").text()){
						$("#btnMoreHit").text("처음으로");
						$("#countHIT").text("0");
					}
				}
			},
			error:function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
		});
	}
	//////////////////////////////////////////////////////////
	var lenNEW = 8; //HIT상품 더복시 클릭시 보여줄 상품 갯수
	// display할 HIT 제품정보 추가 요청하기
	function displayNewAppend(start){
		var form_data = {"start":start,"len":lenNEW,"pspec":"NEW"};
		$.ajax({
			url:"mallDisplayJSON.do",
			type:"GET",
			data:form_data,
			dataType:"JSON",
			success:function(json){
				var html ="";
				if(json.length==0){
				   html+="현재상품 준비중 ...";
				   $("#displayResultNEW").html(html);
				}
				else{
					$.each(json, function(entryIndex, entry){ 
			        	  html += "<div style=\"display: inline-block; margin: 30px; border: solid gray 0px;\" align=\"left\">" 
			        	        + "  <a href=\"/MyMVC/prodView.do?pnum="+entry.pnum+"\">"
			        	        + "    <img width=\"120px;\" height=\"130px;\" src=\"images/"+entry.pimage1+"\">"
			        	        + "  </a><br/>"
			        	        + "제품명 : "+entry.pname+"<br/>"
			        	        + "정가 : <span style=\"color: red; text-decoration: line-through;\">"+entry.price+" 원</span><br/>"
			        	        + "판매가 : <span style=\"color: red; font-weight: bold;\">"+entry.saleprice+" 원</span><br/>"
			        	        + "할인율 : <span style=\"color: blue; font-weight: bold;\">["+entry.percent+"%] 할인</span><br/>"
			        	        + "포인트 : <span style=\"color: orange;\">"+entry.point+" POINT</span><br/>"
			        	        + "</div>";
			              }); // end of $.each()---------------------------
		            html += "<div style=\"clear: both;\">&nbsp;</div>";
					$("#displayResultNEW").append(html);
					$("#btnMoreNEW").val(parseInt(start)+lenNEW);
					$("#countNEW").text(parseInt($("#countNEW").text())+json.length);
					if($("#totalNEWCount").text() == $("#countNEW").text()){
						$("#btnMoreNEW").text("처음으로");
						$("#countNEW").text("0");
					}
				}
			},
			error:function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
		});
	}
</script>
<h2>::: 쇼핑몰 상품 :::</h2>    
<br/>
<!-- HIT 상품 디스플레이 하기 -->

<!-- NEW 제품 디스플레이(div 태그를 사용한것) -->
<div style="width: 90%; margin-top:50px; margin-bottom: 30px;">
	<div style="text-align: center; 
	            font-size: 14pt;
	            font-weight: bold;
	            background-color: #e1e1d0;
	            height: 30px;
	            margin-bottom: 15px;" >
	   <span style="vertical-align: middle;">- NEW 상품(DIV) -</span>
	 </div>

	 
	 <div id="displayResultNEW" style="margin: auto; border: solid 0px red;"></div>
	 <div style="margin-top: 20px; margin-bottom: 20px;">
		<button type="button" id="btnMoreNEW" value=""> 더보기...</button>
		<span id="totalNEWCount">${totalNEWCount}</span>
		<span id="countNEW">0</span>
	 </div>
	
</div>
<jsp:include page="../footer.jsp" />


    
    
    