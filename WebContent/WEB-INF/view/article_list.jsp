<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>person</title>
		<link rel="stylesheet" href="css/article_list.css" />
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
					</h4>
						<div class="l-down-down">
					<ul>
					<c:forEach items="${article_brief_List}" var="alist" >
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
			<c:if test="${ not empty articleList}">
				<c:forEach items="${articleList}" var ="article">
				<div class="right-list">
					<h4 class="title"><a href="article_page.action?id=${article.article_id}">${article.article_name}</a></h4>
					<div id = "article_text">${article.article_text}</div>
				<span class="from">
				${article.article_from_flag==0?"原创":"转载"}&nbsp;&nbsp;${article.article_date}
				</span>
				<span class="count">浏览次数${article.article_view_count}&nbsp;&nbsp;点赞${article.artile_like_count}次</span>
				</div>
				</c:forEach>
				<div class="pager">
				
				<a class="pager_unit"  href="article_list_page.action?page=1">首页</a>
				<c:if test="${currentPage!=1 }">
				<a class="pager_unit"  href="article_list_page.action?page=${currentPage-1}">上一页</a>
				</c:if>
				<c:forEach  var="i"  begin="${end==sumPage?1:currentPage}" end="${end}">
				<c:choose>
					<c:when test="${currentPage==i}">
						<a class ="pager_unit" style="color:red;">${i}</a>
					</c:when>
					<c:otherwise>
						<a class="pager_unit" href="article_list_page.action?page=${i}">${i}</a>
					</c:otherwise>
				</c:choose>
				</c:forEach>
				<c:if test="${currentPage!=sumPage }">
				<a class="pager_unit" href="article_list_page.action?page=${currentPage+1}">下一页</a>
				</c:if>
				<a class="pager_unit"  href="article_list_page.action?page=${sumPage}">尾页</a>
				</div>
				</c:if>
			</div>
		</div>
		
		<div style="clear: both;"></div>
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
			</body>
			<script>
			var select = document.getElementById('select');
			select.addEventListener('click',function(){
				let ul = select.firstElementChild;
				ul.style.display == 'block'?ul.style.display ='none':ul.style.display ='block';
			})
		</script>
</html>