<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
   table#tblMemberRegister {
   	    width: 93%;
   	    /* 선을 숨기는 것 */
   	    border: hidden;
   	    margin: 10px;
   }  
   table#tblMemberRegister td {
   		/* border: solid 1px gray;  */
   		line-height: 20px;
   		padding-top: 8px;
   		padding-bottom: 8px;
   }
   .star { color: red;
           font-weight: bold;
           font-size: 13pt;
   }
   #error_passwd { color: red; 
                   padding-left: 10px; 
                   margin-bottom: 5px;
   }
   div .head {
		width: 90%;
		height: 50px;
		border: 1px solid gray;
		margin-top: 10px;
		margin-bottom: 30px;
		padding-top: 10px;
		padding-bottom: 50px;
		background-color: #ffffe6;
		font-size: 18pt;
	}
</style> 
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script type="text/javascript" src="/MyWeb/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$(".error").hide();
		$("#error_passwd").hide();
		$(".requiredInfo").each(function(){
			$(this).blur(function(){
				var data = $(this).val().trim();
				if(data == "") {
					// 입력하지 않거나 공백만 입력했을 경우
					// alert("입력하지 않거나 공백만 입력했을 경우");
					$(this).parent().find(".error").show();
					$(":input").attr("disabled",true).addClass("bgcol");
					$(this).attr("disabled",false).removeClass("bgcol");
				}
				else{
					// 공백이 아닌 글자를 입력한 경우
					// alert("공백이 아닌 글자를 입력한 경우");
					$(this).parent().find(".error").hide();
					$(":input").attr("disabled",false).removeClass("bgcol");
				}
			});
		});// end of $(".requiredInfo").each()----------------
		
		$("#pwd").blur(function() {
			var passwd = $(this).val();
//			var regExp_PW = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g;
			// 또는
			var regExp_PW = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g); 
			// 숫자/문자/특수문자/ 포함 형태의 8~15자리 이내의 암호 정규식 객체 생성
			var bool = regExp_PW.test(passwd);
			/* 암호 정규표현식 검사를 하는 것 
			      정규표현식에 만족하면 리턴값은 true,
			      정규표현식에 틀리면 리턴값은 false */
			if(!bool) {
				$("#error_passwd").show();
				$(":input").attr("disabled",true).addClass("bgcol"); 
				$(this).attr("disabled",false).removeClass("bgcol");
				$(this).focus();
			}   
			else {
				$("#error_passwd").hide();
				$(":input").attr("disabled",false).removeClass("bgcol"); 
				$("#pwdcheck").focus();
			}
		});// end of $("#pwd").blur()-------------------
		
		
		$("#pwdcheck").blur(function(){
			var passwd = $("#pwd").val();
			var passwdCheck = $(this).val();
			if(passwd != passwdCheck) {
				$(this).parent().find(".error").show();
				$(":input").attr("disabled",true).addClass("bgcol");
				$(this).attr("disabled",false).removeClass("bgcol");
				$("#pwd").attr("disabled",false).removeClass("bgcol");
				$("#pwd").focus();
			}
			else {
				$(this).parent().find(".error").hide();
				$(":input").attr("disabled",false).removeClass("bgcol"); 
			}
		});// end of $("#pwdcheck").blur()--------------		

		
		$("#email").blur(function(){
			var email = $(this).val();
			var regExp_EMAIL = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;  
			// email 을 검사하는 정규식 객체 생성
			var bool = regExp_EMAIL.test(email);
			if(!bool) {
				$(this).parent().find(".error").show();
				$(":input").attr("disabled",true).addClass("bgcol");
				$(this).attr("disabled",false).removeClass("bgcol"); 
				$(this).focus();
			}
			else {
				$(this).parent().find(".error").hide();
				$(":input").attr("disabled",false).removeClass("bgcol");
			}
		});// end of $("#email").blur()--------------
		$("#hp1").val("${member.hp1}");
		$("#hp2").blur(function(){
			var hp2 = $(this).val();
			var bool1 = false;
			var regExp_HP2a = /[1-9][0-9][0-9]/g;
			// 3글자가 숫자이라면 들어오도록 검사해주는 정규표현식
			var bool1 = (hp2.length == 3) && regExp_HP2a.test(hp2);
			var bool2 = false;
			var regExp_HP2b = /[0-9][0-9][0-9][0-9]/g;
			// 숫자 4자리만 들어오도록 검사해주는 정규표현식
			var bool2 = (hp2.length == 4) && regExp_HP2b.test(hp2);
			if( !(bool1 || bool2) ) {
				$(this).parent().find(".error").show();
				$(":input").attr("disabled", true).addClass("bgcol");
				$(this).attr("disabled", false).removeClass("bgcol");
			}
			else {
				$(this).parent().find(".error").hide();
				$(":input").attr("disabled", false).removeClass("bgcol");
			}
		});// end of $("#hp2").blur()-------------
		
		$("#hp3").blur(function(){
			var hp3 = $(this).val();
			var regExp_HP3 = /\d{4}/g;
			// 숫자 4자리만 들어오도록 검사해주는 정규표현식
			var bool = regExp_HP3.test(hp3);
			if(!bool) {
				$(this).parent().find(".error").show();
				$(":input").attr("disabled", true).addClass("bgcol");
				$(this).attr("disabled", false).removeClass("bgcol");
			}
			else {
				$(this).parent().find(".error").hide();
				$(":input").attr("disabled", false).removeClass("bgcol");
			}			
		});// end of $("#hp3").blur()-------------

		
		$("#zipcodeSearch").click(function(){
			new daum.Postcode({
				oncomplete: function(data) {
				    $("#post1").val(data.postcode1);  // 우편번호의 첫번째 값     예> 151
				    $("#post2").val(data.postcode2);  // 우편번호의 두번째 값     예> 019
				    $("#addr1").val(data.address);    // 큰주소                        예> 서울특별시 종로구 인사로 17 
				    $("#addr2").focus();
				}
			}).open();
		});
	});// end of $(document).ready()--------------------
	
	function goEdit(event) {
		
		var flagBool = false;
		
		$(".requiredInfo").each(function(){
			var data = $(this).val().trim();
			if(data == "") {
				flagBool = true;
				return false;
			}
		});
		if(flagBool) {
			alert("필수입력란은 모두 입력하셔야 합니다.");
			event.preventDefault(); // click event 를 작동치 못하도록 한다.
			return;
		}
		else {
			var frm = document.editFrm;
			frm.method = "POST";
			frm.action = "memberEditEnd.do";
			frm.submit();
		}
	}// end of goEdit(event)------------------
</script>
<div class="row" align="center">
	<div class="col-md-12">
		<form name="editFrm">
		<div class="head" align="center">
			::: 회원수정 (<span style="font-size: 10pt; font-style: italic;"><span class="star">*</span>표시는 필수입력사항</span>) :::
		</div>
		<table id="tblMemberRegister">
			<tr>
				<td style="width: 20%; font-weight: bold;">성명&nbsp;<span class="star">*</span></td>
				<td style="width: 80%; text-align: left;">
				    <input type="hidden" name="idx" value="${member.idx }" readonly />
				    <input type="text" name="name" id="name" value="${member.name }" class="requiredInfo" required /> 
					<span class="error">성명은 필수입력 사항입니다.</span>
				</td>
			</tr>
			<tr>
				<td style="width: 20%; font-weight: bold;">비밀번호&nbsp;<span class="star">*</span></td>
				<td style="width: 80%; text-align: left;"><input type="password" name="pwd" id="pwd" class="requiredInfo" required />
					<span id="error_passwd">암호는 영문자,숫자,특수기호가 혼합된 8~15 글자로만 입력가능합니다.</span>
				</td>
			</tr>
			<tr>
				<td style="width: 20%; font-weight: bold;">비밀번호확인&nbsp;<span class="star">*</span></td>
				<td style="width: 80%; text-align: left;"><input type="password" id="pwdcheck" class="requiredInfo" required /> 
					<span class="error">암호가 일치하지 않습니다.</span>
				</td>
			</tr>
			<tr>
				<td style="width: 20%; font-weight: bold;">이메일&nbsp;<span class="star">*</span></td>
				<td style="width: 80%; text-align: left;"><input type="text" name="email" id="email" value="${member.email }" class="requiredInfo" placeholder="abc@def.com" /> 
				    <span class="error">이메일 형식에 맞지 않습니다.</span>
				</td>
			</tr>
			<tr>
				<td style="width: 20%; font-weight: bold;">연락처</td>
				<td style="width: 80%; text-align: left;">
				   <select name="hp1" id="hp1" style="width: 75px; padding: 0px; height: 20px; vertical-align: middle;">
						<option value="010" selected>010</option>
						<option value="011">011</option>
						<option value="016">016</option>
						<option value="017">017</option>
						<option value="018">018</option>
						<option value="019">019</option>
					</select>&nbsp;-&nbsp;
				    <input type="text" name="hp2" id="hp2" value="${member.hp2 }" size="6" maxlength="4" />&nbsp;-&nbsp;
				    <input type="text" name="hp3" id="hp3" value="${member.hp3 }" size="6" maxlength="4" />
				    <span class="error error_hp">휴대폰 형식이 아닙니다.</span>
				</td>
			</tr>
			<tr>
				<td style="width: 20%; font-weight: bold;">우편번호</td>
				<td style="width: 80%; text-align: left;">
				   <input type="text" name="post1" value="${member.post1 }" id="post1" size="6" maxlength="3" />
				   &nbsp;-&nbsp;
				   <input type="text" name="post2" value="${member.post2 }" id="post2" size="6" maxlength="3" />&nbsp;&nbsp;
				   <!-- 우편번호 찾기 -->
				   <img id="zipcodeSearch" src="MyMVC/images/b_zipcode.gif" style="vertical-align: middle;" />
				   <span class="error error_post">우편번호 형식이 아닙니다.</span>
				</td>
			</tr>
			<tr>
				<td style="width: 20%; font-weight: bold;">주소</td>
				<td style="width: 80%; text-align: left;">
				   <input type="text" id="addr1" name="addr1" value="${member.addr1 }" size="60" maxlength="100" /><br style="line-height: 200%"/>
				   <input type="text" id="addr2" name="addr2" value="${member.addr2 }" size="60" maxlength="100" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<button type="button" id="btnUpdate" style="margin-left: 40%; margin-top: 2%; width: 80px; height: 40px;" onClick="goEdit(event);"><span style="font-size: 15pt;">확인</span></button>
				</td>
			</tr>
		</table>
		</form>
	</div>
</div>
