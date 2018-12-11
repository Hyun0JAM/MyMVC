<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% String ctxPath = request.getContextPath(); %>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>간단한 jQuery Ajax 예제1(서버의 응답을 텍스트로 받아서 text 로 출력하는 예제)</title> 

<script type="text/javascript" src="<%=ctxPath %>/js/jquery-3.3.1.min.js"></script> 

<script type="text/javascript">
	
	$(document).ready(function(){
		
		$("#btn1").click(function(){
			
			$.ajax({
				url:"simple1.txt",
				type:"GET",
				dataType:"text",  // xml, json, html, script, text
				success:function(data){
					$("#result").empty(); // 해당요소(#result)의 내용을 모두 비워서 새 데이터로 채울 준비를 한다. 
					$("#result").text(data);
				},
				error: function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}
			});
		});
		
		
		$("#btn2").click(function(){
			
			$.ajax({
				url:"simple2.jsp",
				type:"GET",
				dataType:"text",  // xml, json, html, script, text
				success:function(data){
					$("#result").empty(); // 해당요소(#result)의 내용을 모두 비워서 새 데이터로 채울 준비를 한다. 
					$("#result").text(data);
				},
				error: function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}
			});
		});
		
	});

</script>

</head>
<body>
	<button type="button" id="btn1">simple1.txt</button> &nbsp;&nbsp;
	<button type="button" id="btn2">simple2.jsp</button>
	
	<p>
	여기는 simpleAjax.jsp 페이지 입니다.
	<p>
	<div id="result"></div>
</body>
</html>



