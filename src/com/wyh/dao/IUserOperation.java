package com.wyh.dao;

import com.wyh.bean.User;
import java.util.List;

public abstract interface IUserOperation
{
  public abstract int insert(User paramUser);

  public abstract int update(User paramUser);

  public abstract int update_head_img(String paramString1, String paramString2);

  public abstract List<User> select(String paramString1, String paramString2);

  public abstract User getUserBean(String paramString);

  public abstract List<User> getList(String paramString1, String paramString2);
}