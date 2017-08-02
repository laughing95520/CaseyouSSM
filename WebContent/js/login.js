function sign_sure(){
        	var name=document.getElementById("sign_username").value;
        	var pass = document.getElementById("sign_password1").value;
        	var passsure = document.getElementById("sign_password2").value;
        	if(name!=""&&pass!=""&&passsure!=""&&pass.length>=6&&pass.length<=15&&name.length<20){
		        	if(pass!=passsure){
		        		alert("请确认两次输入的密码一致");
		           return false;
		        	}else{
		        		var hash = hex_md5(pass);
		        		document.getElementById("send_password").value=hash;
     	}
		       }
	      else{
		      	alert("输入不合法");
		      	return false;
		      }
        }
function login_sure(){
	var name = document.getElementById("username").value;
	var pass = document.getElementById("password").value;
	var varify = document.getElementById("in-check-word").value;
	if(name!=""&&pass!=""&&varify!=""){
		var hash = hex_md5(pass);
		document.getElementById("login_send_password").value=hash;
	}else{
		alert("内容不合法");
		return false;
	}
	
	
}

