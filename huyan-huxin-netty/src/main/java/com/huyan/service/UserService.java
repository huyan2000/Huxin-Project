package com.huyan.service;

import java.util.List;

import com.huyan.netty.ChatMsg;
import com.huyan.pojo.Users;
import com.huyan.pojo.vo.FriendRequestVO;
import com.huyan.pojo.vo.MyFriendsVO;

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

	/**
	 * @param 添加好友请求记录
	 */
	public void sendFriendsRequstRequest(String myuserId, String friendUsername);
	
	/**
	 * 	@Description:查询好友请求
	 * */
	public List<FriendRequestVO> queryFriendRequestList(String acceptUserId);
	
	/**
	 * 	@Description:删除好友请求记录
	 */
	public void deleteFriendRequest(String sendUserId, String acceptUserId);
	
	/**
	 * 
	 *	@Description:通过好友请求 
	 *	1.保存好友
	 *	2.逆向保存好友
	 *	3.删除好友请求记录
	 */
	public void passFriendRequest(String sendUserId, String acceptUserId);
	
	/**
	 * @Description: 查询好友列表
	 */
	public List<MyFriendsVO> queryMyFriends(String userId);
	
	/**
	 * 	@Description:保存聊天消息到数据库
	 */
	public String saveMsg(ChatMsg chatMsg);
	
	/**
	 *  @Description:批量签收消息
	 */
	public void updataMsgSigned(List<String> msgIdList);
	
	/**
	 * @Description: 获取未签收消息列表
	 */
	public List<com.huyan.pojo.ChatMsg> getUnReadMsgList(String acceptUserId);
	
	
}
