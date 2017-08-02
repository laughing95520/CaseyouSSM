<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<title>Editor</title>
		<link rel="stylesheet" href="css/editor.css" />
		<link rel="stylesheet" type="text/css" href="css/wangEditor.min.css">
     	<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
    	<script type="text/javascript" src="js/wangEditor.min.js"></script>
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
				<li id="thr">注册</li>
				<li id="four">登录</li>
			</ul>
		</div>
		
		<div style="clear: both;"></div>
		<div id="editor">
		<form method="post" enctype="multipart/form-data" action="publish.action">
			<div id="editor_head">
			
			<p>分类：<select name="article_class">
					<option value="java">java</option>
					<option value="mysql">mysql</option>
					<option value="servlet">servlet</option>
					<option value="web">web</option>
					</select>
			<p/>
			<p>文章名：<input type="text" name="article_name" class="margin"/><p/>
			<label for="original"><input type="radio" name="article_from_flag" class="margin" id="original" value="0"/>原创</label>
			<label for="reprint"><input type="radio" name="article_from_flag" class="margin" id="reprint" value="1"/>转载</label>
			<p class="margin">原文链接</p>
			<input type="text" class="margin" name="article_from"/>
			<h4 class="margin">正文:</h4>
			</div>
			<textarea id="div1" name="article_text" >
			请输入内容...
    		</textarea>
			<div id="fujian">
				附件:<input type="file" value="选择上传文件" name="file"/>
			</div>
    		<input type="submit" value="发布" id="submit_button1" class="sub_button"/>
    <script type="text/javascript">
	var editor = new wangEditor('div1');
	//上传图片
	editor.config.uploadImgUrl = 'uploadArticleImg.action';
	editor.create();
    </script> 
		</form>
		</div>
		
		<div class="push"></div>
		</div>
		</div>
		<div style="clear: both;"></div>
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
