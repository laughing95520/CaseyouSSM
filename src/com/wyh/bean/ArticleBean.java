package com.wyh.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class ArticleBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5593556401234399423L;
	
	int sum_view_count;
	int sum_article_count;
	int file_count;
	int file_size;
	String file_path;
	String  article_id;
	String article_name;
	String article_writer;
	String article_text;
	int article_from_flag;
	String article_from;
	Timestamp article_date;
	int article_view_count;
	int artile_like_count;
	String up_id;
	String next_id;
	String up_name;
	String next_name;

	public ArticleBean()
	{
		
	}
	
	@Override
	public String toString() {
		return "Article [sum_view_count=" + sum_view_count + ", sum_article_count=" + sum_article_count
				+ ", file_count=" + file_count + ", file_size=" + file_size + ", file_path=" + file_path
				+ ", article_id=" + article_id + ", article_name=" + article_name + ", article_writer=" + article_writer
				+ ", article_text=" + article_text + ", article_from_flag=" + article_from_flag + ", article_from="
				+ article_from + ", article_date=" + article_date + ", article_view_count=" + article_view_count
				+ ", artile_like_count=" + artile_like_count + ", up_id=" + up_id + ", next_id=" + next_id
				+ ", up_name=" + up_name + ", next_name=" + next_name + "]";
	}
	
	public int getSum_view_count() {
		return sum_view_count;
	}

	public void setSum_view_count(int sum_view_count) {
		this.sum_view_count = sum_view_count;
	}

	public int getSum_article_count() {
		return sum_article_count;
	}

	public void setSum_article_count(int sum_article_count) {
		this.sum_article_count = sum_article_count;
	}

	public String getUp_name() {
		return up_name;
	}

	public void setUp_name(String up_name) {
		this.up_name = up_name;
	}

	public String getNext_name() {
		return next_name;
	}

	public void setNext_name(String next_name) {
		this.next_name = next_name;
	}

	public String getUp_id() {
		return up_id;
	}

	public void setUp_id(String up_id) {
		this.up_id = up_id;
	}

	public String getNext_id() {
		return next_id;
	}

	public void setNext_id(String next_id) {
		this.next_id = next_id;
	}

	public int getArtile_like_count() {
		return artile_like_count;
	}

	public void setArtile_like_count(int artile_like_count) {
		this.artile_like_count = artile_like_count;
	}

	public int getArticle_view_count() {
		return article_view_count;
	}

	public void setArticle_view_count(int article_view_count) {
		this.article_view_count = article_view_count;
	}

	public int getFile_count() {
		return file_count;
	}
	public void setFile_count(int file_count) {
		this.file_count = file_count;
	}
	public int getfile_size() {
		return file_size;
	}
	public void setfile_size(int article_file_size) {
		this.file_size = article_file_size;
	}
	
	public int getArticle_from_flag() {
		return article_from_flag;
	}
	public void setArticle_from_flag(int article_from_flag) {
		this.article_from_flag = article_from_flag;
	}
	public String getfile_path() {
		return file_path;
	}
	public void setfile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getArticle_id() {
		return article_id;
	}
	public void setArticle_id(String article_id) {
		this.article_id = article_id;
	}
	public String getArticle_name() {
		return article_name;
	}
	
	public void setArticle_name(String article_name) {
		this.article_name = article_name;
	}
	public String getArticle_writer() {
		return article_writer;
	}
	public void setArticle_writer(String article_writer) {
		this.article_writer = article_writer;
	}
	public String getArticle_text() {
		return article_text;
	}
	public void setArticle_text(String article_text) {
		this.article_text = article_text;
	}
	public String getArticle_from() {
		return article_from;
	}
	public void setArticle_from(String article_from) {
		this.article_from = article_from;
	}
	public Timestamp getArticle_date() {
		return article_date;
	}
	public void setArticle_date(Timestamp article_date) {
		this.article_date = article_date;
	}
	
}
