<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 为了使用jstl.jar这个工具 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="utf-8" />
		<title>article</title>
		<link rel="stylesheet" href="css/article.css" />
		<script>
				function like(we){
				var xmlhttp;
				if (window.XMLHttpRequest)
				  {// code for IE7+, Firefox, Chrome, Opera, Safari
				  xmlhttp=new XMLHttpRequest();
				  }
				else
				  {// code for IE6, IE5
				  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
				  }
				xmlhttp.onreadystatechange=function(){
				if(xmlhttp.readyState==4&&xmlhttp.status==200){
					var res = xmlhttp.responseText;
					document.getElementById("article_like_count").innerHTML=res;
					}
				}
				var url = "like.action";
				xmlhttp.open("POST",url,true);
				xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
				var id =document.getElementById("article_id").innerHTML;
				var postcontent ="id="+id;
				xmlhttp.send(postcontent);
				we.setAttribute("onclick","");
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
						个人资料
						<span  class="h4-span">
							<a href="person_info_page.action">编辑></a>
						</span>
					</h4>
					
					<table>
						<tr><th>
					<img src="${headimg}" width="150px" height="150px"/>
						</th></tr>
						<tr>
							<td>用户名:${username}</td>
						</tr>
						<tr>
							<td>访问量:${Sum_view_count}次</td>
						</tr>
						<tr>
							<td>文章:${ Sum_article_count}篇</td>
						</tr>
					</table>
				</div>
				<div id="l-down">
						<h4>
						个人文章
						<span class="h4-span">
							<a>更多></a>
						</span>
					</h4>
						<div class="l-down-down">
					<ul>
					<c:forEach items="${articleList}" var="alist" >
					<li>
					<a class="list" href="article_page.action?id=${alist.article_id}">
					${alist.article_name}
					</a>
					<span class="down-count">浏览${alist.article_view_count}次</span>
					</li>
					</c:forEach>
					</ul>
					</div>
				</div>
			</div>
			<div id="right">
				<div class="article-top">
				<h3 class="title">${article.article_name}</h3>
				<h1 style="display: none; " id="article_id">${article.article_id}</h1>
				<span class="from">${article.article_from_flag==0?"原创":"转载"}<a href='${article.article_from}'>${article.article_from}</a></span><span class="from">${article.article_date}</span>
				<span class="count">浏览次数${article.article_view_count}&nbsp;&nbsp;下载${article.file_count}次</span>
				</div>
				<div class="article-content">
					<p id="content_text">${article.article_text}</p>
					<p id="fujian">
					${message}
					<c:if test="${not empty article.file_path}">
					(附件)${article.file_size}M
					<a href="download.action?id=${article.article_id}">下载</a>
					</c:if>
					</p>
					<div class="like" onclick="like(this)">
						<dl id="Digg" class="diggable">
							<dt>顶</dt>
							<dd id="article_like_count">${article.artile_like_count}</dd>
						</dl>
					</div>
					<ul class="article-next-prev">
						<li class="article-prev"><span>上一篇</span>
							<c:if test="${empty article.up_id}">
							<span>亲，前面没有了=_=</span>
							</c:if>
							<c:if test="${not empty article.up_id}">
							<a href="article_page.action?id=${article.up_id}">${article.up_name}</a>
							</c:if>
						</li>
						<li class="article-prev"><span>下一篇</span>
						<c:choose>
							<c:when test="${not empty article.next_id}">
								<a href="article_page.action?id=${article.next_id}">${article.next_name}</a>
							</c:when>
							<c:otherwise>
								<span>亲，已经到最后了=_=</span>
							</c:otherwise>
						</c:choose>
						</li>
					</ul>
				</div>
			</div>
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
			<img src="img/2.png" width="85px" height="85px"/></td>
		<td><p>微信号:wyh106970162<br />
		手&nbsp;&nbsp;&nbsp;机:15823302467</p></td>
					
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