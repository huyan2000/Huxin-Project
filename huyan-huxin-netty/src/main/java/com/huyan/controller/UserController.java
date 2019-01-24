package com.huyan.controller;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huyan.pojo.Users;
import com.huyan.pojo.vo.UsersVO;
import com.huyan.service.UserService;
import com.huyan.utils.HUyanJSONResult;
import com.huyan.utils.MD5Utils;

/** 
  * @author 胡琰 
  * @version 创建时间：2019年1月20日 上午11:40:01 
  * @Description:
  */
@RestController
@RequestMapping("u")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/registOrLogin")
	public HUyanJSONResult registOrLogin(@RequestBody Users user) throws Exception {
		
		//0.判断用户名和密码不能为空
		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
			return HUyanJSONResult.errorMsg("用户或密码不能为空...");
		}
		//1.判断用户名是否存在，如果存在就登陆，不存在就注册
		boolean usernameIsExist = userService.queryUsernameIsExist(user.getUsername());
		Users userResult = null;
		if (usernameIsExist) {
			//1.1 Login
		userResult = userService.queryUserForLogin(user.getUsername(), 
								MD5Utils.getMD5Str(user.getPassword()));
		if (userResult == null) {
			return HUyanJSONResult.errorMsg("密码不正确...");
			}
		}else {
			//1.2 Regist
			user.setNickname(user.getUsername());
			user.setFaceImage("");
			user.setFaceImageBig("");
			user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
			userResult = userService.saveUser(user);
		}
		
		UsersVO userVO = new UsersVO();
		BeanUtils.copyProperties(userResult, userVO);
		
		return HUyanJSONResult.ok(userVO);
	}
}
