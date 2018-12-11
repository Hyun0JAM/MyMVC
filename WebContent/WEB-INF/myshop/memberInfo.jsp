<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
#p{
	color: black;
	line-height:180%;
	font-size:0.9em;}
li{	text-align:left;}
button{
	margin-top:3%; 
	border:none; 
	padding: 5px 10px; 
	color: black;
	font-size:0.8em;
	font-weight: bold;
	border-radius: 3px;}
</style>
<div align="center" style="margin-top: 30px;">
	<h2 class="font" style="font-weight:bold">"회원 상세 정보"</h2>
	<div class="font" style="border:1px dotted; margin-top:3%; width:450px; padding:30px;">
	<ul>
		<li><span id="p">회원번호 : </span>&nbsp;${member.idx}<br/></li>
		<li><span id="p">회원명 : </span>&nbsp;${member.name }<br/></li>
		<li><span id="p">아이디 : </span>&nbsp;${member.userid }<br/></li>
		<li><span id="p">연락처 : </span>&nbsp;${member.hp1 }-${member.hp2 }-${member.hp3 }<br/></li>
		<li><span id="p">성별 : </span>&nbsp;${member.showGender }<br/></li>
		<li><span id="p">주소 : </span>&nbsp;(${member.addr1 })${member.addr2 } <br/></li>
		<li><span id="p">생일 : </span>&nbsp;${member.birthyyyy }/${member.birthmm }/${member.birthdd }<br/></li>
		<li><span id="p">가입일자 : </span>&nbsp;${member.registerday }<br/></li>
		<li><span id="p">코인 : </span>&nbsp;${member.coin }<br/></li>
		<li><span id="p">포인트 : </span>&nbsp;${member.point }<br/></li>
	</ul>
	</div>
	<button type="button" onClick="javascript:self.close();">닫기</button>
</div>