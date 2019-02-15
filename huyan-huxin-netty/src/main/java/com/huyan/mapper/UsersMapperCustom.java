package com.huyan.mapper;

import java.util.List;

import com.huyan.pojo.Users;
import com.huyan.pojo.vo.FriendRequestVO;
import com.huyan.pojo.vo.MyFriendsVO;
import com.huyan.utils.MyMapper;

public interface UsersMapperCustom extends MyMapper<Users> {
	
	public List<FriendRequestVO> queryFriendRequestList(String acceptUserId);
	
	public List<MyFriendsVO> queryMyFriends(String userId);
}