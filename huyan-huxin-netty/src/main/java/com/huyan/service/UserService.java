package com.huyan.service;

import com.huyan.pojo.Users;

/** 
  * @author 胡琰 
  * @version 创建时间：2019年1月21日 上午10:46:56 
  * @Description:
  * queryUsernameIsExist：判断用户是否存在
  * queryUserForLogin：查询用户是否存在
  * 			
  */
public interface UserService {
	
	public boolean queryUsernameIsExist(String username);
	
	public Users queryUserForLogin(String username, String pwd);
	
	public Users saveUser(Users user);
}
