<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<html>

	<head>
		<meta content="text/html;charset=UTF-8" />
		<title>登录-注册</title>
		<script src="js/jquery-2.2.4.min.js"></script>
		<link rel="stylesheet" type="text/css" href="css/login.css" />
		<script src="js/md5.js"></script>  
		<script type="text/javascript" src="js/login.js"></script>
		
		<script>
			$(document).ready(function() {
				$(".form").slideDown(500);
				$("#landing").addClass("border-btn");
				$("#registered").click(function() {
					$("#landing").removeClass("border-btn");
					$("#landing-content").hide(500);
					$(this).addClass("border-btn");
					$("#registered-content").show(500);
				})
				$("#registeredtxt").click(function() {
					$("#landing").removeClass("border-btn");
					$("#landing-content").hide(500);
					$(this).addClass("border-btn");
					$("#registered-content").show(500);
				})
				$("#landing").click(function() {
					$("#registered").removeClass("border-btn");
					$(this).addClass("border-btn");
					$("#landing-content").show(500);
					$("#registered-content").hide(500);
				})
			});
			var xmlHttpReq;
			function createXmlHttpRequest(){
				if(window.XMLHttpRequest){
					xmlHttpReq = new XMLHttpRequest();
				}else{
					xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
				}
			}
			function checkcode(){
				var email = document.getElementById("email_text").value;
				var regex =/^([0-9A-Za-z\-_\.]+)@([0-9a-z]+\.[a-z]{2,3}(\.[a-z]{2})?)$/g;
				if(regex.test(email)){
				createXmlHttpRequest();
				xmlHttpReq.onreadystatechange=handle;
				var url = "check.action";
				var postContent ="email=" + encodeURIComponent(email);
				xmlHttpReq.open("post",url,true);
				xmlHttpReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded","charset=utf-8");
				xmlHttpReq.send(postContent);
				}
				else{alert("邮箱不合法！");}
			}
		
			function handle(){
				if(xmlHttpReq.readyState==4&&xmlHttpReq.status==200){
					var res = xmlHttpReq.responseText;
					var result = document.getElementById("result");
					result.innerHTML = res;
					document.getElementById("email").style.display="none";
					document.getElementById("button_check").style.display="none";
					document.getElementById("checkword").style.display="block";
					document.getElementById("result_div").style.display="block";
				}
			}
			function req(){
				
				var flag = document.getElementById("flag").innerHTML;
			    var login_flag = document.getElementById("login_flag").innerHTML;
			    if((flag=="false")||(flag=="checkfalse")){
					$('#registered').trigger("click");
					document.getElementById("hint").style.display="block";
				}
			    if(login_flag=="false"){
			    	/*document.getElementById("registeredtxt").style.color="red";*/
			    	document.getElementById("registeredtxt").innerHTML = "用户名或密码错误！";
			    }
			    if(login_flag=="fail"){
			    	document.getElementById("registeredtxt").innerHTML = "验证码错误！";
			    }
				
			}
			
	</script>
		
	</head>
	<body onload="req()">
		<div class="form">
			<div id="landing" >
				登录
			</div>
		
			<div id="registered">注册</div>
			<div class="fix"></div>
			<div id="landing-content">
			<form  method="post" onsubmit="return login_sure()" action="login.action">
				<div id="photo"><p id="check-font">${creatword}</p></div>
				<div class="inp"><input type="text" placeholder="验证码" id="in-check-word" name="checkword"/></div>
				<div class="inp"><input type="text" placeholder="用户名" id="username" name="username"/></div>
				<div class="inp">
				<input type="password" placeholder="密码" id="password" />
				<input type="hidden" name="password" id="login_send_password" name="password"/>
				</div>
				<input type="submit" value="登录" id="login_button" class="login" onclick="login_sure()"/>
				<div id="bottom"><span id="registeredtxt">没有账号？？？戳我啊</span></div>
				<p id="login_flag" style="display:none">${login_flag}</p>
			</form>
			</div>
			<form method="post" onsubmit="return sign_sure()" action="sign.action">
			<div id="registered-content">
				<div class="inp" style="display:none" id="result_div"><p id="result"></p></div>
				<div class="inp" id="email"><input type="email" placeholder="请输入邮箱"  name="email" id="email_text"/></div>
				<div class="inp" style="display:none" id="checkword"><input type="text" placeholder="请输入验证码" name="checkword"/></div>
				<div class="inp" id="button_check"><input type="button" value="获得验证码"  onclick="checkcode()" /></div>
				<div class="inp" ><input type="text" placeholder="请输入用户名" id="sign_username" name="username"/></div>
				<div class="inp"><input type="password" placeholder="请输入密码(6-10位)" id="sign_password1" /></div>
				<div class="inp">
				<input type="password" placeholder="请再次输入密码" id="sign_password2"/>
				<input type="hidden" name="password" id="send_password"/>
				</div>
				<input type="submit" value="立即注册" class="login" onclick="sign_sure()"/>
				<p id="hint" style="display:none">${flag=='false'?'用户名或邮箱已被占用':'验证码错误'}</p>
				<p id="flag" style="display:none">${flag}</p>
			</div>
			</form>
		</div>
			
	</body>
</html>