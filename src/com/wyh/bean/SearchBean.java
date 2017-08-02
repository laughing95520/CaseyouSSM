package com.wyh.bean;

import java.io.Serializable;

public class SearchBean implements Serializable{

	
	private static final long serialVersionUID = -383937440032042631L;

	ArticleBean articleBean;
	float similarityRatio;
	
	@Override
	public String toString() {
		return "SearchBean [articleBean=" + articleBean + ", similarityRatio=" + similarityRatio + "]";
	}
	public float getSimilarityRatio() {
		return similarityRatio;
	}
	public void setSimilarityRatio(float similarityRatio) {
		this.similarityRatio = similarityRatio;
	}
	public  SearchBean() {
		
	}
	public ArticleBean getArticleBean() {
		return articleBean;
	}
	public void setArticleBean(ArticleBean articleBean) {
		this.articleBean = articleBean;
	}
	
	
}
