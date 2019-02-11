package com.huyan.controller;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.huyan.enums.SearchFriendsStatusEnum;
import com.huyan.pojo.Users;
import com.huyan.pojo.bo.UsersBO;
import com.huyan.pojo.vo.UsersVO;
import com.huyan.service.UserService;
import com.huyan.utils.FastDFSClient;
import com.huyan.utils.FileUtils;
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

	@Autowired
	private FastDFSClient fastDFSClient;

	@PostMapping("/registOrLogin")
	public HUyanJSONResult registOrLogin(@RequestBody Users user) throws Exception {

		// 0.判断用户名和密码不能为空
		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
			return HUyanJSONResult.errorMsg("用户或密码不能为空...");
		}
		// 1.判断用户名是否存在，如果存在就登陆，不存在就注册
		boolean usernameIsExist = userService.queryUsernameIsExist(user.getUsername());
		Users userResult = null;
		if (usernameIsExist) {
			// 1.1 Login
			userResult = userService.queryUserForLogin(user.getUsername(), MD5Utils.getMD5Str(user.getPassword()));
			if (userResult == null) {
				return HUyanJSONResult.errorMsg("密码不正确...");
			}
		} else {
			// 1.2 Regist
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

	@PostMapping("/uploadFaceBase64")
	public HUyanJSONResult uploadFaceBase64(@RequestBody UsersBO userBo) throws Exception {

		// 获取前端传过来的base64字符串，然后转换为文件对象再上传
		String base64Data = userBo.getFaceData();
		String userFacePath = "E:\\" + userBo.getUserid() + "userface64.png";
		FileUtils.base64ToFile(userFacePath, base64Data);

		// 上传文件到fastdfs
		MultipartFile faceFile = FileUtils.fileToMultipart(userFacePath);
		String url = fastDFSClient.uploadBase64(faceFile);
		System.out.println(url);

		// 获取缩略图的url
		String thump = "_80x80.";
		String arr[] = url.split("\\.");
		String thumpImgUrl = arr[0] + thump + arr[1];

		// 更新用户头像
		Users user = new Users();
		user.setId(userBo.getUserid());
		user.setFaceImage(thumpImgUrl);
		user.setFaceImageBig(url);

		userService.updateUserInfo(user);

		return HUyanJSONResult.ok(user);
	}

	@PostMapping("/setNickname")
	public HUyanJSONResult setNickname(@RequestBody UsersBO userBo) throws Exception {

		Users user = new Users();
		user.setId(userBo.getUserid());
		user.setNickname(userBo.getNickname());

		Users result = userService.updateUserInfo(user);

		return HUyanJSONResult.ok(result);
	}

	@PostMapping("/search")
	public HUyanJSONResult searchUser(String myuserId,String friendUsername) throws Exception {
		//0.判断myuserId friendUsername不能为空
		if (StringUtils.isBlank(myuserId) || StringUtils.isBlank(friendUsername)) {
			return HUyanJSONResult.errorMsg("");
		}
		
		// 前置条件 - 1. 搜索的用户如果不存在，返回[无此用户]
		// 前置条件 - 2. 搜索账号是你自己，返回[不能添加自己]
		// 前置条件 - 3. 搜索的朋友已经是你的好友，返回[该用户已经是你的好友]
		Integer status = userService.preconditionSearchFriends(myuserId, friendUsername);
		if (status == SearchFriendsStatusEnum.SUCCESS.status) {
			Users user = userService.queryUserInfoByUsername(friendUsername);
			UsersVO userVO = new UsersVO();
			BeanUtils.copyProperties(user, userVO);
				return HUyanJSONResult.ok(userVO);
			} else {
				String errorMsg = com.huyan.enums.SearchFriendsStatusEnum.getMsgByKey(status);
				return HUyanJSONResult.errorMsg(errorMsg);
			}
	}
}
