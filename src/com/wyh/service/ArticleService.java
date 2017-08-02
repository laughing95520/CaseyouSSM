package com.wyh.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyh.bean.ArticleBean;
import com.wyh.dao.ArticleOperation;

@Service
public class ArticleService {
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	Logger log = Logger.getLogger(ArticleService.class);
	  public SqlSessionFactory getSqlSessionFactory() {
			return sqlSessionFactory;
		}

		public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
			this.sqlSessionFactory = sqlSessionFactory;
		}

	
	public int insert(ArticleBean articleBean){
		int count = 0;
		SqlSession sqlsession=null;
		if (null != articleBean && articleBean.getfile_path()!=null) {
		//多表更新时用事务操作
		try {
			
			   sqlsession = sqlSessionFactory.openSession();
			   //开启事务
			   sqlsession.commit(false);
				ArticleOperation articleOperation = (ArticleOperation)sqlsession.getMapper(ArticleOperation.class);
				count = articleOperation.insert(articleBean);
				count +=articleOperation.insert_file(articleBean);
				//事务提交
				sqlsession.commit();
				return count;
				
		} catch (Exception e) {
			//事务回滚
			log.error("发布事务出错"+e.getMessage());
			sqlsession.rollback();
			return count;
		}
		finally {
			//恢复事务
			sqlsession.commit(true);
			sqlsession.close();
		}
		
		} else {
			SqlSession sqlSession = null;
			int i =0;
			try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			i = articleOperation.insert(articleBean);
			return i;
			} finally {
				sqlSession.close();
			}
		}
	
	}
	public List<ArticleBean> getArticle(String username,int i){
		SqlSession sqlSession=null;
		List<ArticleBean> articleBean = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = (ArticleOperation)sqlSession.getMapper(ArticleOperation.class);
			articleBean = articleOperation.getArticle(username,i);
			if (articleBean==null) {
				articleBean = new ArrayList<ArticleBean>(1);
				log.error("获得文章出错");
			}
			return articleBean;
		} finally {
			sqlSession.close();
		}
	}
	public String getfile_path(String id) {
		SqlSession sqlSession =null;
		String file_path = "";
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = (ArticleOperation)sqlSession.getMapper(ArticleOperation.class);
			file_path = articleOperation.getfile_path(id);
			if (file_path=="") {
				log.error("获得文件路径出错");
			}
			return file_path;
		} finally {
			sqlSession.close();
		}
	}
	public int update_file_count(String id){
		SqlSession sqlSession = null;
		int i = 0;
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			i = articleOperation.update_file_count(id);
			return i;
		} finally {
			sqlSession.close();
		}
	}
	//TODO:更新浏览次数
	public int update_view_count(String id,int value){
		SqlSession sqlSession = null;
		int count = 0;
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			count = articleOperation.update_view_count(id, value);
			
			//ViewLRUCache.updateViewLRUCache(articleBean, value);
			
			return count;
		} finally {
			
			//TODO:确保sqlsession关闭
			sqlSession.close();
		}
		
	}
	public int insert_view_count(String id, int value){
		int count=0;
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			count = articleOperation.insert_view_count(id,value);
		} finally {
			sqlSession.close();
		}
		return count;
	}
	public int sure_view(String id){
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			int count = articleOperation.sure_view(id);
			return count;
		} finally {
			sqlSession.close();
		}
	}
	public int insert_like_count(String id,String username){
		int count = 0;
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			count = articleOperation.insert_like_count(id,username);
			return count;
		} finally {
			sqlSession.close();
		}
		
	}
	
	public int artile_like_count(String id){
		int count = 0;
		SqlSession sqlSession = null;
		try {
			sqlSession =sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			count = articleOperation.artile_like_count(id);
			return count;
		} finally {
			sqlSession.close();
		}
	
		
	}
	public List<ArticleBean> getArticleList(String username) {
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			List<ArticleBean> map = articleOperation.getArticleList(username);
			if (map == null) {
				log.error("获得该用户文章列表出错");
				map = new ArrayList<ArticleBean>(1);
			}
			return map;
			
		} finally {
			sqlSession.close();
		}
	}
	public ArticleBean getSumCount(String uString){
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			ArticleBean count = articleOperation.getSumCount(uString);
			if (count == null) {
				log.error("获得该用户文章总数出错");
				count = new ArticleBean();
			}
			return count;
		} finally {
			sqlSession.close();
		}
	}
	public List<ArticleBean> getPageList(String username, int i) {
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			List<ArticleBean> map = articleOperation.getPageList(username,i);
			if (map == null) {
				log.error("分页该用户文章信息列表出错");
				map = new ArrayList<ArticleBean>();
			}
			return map;
		} finally {
			sqlSession.close();
		}
		
	}
	public List<ArticleBean> getArticle_like_list(int page) {
		SqlSession sqlSession = null;
		try {
			sqlSession =sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			List<ArticleBean> map = articleOperation.getArticle_like_list(page);
			if (map == null) {
				log.error("by点赞获得分页出错");
				map =  new ArrayList<ArticleBean>();
			}
			return map;
		} finally {
			sqlSession.close();
		}
	}
	public int Article_Num() {
		int count = 0;
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			count = articleOperation.Article_Num();
			return count;
		} finally {
			sqlSession.close();
		}
	}
	public int getArticle_index_By_download(String id) {
		
		int count = 0;
		SqlSession sqlSession = null;
		try {
			sqlSession =sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			count =  articleOperation.getArticle_index_By_download(id);
			return count;
		} finally {
			sqlSession.close();
		}
		
	}
	public int  getArticle_index_By_like(String id) {
		
		int count = 0;
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			count =  articleOperation.getArticle_index_By_like(id);
			return count;
		} finally {
			sqlSession.close();
		}
		
	}
	
	public int getArticle_index_By_time(String id) {
		int count = 0;
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			count =  articleOperation.getArticle_index_By_time(id);
			return count;
		} finally {
			sqlSession.close();
		}
	}
	
	public int getArticle_index_By_view(String id) {
		int count = 0;
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			count =  articleOperation.getArticle_index_By_view(id);
			return count;
		} finally {
			sqlSession.close();
		}
	}
	public List<ArticleBean> getArticleByLike(int index) {
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			List<ArticleBean> map = articleOperation.getArticleByLike(index);
			if (map == null) {
				log.error("by点赞获得文章出错");
				map = new ArrayList<ArticleBean>();
			}
			return map;
		} finally {
			sqlSession.close();
		}
	}
	public int getArticle_index(String id, String username) {
		int count = 0;
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			count =  articleOperation.getArticle_index(id,username);
			return count;
		} finally {
			sqlSession.close();
		}
	}
	public List<ArticleBean> getBrief_Article_List_ByLike() {
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			List<ArticleBean> map = articleOperation.getBrief_Article_List_ByLike();
			//TODO:返回出去的对象 确保不为null
			if (map == null) {
				log.error("获得简单文章信息byLike出错");
				map = new ArrayList<ArticleBean>(1);
			}
			return map;
		} finally {
			sqlSession.close();
		}
	}
	public List<ArticleBean> getBrief_Article_list_ByDownload() {
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			List<ArticleBean> map = articleOperation.getBrief_Article_list_ByDownload();
			if (map == null) {
				log.error("获得简单文章信息byDownload出错");
				map = new ArrayList<ArticleBean>(1);
			}
			return map;
		} finally {
			sqlSession.close();
		}
	}
	public List<ArticleBean> getArticle_download_list(int i) {
		 
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			List<ArticleBean> map = articleOperation.getArticle_download_list(i);
			if (map == null) {
				log.error("分页文章byDownload出错");
				map = new ArrayList<ArticleBean>(1);
			}
			return map;
		} finally {
			sqlSession.close();
		}
	}
	public List<ArticleBean> getArticleByDownload(int index) {
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			List<ArticleBean> map = articleOperation.getArticleByDownload(index);
			if (map == null) {
				log.error("获得文章信息byDownload出错");
				map = new ArrayList<ArticleBean>(1);
			}
			return map;
		} finally {
			sqlSession.close();
		}
	}
	public List<ArticleBean> getBrief_Article_By_View() {
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			List<ArticleBean> map = articleOperation.getBrief_Article_By_View();
			if (map == null) {
				log.error("获得简单文章信息byView出错");
				map = new ArrayList<ArticleBean>(1);
			}
			return map;
		} finally {
			sqlSession.close();
		}
	}
	public List<ArticleBean> getBrief_Article_By_Time() {
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			List<ArticleBean> map = articleOperation.getBrief_Article_By_Time();
			if (map == null) {
				log.error("获得简单文章信息byTime出错");
				map = new ArrayList<ArticleBean>(1);
			}
			return map;
		} finally {
			sqlSession.close();
		}
	}

	public List<ArticleBean> getArticle_view_list(int i) {
		

		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			List<ArticleBean> map = articleOperation.getArticle_view_list(i);
			if (map == null) {
				log.error("分页文章byView出错");
				map = new ArrayList<ArticleBean>(1);
			}
			return map;
		} finally {
			sqlSession.close();
		
		}
		
	}
	public List<ArticleBean> getArticle_time_list(int i) {
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			List<ArticleBean> map =  articleOperation.getArticle_time_list(i);
			if (map == null) {
				log.error("分页文章byTime出错");
				map = new ArrayList<ArticleBean>(1);
			}
			return map;
		} finally {
			sqlSession.close();
		}
	}
	public List<ArticleBean> getArticleByTime(int index) {
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			List<ArticleBean> map = articleOperation.getArticleByTime(index);
			if (map == null) {
				log.error("获得文章byTime出错");
				map = new ArrayList<ArticleBean>(1);
			}
			return map;
		} finally {
			sqlSession.close();
		}
	}
	public List<ArticleBean> getArticleByView(int index) {
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			List<ArticleBean> map = articleOperation.getArticleByView(index);
			if (map == null) {
				log.error("获得文章byView出错");
				map = new ArrayList<ArticleBean>(1);
			}
			return map;
		} finally {
			sqlSession.close();
		}
	}
	public List<ArticleBean> getAllArticle() {
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			ArticleOperation articleOperation = sqlSession.getMapper(ArticleOperation.class);
			List<ArticleBean> map = articleOperation.getAllArticle();
			if (map == null) {
				log.error("获得所有文章出错");
				map = new ArrayList<ArticleBean>(1);
			}
			return map;
		} finally {
			sqlSession.close();
		}
	}
}
