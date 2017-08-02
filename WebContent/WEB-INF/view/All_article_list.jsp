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
			
				<c:forEach items="${articleList}" var ="article">
				<div class="right-list">
					<h4 class="title">
					<c:if test="${action=='like'}">
						<a href="article_page_By_like.action?id=${article.article_id}">
							${article.article_name}
						</a>
					</c:if>
					<c:if test="${action=='download'}">
						<a href="article_page_By_download.action?id=${article.article_id}">
							${article.article_name}
						</a>
					</c:if>
					<c:if test="${action=='view'}">
						<a href="article_page_By_view.action?id=${article.article_id}">
							${article.article_name}
						</a>
					</c:if>
					<c:if test="${action=='time'}">
						<a href="article_page_By_time.action?id=${article.article_id}">
							${article.article_name}
						</a>
					</c:if>
					<c:if test="${action=='search'}">
						<a href="article_page_By_search.action?id=${article.article_id}">
							${article.article_name}
						</a>
					</c:if>
					</h4>
					<div id ="article_text">${article.article_text}</div>
				<span class="from">
				${article.article_from_flag==0?"原创":"转载"}&nbsp;&nbsp;${article.article_date}
				</span>
				<span class="count">浏览次数${article.article_view_count}&nbsp;&nbsp;
				点赞${article.artile_like_count}次&nbsp;&nbsp;下载${article.file_count}次</span>
				</div>
				</c:forEach>
				<div class="pager">
				<c:choose>
				
				<c:when test="${action=='search'}">
					<a class="pager_unit"  href="searchPage.action?page=1&&search=${search}">首页</a>
					<c:if test="${currentPage!=1 }">
					<a class="pager_unit"  href="searchPage.action?page=${currentPage-1}&&search=${search}">上一页</a>
					</c:if>
					<c:forEach  var="i"  begin="${end==sumPage?1:currentPage}" end="${end}">
					<c:choose>
						<c:when test="${currentPage==i}">
							<a class ="pager_unit" style="color:red;">${i}</a>
						</c:when>
						<c:otherwise>
							<a class="pager_unit" href="searchPage.action?page=${i}&&search=${search}">${i}</a>
						</c:otherwise>
					</c:choose>
					</c:forEach>
					<c:if test="${currentPage!=sumPage }">
							<a class="pager_unit" href="searchPage.action?page=${currentPage+1}&&search=${search}">下一页</a>
					</c:if>
					<a class="pager_unit"  href="searchPage.action?page=${sumPage}&&search=${search}">尾页</a>
				</c:when>
				
				
				
				
				
				<c:when test="${action=='like'}">
					<a class="pager_unit"  href="Like_list_page.action?page=1">首页</a>
					<c:if test="${currentPage!=1 }">
					<a class="pager_unit"  href="Like_list_page.action?page=${currentPage-1}">上一页</a>
					</c:if>
					<c:forEach  var="i"  begin="${end==sumPage?1:currentPage}" end="${end}">
					<c:choose>
						<c:when test="${currentPage==i}">
							<a class ="pager_unit" style="color:red;">${i}</a>
						</c:when>
						<c:otherwise>
							<a class="pager_unit" href="Like_list_page.action?page=${i}">${i}</a>
						</c:otherwise>
					</c:choose>
					</c:forEach>
					<c:if test="${currentPage!=sumPage }">
					<a class="pager_unit" href="Like_list_page.action?page=${currentPage+1}">下一页</a>
					</c:if>
					<a class="pager_unit"  href="Like_list_page.action?page=${sumPage}">尾页</a>
				</c:when>
				<c:when test="${action=='download' }">
					<a class="pager_unit"  href="Download_list_page.action?page=1">首页</a>
					<c:if test="${currentPage!=1 }">
					<a class="pager_unit"  href="Download_list_page.action?page=${currentPage-1}">上一页</a>
					</c:if>
					<c:forEach  var="i"  begin="${end==sumPage?1:currentPage}" end="${end}">
					<c:choose>
						<c:when test="${currentPage==i}">
							<a class ="pager_unit" style="color:red;">${i}</a>
						</c:when>
						<c:otherwise>
							<a class="pager_unit" href="Download_list_page.action?page=${i}">${i}</a>
						</c:otherwise>
					</c:choose>
					</c:forEach>
					<c:if test="${currentPage!=sumPage }">
					<a class="pager_unit" href="Download_list_page.action?page=${currentPage+1}">下一页</a>
					</c:if>
					<a class="pager_unit"  href="Download_list_page.action?page=${sumPage}">尾页</a>
				</c:when>
				
				<c:when test="${action=='view'}">
					<a class="pager_unit"  href="View_list_page.action?page=1">首页</a>
					<c:if test="${currentPage!=1 }">
					<a class="pager_unit"  href="View_list_page.action?page=${currentPage-1}">上一页</a>
					</c:if>
					<c:forEach  var="i"  begin="${end==sumPage?1:currentPage}" end="${end}">
					<c:choose>
						<c:when test="${currentPage==i}">
							<a class ="pager_unit" style="color:red;">${i}</a>
						</c:when>
						<c:otherwise>
							<a class="pager_unit" href="View_list_page.action?page=${i}">${i}</a>
						</c:otherwise>
					</c:choose>
					</c:forEach>
					<c:if test="${currentPage!=sumPage }">
					<a class="pager_unit" href="View_list_page.action?page=${currentPage+1}">下一页</a>
					</c:if>
					<a class="pager_unit"  href="View_list_page.action?page=${sumPage}">尾页</a>
				</c:when>
				
				<c:otherwise>
					<c:if test="${sumPage!=null&&sumPage!=0}">
					<a class="pager_unit"  href="Time_list_page.action?page=1">首页</a>
					</c:if>
					<c:if test="${currentPage!=1&&currentPage!=null }">
					<a class="pager_unit"  href="Time_list_page.action?page=${currentPage-1}">上一页</a>
					</c:if>
					<c:forEach  var="i"  begin="${end==sumPage?1:currentPage}" end="${end}">
					<c:choose>
						<c:when test="${currentPage==i}">
							<a class ="pager_unit" style="color:red;">${i}</a>
						</c:when>
						<c:otherwise>
							<a class="pager_unit" href="Time_list_page.action?page=${i}">${i}</a>
						</c:otherwise>
					</c:choose>
					</c:forEach>
					<c:if test="${currentPage!=sumPage }">
					<a class="pager_unit" href="Time_list_page.action?page=${currentPage+1}">下一页</a>
					</c:if>
					<c:if test="${sumPage!=null&&sumPage!=0}">
					<a class="pager_unit"  href="Time_list_page.action?page=${sumPage}">尾页</a>
					</c:if>
				</c:otherwise>
				</c:choose>
				</div>
			</div>
		</div>
		
		<div style="clear: both;"></div>
		<div class="push"></div>
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