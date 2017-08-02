<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>person-info</title>
		<link rel="stylesheet" href="css/person-info.css" />
		<script>
			
			function judgeFileType(){
			    var UPLOAD_FILE=$("#UPLOAD_FILE").val();//获取文本域的值
			    var fileType=UPLOAD_FILE.substr(UPLOAD_FILE.lastIndexOf(".")+1).toLowerCase();//获取文件后缀
			    var filterType="jpg,gif,bmp,png";//定义允许上传的文件后缀
			    if(filterType.indexOf(fileType)==-1){
			        alert("附件格式不正确,只能上传"+filterType+"格式");
			        return false;
			    }
			}
		</script>
	</head>
	<body>
		<div class="wrapper">
          <div class="main">
		<div id="head">
			<ul>
				<li id="first">CaseU|Java Web</li>
				<li id="sec">
					<div id="select">
					chose page
					<ul>
						<li>
							<a href="homepage.action">主页</a>	
						</li>
						<li>
							<a href="person_info_page.action">个人中心</a>
						</li>
						<li>
							<a href="article_list_page.action">文章列表</a>
						</li>
						<li>
							<a href="editor_page.action">发布文章</a>
						</li>
					</ul>
				</div>
				</li>
				<li id="thr" >${username == null ? '<a href="login_sign_page.action">登录</a>':username}</li>
				<li id="four">${username == null ? '<a href="login_sign_page.action">注册</a>':'<a href="login_out.action">注销</a>'}</li>
			</ul>
		</div>
		<div id="person-serch">
			<form action="search.action" method="post">
			<input type="text" value="${search}"name="search" id="textsearch"/>
			<input type="submit" value="搜索" id="search-button"/>
			</form>
		</div>
		
		<div id="content">
			<div id="left">
				<div id="l-up">
					<h4>
						头像信息
					</h4>
					<form method="post"  onsubmit="return judgeFileType()"  action="uploadImg.action" enctype="multipart/form-data" > 
					<table>
						<tr>
						<th>
						<img src="${headimg}" width="150px" height="150px"/>
						</th>
						</tr>
						<tr>
							<td class="change">
							<div id="file">
							<input type="file"  name="file" value="更改头像"  id="UPLOAD_FILE"/>
							<p id="inputfile">选择图片</p>
							</div>
							<input type="submit" value="保存头像" id="img_button"/>
							
							</td>
						</tr>
					</table>
					 </form>
						<!-- <iframe id="ifm" name="ifm" style="display:none" ></iframe> -->
						
				</div>
			</div>
			<div id="right-show">
				<h4>基本信息</h4>
				<form>
					<table class="info-table">
						<tr>
							<td class="info-left">邮箱</td>
							<td class="info-right">${userbean.email}</td>
						</tr>
						<tr>
							<td class="info-left">昵称</td>
							<td class="info-right">${username}</td>
						</tr>
						<tr>
							<td class="info-left">所在地</td>
							<td class="info-right">${userbean.place}</td>
						</tr>
						<tr>
							<td class="info-left">性别</td>
							<td class="info-right">${userbean.sex==0?'男':'女'}</td>
						</tr>
						<tr>
							<td class="info-left">生日</td>
							<td class="info-right">${userbean.birthday}</td>
						</tr>
					</table>
					<!-- <input type="button" value="保存" class="button" id="save"/> -->
					<input type="button" value="编辑" class="button" id="change" onclick="hidediv()"/>
					
				</form>
			</div>
			
			<div id="right-change">
				<h4>基本信息</h4>
				<form method="post" onsubmit="return save_check()" action="save_info.action">
					<table class="info-table">
						<tr>
							<td class="info-left">邮箱</td>
							<td class="info-right">
							<input type="email" value="${userbean==null?'':userbean.email}"  name="email"/>
							</td>
						</tr>
						<tr>
							<td class="info-left">昵称</td>
							<td class="info-right">
							<input type="text" value="${userbean==null?'':userbean.username}" id="username" name="username"/>
							</td>
						</tr>
						<tr>
							<td class="info-left">所在地</td>
							<td class="info-right">
							<input type="text" value="${userbean==null?'':userbean.place}" id="palce" name="place"/>
							</td>
						</tr>
						<tr>
							<td class="info-left">性别</td>
							<td class="info-right">
							男：
							<input type="radio" checked="checked" name="sex" value="0" />
							女：
							<input type="radio" name="sex" value="1" />
							</td>
						</tr>
						<tr>
							<td class="info-left">生日</td>
							<td class="info-right">
							<input type="text" value="${userbean==null?'yyyy-mm-dd':userbean.birthday}" id="date" name="date"/>
							</td>
						</tr>
					</table>
					<input type="hidden" value="${userbean.id}" name="id"/>
					<input type="hidden" value="${login_checkword}" name="security_check" id="security_check"/>
					<input type="submit" value="保存" class="button" id="save" onclick="save_check()"/>
					<input type="reset" value="重置" class="button" id="change" onclick="hidediv()"/>
				</form>
			</div>
			<script>
				function hidediv(){
					document.getElementById("right-show").style.display="none";
					document.getElementById("right-change").style.display="block";
				}
				function save_check(){
					var str = document.getElementById("date").value;
					var username = document.getElementById("username").value;
					var place = document.getElementById("place").value;
					if(username.length>0&&username.length<20&&str.match(/^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/)){
						return true;
					} else {
						alert("输入不合法");
					return false;
					} 
					/* document.getElementById("right-show").style.display="block";
					document.getElementById("right-change").style.display="none"; */
				}
			</script>
		</div>
		<div style="clear: both;"></div>
		<div class="push"></div>
		</div>
		</div>
		
		<footer class="footer">
		<div id="footer-top">
			<ul>
				<li>首页</li>
				<li class="nbsp">|</li>
				<li>业务介绍</li>
				<li class="nbsp">|</li>
				<li>网站声明</li>
				<li class="nbsp">|</li>
				<li>关于我们</li>
			</ul>
		</div>
		<div id="footer-bottom">
			<table>
				<tr>
					<td>
			<img src="img/2.png" width="85px" height="85px"/>
					</td>
					<td>
						<p>微信号:wyh106970162<br />
						手&nbsp;&nbsp;&nbsp;机:15823302467
						</p>
					</td>
				</tr>
			</table>
		</div>
		</footer>
		<script>
			var select = document.getElementById('select');
			select.addEventListener('click',function(){
				let ul = select.firstElementChild;
				ul.style.display == 'block'?ul.style.display ='none':ul.style.display ='block';
			})
		</script>
	</body>
</html>
