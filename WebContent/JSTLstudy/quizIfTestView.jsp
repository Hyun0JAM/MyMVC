<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>두개의 수를 입력받아서 더 큰수 알아오기 결과물</title>
</head>
<body>

<body>
	더큰수 :
	<c:if test="${num1 > num2}">
		${num1}
	</c:if>
	
	<c:if test="${num1 < num2}">
		${num2}
	</c:if>
	
	<c:if test="${num1 == num2}">
		${num1} 와 ${num1} 은 같습니다.
	</c:if>

</body>
</html>