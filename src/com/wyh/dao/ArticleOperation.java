package com.wyh.dao;

import java.util.List;


import com.wyh.bean.ArticleBean;

public   interface ArticleOperation {
	//����������Ϣ
	public   int insert(ArticleBean articleBean);
	
	//��������ڸ����ߵĵڼ���
	public   int getArticle_index(String id, String username);
	
	//���������ڸ����ߵĵڼ��У��������һƪ���µ���Ϣ
	public   List<ArticleBean> getArticle(String username,int i);
	
	//�������¸�����Ϣ
	public   int insert_file(ArticleBean articleBean);
	
	//������¸���·��
	public   String getfile_path(String id);
	
	//���¸������ش���
	public   int update_file_count(String id);
	
	//�����������
	public   int update_view_count(String id,int value);

	//�����������
	//public   ArticleBean update_view_count(String id,int value);
	
	//ȷ������������޸�����
	public   int sure_view(String id);
	
	//��������½�������
	public   int insert_view_count(String id, int value);
	
	//���µ��ޱ�
	public abstract int insert_like_count(String id,String username);
	
	//���޺��õ�����
	public abstract int artile_like_count(String id);
	
	
	//��ø����������ǰ10�����¼���Ϣ
	public abstract List<ArticleBean> getArticleList(String username);
	
	//�û������������������
	public abstract ArticleBean getSumCount(String uString);
	
	//��ҳ������Ϣ�б� ��ʱ������
	public abstract List<ArticleBean> getPageList(String username, int i);
	
	//��������ҳ
	public abstract List<ArticleBean> getArticle_like_list(int page);
	
	//��������ҳ
	public abstract List<ArticleBean> getArticle_download_list(int i);
	
	//�������ҳ
	public abstract List<ArticleBean> getArticle_view_list(int i);
	
	//ʱ���ҳ
	public abstract List<ArticleBean> getArticle_time_list(int i);
	
	//������ݿ���������
	public abstract int Article_Num();
	
	//�����������������λ�ã��ڼ���
	public abstract int getArticle_index_By_download(String id);
	
	//ʱ������index���������Ϣ
	public abstract List<ArticleBean> getArticleByTime(int index);
	
	//����ʱ������������λ�ã��ڼ���
	public abstract int getArticle_index_By_time(String id);
	
	//����������������λ��
	public abstract int getArticle_index_By_view(String id);
	
	//����������������
	public abstract List<ArticleBean> getArticleByView(int index);
	
	//����������...�ڼ��л����һƪ��һƪ������Ϣ
	public abstract List<ArticleBean> getArticleByDownload(int index);
	
	//���ݵ����������������λ�ã��ڼ���
	public abstract int getArticle_index_By_like(String id);
	
	//���ݵ�����...�ڼ��л����һƪ��һƪ������Ϣ
	public abstract List<ArticleBean> getArticleByLike(int index);

	//��õ�����ǰ10��������Ϣlist
	public abstract List<ArticleBean> getBrief_Article_List_ByLike();

	//���������ǰ10��������Ϣlist
	public abstract List<ArticleBean> getBrief_Article_list_ByDownload();

	//�����ǰ����������Ϣ
	public abstract List<ArticleBean> getBrief_Article_By_View();

	//���������ƪ������Ϣ
	public abstract List<ArticleBean> getBrief_Article_By_Time();

	//�����������
	public abstract List<ArticleBean> getAllArticle();

	//��õ�ƪ����
	public abstract ArticleBean getArticleById(String id);

	



	
	
	

	

	
	
	
	
	
	

}
