<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/ajaxstudy/chap2/css/style.css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-3.3.1.min.js"></script> 
<script type="text/javascript">
	$(document).ready(function(){
		$("#btn").click(function(){
			var form_data = {booklist:$("fictioninfo").val()};
			$.ajax({
				url:"2booksXML.do",
				type:"GET",
				data:form_data,
				dataType:"xml",
				success:function(xml){ // 변수가 아무거나 들어와도 좋지만 기억하기 좋게 데이터 형식을 쓴다.
					$("#fictioninfo").empty();  // ul엘리먼트 속에있는 모든것을 모두 비워서 새 데이터를 채울 준비를 한다.
					$("#programinginfo").empty();
					var rootElement = $(xml).find(":root"); 
					//% $(파라미터에들어온 변수).find(":root"); 넘어온 xml의 최상위 root엘리먼트를 찾아주는것 
					//alert("확인용 : "+$(rootElement).prop("tagName"));
					var bookArr = $(rootElement).find("book");
					/*  book이라는 특정한 엘리먼트를 찾는 것
						검색되어진 book이라는 엘리먼트가 복수개 이므로
						저장되어질 bookArr 변수는 타입이 배열타입이된다.*/
					
				bookArr.each(function(){
						var html = "<li>도서명 : "+$(this).find("title").text()+" , 작가명 : "+$(this).find("author").text()+" </li>"
						var subject = $(this).find("subject").text();
						if(subject == "소설"){
							$("#fictioninfo").append(html);
						}
						else if(subject == "프로그래밍"){
							$("#programinginfo").append(html);
						}
					});
				},
				error:function(request, status, error){// 실패했다면 어디가 틀렸는지 alert를 띄워준다.
		               alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		        }
			});
		});
	});
</script>
<style type="text/css">
	h3 {color: blue;}
	ul {list-style: square;}
</style>
</head>
	<div align="center">
		<button type="button" id="btn">도서출력</button>
	</div>
	<div id="fiction">
		<h3>소설</h3>
		<ul id="fictioninfo"></ul>
	</div>
	<div id="it">
		<h3>프로그래밍</h3>
		<ul id="programinginfo"></ul>
	</div>
</html>
	