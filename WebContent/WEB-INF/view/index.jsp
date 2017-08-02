<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>homepage</title>
		<link rel="stylesheet" href="css/index.css" />
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
		<div id="serch">
			<form action="search.action" method="post">
			<input type="text" value="${search}"name="search" id="textsearch"/>
			<input type="submit" value="搜索" id="search-button"/>
			</form>
		</div>
		<div id="mid">
		<div id="left">
			<div class="midleft">
				<div class="up">
					<span>最新推荐</span>
					<a href="View_list_page.action">更多></a>
				</div>
					<div class="down">
		<c:forEach items="${articles_byView }" var="article">
						<div class="down-every">
					<h4 class="title">
						<a href="article_page_By_view.action?id=${article.article_id}">${article.article_name}</a>
					</h4>
						<div class="article_text">${article.article_text}</div>
					<span id="from">
					${article.article_from_flag==0?"原创":"转载"}<a href='${article.article_from}'>${article.article_from}</a>
					${article.article_date}
					</span>
					<span id="count">浏览次数${article.article_view_count}&nbsp;&nbsp;点赞${article.artile_like_count}次</span>
						</div>
		</c:forEach>
					</div>
			</div>
		<div style="clear: left;"></div>
		<div class="midleft">
			<div class="up">
				<span>最新动态</span>
				<a href="Time_list_page.action">更多></a>
			</div>
		
		<div class="down">
		<c:forEach items="${articles_byTime}"  var="article">
			<div class="down-every">
			<h4 class="title"><a href="article_page_By_time.action?id=${article.article_id}">${article.article_name}</a></h4>
			<div class="article_text">${article.article_text}</div>
				<span id="from">
		${article.article_from_flag==0?"原创":"转载"}<a href='${article.article_from}'>${article.article_from}</a>
		${article.article_date}
		</span>
		<span id="count">浏览次数${article.article_view_count}&nbsp;&nbsp;点赞${article.artile_like_count}次</span>
			</div>
		</c:forEach>
		</div>
		</div>
		</div>
		<div id="right">
		<div class="midright">
		<div class="up">
		<span>点赞最多</span><a href="Like_list_page.action">更多></a>
		</div>
		<div class="down"><ul>
		<li>
		<c:forEach items="${articles_bylike}" var="article">
			
				<a class="list" href="article_page_By_like.action?id=${article.article_id}">${article.article_name }</a>
				<span class="down-count">点赞${article.artile_like_count}次</span>
			
		</c:forEach>
		</li>
		</ul>
		</div>
		</div>
		<div class="midright">
		<div class="up">
		<span>下载最多</span><a href = "Download_list_page.action" >更多></a>
		</div>
		<div class="down">
		<ul>
		<c:forEach items="${articles_byDownload}" var="article">
			<li>
				<a class="list" href="article_page_By_download.action?id=${article.article_id}">${article.article_name }</a>
				<span class="down-count">下载${article.file_count}次</span>
			</li>
		</c:forEach>
		</ul>
		</div>
		</div>
		</div>
		</div>
		<div style="clear: left;"></div>
		<div class="push"></div>
		</div>
		</div>
		<footer id="footer">
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
