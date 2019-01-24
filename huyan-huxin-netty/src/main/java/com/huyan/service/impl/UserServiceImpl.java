package com.huyan.service.impl;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.zxing.Result;
import com.huyan.mapper.UsersMapper;
import com.huyan.pojo.Users;
import com.huyan.service.UserService;

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
	private Sid sid;
	
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

	@Override
	public Users saveUser(Users user) {
		
		String UserId = sid.nextShort();
		//生成二維碼
		user.setQrcode("");
		
		user.setId(UserId);
		
		userMapper.insert(user);
		
		return user;
	}

}
