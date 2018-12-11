<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% String ctxPath = request.getContextPath(); %>  
  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jQuery Ajax 예제2(서버의 응답을 HTML로 받아서 HTML로 출력하는 예제)</title>

<link rel="stylesheet" type="text/css" href="<%=ctxPath %>/ajaxstudy/chap2/css/style.css" />

<script type="text/javascript" src="<%=ctxPath %>/js/jquery-3.3.1.min.js"></script> 

<script type="text/javascript">
   
   $(document).ready(function(){
      <%-- newsTitle.do 의 내용이 들어가는 것이다.--%>
      startAjaxCalls();
      $("#btn1").click(function(){
    	  var form_data = {searchname:$("name").val()}; // 키값:value값
    			  		// ,tel:$("#tel").val()};
         $.ajax({
            url:"memberHTML.do",
            type:"GET",
            data:form_data,
            dataType:"html", // html, json , xml, script ...
            success:function(data){ 
               $("#memberInfo").empty().html(data);
            },
            error:function(request, status, error){// 실패했다면 어디가 틀렸는지 alert를 띄워준다.
               alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
         });
      });
      
      $("#btn2").click(function(){
    	  $.ajax({
    		  url:"imageHTML.do",
    		  type:"GET", // default는 GET방식
    		  dataType:"html",
    		  success:function(html){ // 파라미터는 결과물을 써준다.
    			  $("#imgInfo").empty().html(html);
    		  },
    	  	  error:function(request, status, error){// 실패했다면 어디가 틀렸는지 alert를 띄워준다.
              alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
           }
    	  });
      });
      $("#btn3").click(function(){
    	  $("#memberInfo").empty();
    	  $("#imgInfo").empty();
    	  $("#name").val("");
      });
   });// end of $(document).ready();------------------
   
   function getNewsTitle(){
      $.ajax({
         url:"newsTitleHTML.do",
         type:"GET",
         dataType:"html",
         success:function(data){
            //console.log("== data 값 보기 ==");
            //console.log(data);
            $("#newsTitle").html(data);
         },
         error:function(request, status, error){// 실패했다면 어디가 틀렸는지 alert를 띄워준다.
            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
         }
      });
   }
   
   function startAjaxCalls(){
      getNewsTitle();
      setTimeout(function(){
         startAjaxCalls();
      }, 10000);
   }

</script>

</head>
<body>
    <h2>이곳은 MyMVC/ajaxstudy.do 페이지의 데이터가 보이는 곳입니다.</h2>
    <br/><br/>    
   <div align="center">
      <form name="searchFrm">
         회원명 : <input type="text" name="name" id="name" />
      </form>
      <br/>
      <button type="button" id="btn1">회원명단보기</button> &nbsp;&nbsp;
      <button type="button" id="btn2">사진보기</button> &nbsp;&nbsp;
      <button type="button" id="btn3">지우기</button> 
   </div>
   
   <div id="newsTitle" style="margin-top: 20px; margin-bottom: 20px;">신문기사</div> 
   
   <div id="container">
      <div id="memberInfo"></div>
      <div id="imgInfo"></div>
   </div>

</body>
</html>



