package com.huyan.service.impl;

import java.io.IOException;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.Result;
import com.huyan.enums.SearchFriendsStatusEnum;
import com.huyan.mapper.MyFriendsMapper;
import com.huyan.mapper.UsersMapper;
import com.huyan.pojo.MyFriends;
import com.huyan.pojo.Users;
import com.huyan.service.UserService;
import com.huyan.utils.FastDFSClient;
import com.huyan.utils.FileUtils;
import com.huyan.utils.QRCodeUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/** 
  * @author 胡琰 
  * @version 创建时间：2019年1月21日 上午10:48:00 
  * @Description:
  */
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UsersMapper userMapper;
	
	@Autowired
	private MyFriendsMapper myFriendsMapper;
	
	@Autowired
	private Sid sid;
	
	@Autowired
	private QRCodeUtils qrCodeUtils;
	
	@Autowired
	private FastDFSClient fastDFSClient;
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public boolean queryUsernameIsExist(String username) {
		
		Users user = new Users();
		user.setUsername(username);
		
		Users result = userMapper.selectOne(user);
		return result != null ? true : false;
	}

	/* (non-Javadoc)
	 * @see com.huyan.service.UserService#queryUserForLogin(java.lang.String, java.lang.String)
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Users queryUserForLogin(String username, String pwd) {
		
		Example userExample = new Example(Users.class);
		Criteria criteria = userExample.createCriteria();
		
		criteria.andEqualTo("username", username);
		criteria.andEqualTo("password", pwd);
		
		Users result = userMapper.selectOneByExample(userExample);
		
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public Users saveUser(Users user) {
		
		String UserId = sid.nextShort();
		//生成二維碼
		String qrCodePath = "C://user" + UserId + "qrcode.png";
		//huxin_qrcode:[username]
		qrCodeUtils.createQRCode(qrCodePath, "huxin_qrcode:[username]" + user.getUsername());
		MultipartFile qrcodeFile = FileUtils.fileToMultipart(qrCodePath);
		String qrCodeUrl = "";
		try {
			qrCodeUrl = fastDFSClient.uploadQRCode(qrcodeFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		user.setQrcode(qrCodeUrl);
		
		user.setId(UserId);
		
		userMapper.insert(user);
		
		return user;
	}

	/* (non-Javadoc)
	 * @see com.huyan.service.UserService#updateUserInfo(com.huyan.pojo.Users)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public Users updateUserInfo(Users user) {
		userMapper.updateByPrimaryKeySelective(user);
		return queryUserById(user.getId());
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	private Users queryUserById(String useId) {
		return userMapper.selectByPrimaryKey(useId);
	}

	/* (non-Javadoc)
	 * @see com.huyan.service.UserService#preconditionSearchFriends(java.lang.String, java.lang.String)
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Integer preconditionSearchFriends(String myuserId, String friendUsername) {
		
		Users user = queryUserInfoUsername(friendUsername);
		
		//1.搜索的用户如果不存在，返回[无此用户]
		if (user == null) {
			return SearchFriendsStatusEnum.USER_NOT_EXIST.status;
		}
		//2.搜索账号是你自己，返回[不能添加自己]
		if (user.getId().equals(myuserId)) {
			return SearchFriendsStatusEnum.NOT_YOURSELF.status;
		}
		//3.搜索的朋友是你的好友，返回[该用户已经是你的好友]
		Example mfe = new Example(MyFriends.class);
		Criteria mfc = mfe.createCriteria();
		mfc.andEqualTo("myUserId",myuserId);
		mfc.andEqualTo("myFriendUserId",user.getId());
		MyFriends myFriendsRel = myFriendsMapper.selectOneByExample(mfe);
		if (myFriendsRel != null) {
			return SearchFriendsStatusEnum.ALREADY_FRIENDS.status;
		}
		
		return SearchFriendsStatusEnum.SUCCESS.status;
	}
	public Users queryUserInfoUsername(String username) {
		Example ue = new Example(Users.class);
		Criteria uc = ue.createCriteria();
		uc.andEqualTo("username",username);
		return userMapper.selectOneByExample(ue);
	}

	/* (non-Javadoc)
	 * @see com.huyan.service.UserService#queryUserInfoByUsername(java.lang.String)
	 */
	@Override
	public Users queryUserInfoByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}
}
