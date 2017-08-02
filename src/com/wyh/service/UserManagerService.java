package com.wyh.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyh.bean.User;
import com.wyh.dao.IUserOperation;


@Service
public class UserManagerService
{
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
  public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

public List<User> getList(String username, String password)
  {
    SqlSession sqlSession = null;
    try {
    	sqlSession = sqlSessionFactory.openSession();
    	IUserOperation userOperation = (IUserOperation)sqlSession.getMapper(IUserOperation.class);
    	List<User> list = userOperation.getList(username, password);
    	if (list == null) {
			list = new ArrayList<User>(1);
		}
    	return list;
	} finally {
		sqlSession.close();
	}
  }

  public List<User> select(String username, String email) {
    SqlSession sqlSession = null;
    try {
    	sqlSession = sqlSessionFactory.openSession();
    	IUserOperation userOperation = (IUserOperation)sqlSession.getMapper(IUserOperation.class);
    	List<User> list = userOperation.select(username, email);
    	if (list == null) {
			list = new ArrayList<User>(1);
		}
    	return list;
	} finally {
		sqlSession.close();
	}
  }

  public User getUserBean(String username) {
    SqlSession sqlSession = null;
    try {
    	sqlSession = sqlSessionFactory.openSession();
    	IUserOperation userOperation = (IUserOperation)sqlSession.getMapper(IUserOperation.class);
    	User user = userOperation.getUserBean(username);
    	if (user == null) {
			user = new User();
		}
    	return user;
	} finally {
		sqlSession.close();
	}
  }

  public int inseret(User userBean) {
    SqlSession sqlSession = null;
    int count = 0;
    try {
    	sqlSession = sqlSessionFactory.openSession();
    	IUserOperation userOperation = (IUserOperation)sqlSession.getMapper(IUserOperation.class);
    	count = userOperation.insert(userBean);
    	return count;
	} finally {
		sqlSession.close();
	}
  }
  

  public boolean saveUser(User userBean)
  {
    SqlSession sqlSession = null;
    int count = 0;
    try {
    	sqlSession = sqlSessionFactory.openSession();
    	IUserOperation userOperation = (IUserOperation)sqlSession.getMapper(IUserOperation.class);
    	count = userOperation.update(userBean);
    	return count > 0;
	} finally {
		sqlSession.close();
	}
  }

  public boolean uploadPhoto(String headimg, String username)
  {
    SqlSession sqlSession = null;
    int count = 0;
    try {
    	sqlSession = sqlSessionFactory.openSession();
    	IUserOperation userOperation = (IUserOperation)sqlSession.getMapper(IUserOperation.class);
    	count = userOperation.update_head_img(headimg, username);
    	return count > 0;
	} finally {
		sqlSession.close();
	}
  }

}