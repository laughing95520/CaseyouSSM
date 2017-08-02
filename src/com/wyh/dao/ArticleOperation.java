package com.wyh.dao;

import java.util.List;


import com.wyh.bean.ArticleBean;

public   interface ArticleOperation {
	//插入文章信息
	public   int insert(ArticleBean articleBean);
	
	//获得文章在该作者的第几行
	public   int getArticle_index(String id, String username);
	
	//根据文章在该作者的第几行，获得上下一篇文章的信息
	public   List<ArticleBean> getArticle(String username,int i);
	
	//插入文章附件信息
	public   int insert_file(ArticleBean articleBean);
	
	//获得文章附件路径
	public   String getfile_path(String id);
	
	//更新附件下载次数
	public   int update_file_count(String id);
	
	//更新浏览次数
	public   int update_view_count(String id,int value);

	//更新浏览次数
	//public   ArticleBean update_view_count(String id,int value);
	
	//确定浏览表中有无该文章
	public   int sure_view(String id);
	
	//浏览表中新建该文章
	public   int insert_view_count(String id, int value);
	
	//更新点赞表
	public abstract int insert_like_count(String id,String username);
	
	//点赞后获得点赞量
	public abstract int artile_like_count(String id);
	
	
	//获得该作者浏览量前10的文章简单信息
	public abstract List<ArticleBean> getArticleList(String username);
	
	//用户文章总数，浏览总数
	public abstract ArticleBean getSumCount(String uString);
	
	//分页文章信息列表 按时间排序
	public abstract List<ArticleBean> getPageList(String username, int i);
	
	//点赞量分页
	public abstract List<ArticleBean> getArticle_like_list(int page);
	
	//下载量分页
	public abstract List<ArticleBean> getArticle_download_list(int i);
	
	//浏览量分页
	public abstract List<ArticleBean> getArticle_view_list(int i);
	
	//时间分页
	public abstract List<ArticleBean> getArticle_time_list(int i);
	
	//获得数据库文章总数
	public abstract int Article_Num();
	
	//根据下载量获得文章位置，第几行
	public abstract int getArticle_index_By_download(String id);
	
	//时间排序，index获得文章信息
	public abstract List<ArticleBean> getArticleByTime(int index);
	
	//根据时间排序获得文章位置，第几行
	public abstract int getArticle_index_By_time(String id);
	
	//根据浏览量获得文章位置
	public abstract int getArticle_index_By_view(String id);
	
	//根据浏览量获得文章
	public abstract List<ArticleBean> getArticleByView(int index);
	
	//根据下载量...第几行获得上一篇下一篇文章信息
	public abstract List<ArticleBean> getArticleByDownload(int index);
	
	//根据点赞量获得文章所处位置，第几行
	public abstract int getArticle_index_By_like(String id);
	
	//根据点赞量...第几行获得上一篇下一篇文章信息
	public abstract List<ArticleBean> getArticleByLike(int index);

	//获得点赞量前10的文章信息list
	public abstract List<ArticleBean> getBrief_Article_List_ByLike();

	//获得下载量前10的文章信息list
	public abstract List<ArticleBean> getBrief_Article_list_ByDownload();

	//浏览量前三的文章信息
	public abstract List<ArticleBean> getBrief_Article_By_View();

	//获得最新三篇文章信息
	public abstract List<ArticleBean> getBrief_Article_By_Time();

	//获得所有文章
	public abstract List<ArticleBean> getAllArticle();

	//获得单篇文章
	public abstract ArticleBean getArticleById(String id);

	



	
	
	

	

	
	
	
	
	
	

}
