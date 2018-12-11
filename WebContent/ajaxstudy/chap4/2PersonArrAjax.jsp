<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-3.3.1.min.js"></script> 
<script type="text/javascript">
	$(document).ready(function(){
		$("#displayArea").hide();
		$("#btn").click(function(){
			$.ajax({
				url:"2personArrJSON.do",
				//type:"GET",
				//data:,form_data
				dataType:"JSON",
				success:function(json){
					$("#displayArea").show();
					var html = "";
					if(json.length>0){
						$.each(json,function(entryIndex,entry){ // 관습상 쓴다 entry
							html += "<tr><td>"+(entryIndex+1)+"</td>"+ //index 0부터 시작
									"<td>"+entry.NAME+"</td>"+
									"<td>"+entry.AGE+"</td>"+
									"<td>"+entry.PHONE+"</td>"+
									"<td>"+entry.EMAIL+"</td>"+
									"<td>"+entry.ADDR+"</td></tr>";
						});
					}
					$("#personinfo").append(html);
				},
				error:function(request, status, error){// 실패했다면 어디가 틀렸는지 alert를 띄워준다.
	               alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	            }
			});
		});
	});
</script>
<div>
	<button type="button" id="btn">회원보기</button>
</div>
<h3>회원내역</h3>
<div id="displayArea">
    <table>
	<thead>
		<tr>
			<th>번호</th>
			<th>성명</th>
			<th>나이</th>
			<th>전화번호</th>
			<th>이메일</th>
			<th>주소</th>
		</tr>
	</thead>
	<tbody id="personinfo"></tbody>
	</table>	
</div>