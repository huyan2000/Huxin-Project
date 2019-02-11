package com.huyan.service;

import org.apache.ibatis.lang.UsesJava7;

import com.huyan.pojo.Users;

/** 
  * @author 胡琰 
  * @version 创建时间：2019年1月21日 上午10:46:56 
  * @Description:
  * queryUsernameIsExist：判断用户是否存在
  * queryUserForLogin：查询用户是否存在
  * saveUser：用户注册
  * updateUserInfo：修改用户记录	
  * preconditionSearchFriends:搜索朋友的前置条件	
  * queryUserInfoByUsername:根据用户名查询用户对象
  */
public interface UserService {
	
	public boolean queryUsernameIsExist(String username);
	
	public Users queryUserForLogin(String username, String pwd);
	
	public Users saveUser(Users user);
	
	public Users updateUserInfo(Users user);
	
	public Integer preconditionSearchFriends(String myuserId,String friendUsername);
	
	public Users queryUserInfoByUsername(String username);
	
}
