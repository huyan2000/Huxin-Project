package com.huyan.controller;

import java.lang.reflect.Proxy;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.huyan.enums.OperatorFriendRequestTypeEnum;
import com.huyan.enums.SearchFriendsStatusEnum;
import com.huyan.pojo.Users;
import com.huyan.pojo.bo.UsersBO;
import com.huyan.pojo.vo.MyFriendsVO;
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

	/**
	 * @Description: 搜索好友接口，根据账号做匹配查询而不是模糊查询
	 */
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
	/**
	 * @Description: 发送加好友的请求
	 */
	@PostMapping("/addFriendRequest")
	public HUyanJSONResult addFriendRequest(String myuserId,String friendUsername) throws Exception {
		//0.判断myuserId friendUsername不能为空
		if (StringUtils.isBlank(myuserId) || StringUtils.isBlank(friendUsername)) {
			return HUyanJSONResult.errorMsg("");
		}
		
		// 前置条件 - 1. 搜索的用户如果不存在，返回[无此用户]
		// 前置条件 - 2. 搜索账号是你自己，返回[不能添加自己]
		// 前置条件 - 3. 搜索的朋友已经是你的好友，返回[该用户已经是你的好友]
		Integer status = userService.preconditionSearchFriends(myuserId, friendUsername);
		if (status == SearchFriendsStatusEnum.SUCCESS.status) {
			userService.sendFriendsRequstRequest(myuserId, friendUsername);

			} else {
				String errorMsg = com.huyan.enums.SearchFriendsStatusEnum.getMsgByKey(status);
				return HUyanJSONResult.errorMsg(errorMsg);
			}
		return HUyanJSONResult.ok();
	}
	@PostMapping("/queryFriendRequests")
	public HUyanJSONResult addFriendRequest(String userId) throws Exception {
		
				// 0.判断 myUserId friendUsername 不能为空
				if (StringUtils.isBlank(userId)) {
					return HUyanJSONResult.errorMsg("");
				}
				
				//1.查询用户接收到的朋友申请
				return HUyanJSONResult.ok(userService.queryFriendRequestList(userId));
		
	}
	/**
	 *	@Description: 接受方通过或者忽略好友请求 
	 */
	@PostMapping("/operFriendRequest")
	public HUyanJSONResult operFriendRequest(String acceptUserId,String sendUserId,Integer operType) throws Exception {
		
				// 0.判断 acceptUserId sendUserId 不能为空
				if (StringUtils.isBlank(acceptUserId) || StringUtils.isBlank(acceptUserId) || operType == null) {
					return HUyanJSONResult.errorMsg("");
				}
				//1.如果operType 没有对应的枚举值，则直接抛出空错误信息
				if (StringUtils.isBlank(OperatorFriendRequestTypeEnum.getMsgByType(operType))) {
					return HUyanJSONResult.errorMsg("");
				}
				if (operType == OperatorFriendRequestTypeEnum.IGNORE.type) {
					//判断如果忽略好友请求，则直接删除好友请求的数据库记录
					userService.deleteFriendRequest(sendUserId, acceptUserId);
					}else if (operType == OperatorFriendRequestTypeEnum.PASS.type) {
						//3.判断如果是好友请求，则互相增加好友记录到数据库对应的表
						//然后删除好友请求的数据库表记录
						userService.passFriendRequest(sendUserId, acceptUserId);
						}
						//4.数据库查询好友列表
						List<MyFriendsVO> myfriends = userService.queryMyFriends(acceptUserId);
				//1.查询用户接收到的朋友申请
				return HUyanJSONResult.ok(myfriends);
		
	}
	/**
	 *	@Description: 查询我的好友列表
	 */
	@PostMapping("/myFriends")
	public HUyanJSONResult myFriends(String userId) {
		//0.userId判断不能为空
		if (StringUtils.isBlank(userId)) {
			return HUyanJSONResult.errorMsg("");
		}
		//1.数据库查询好友列表
		List<MyFriendsVO> myfriends = userService.queryMyFriends(userId);
		return HUyanJSONResult.ok(myfriends);
	}
}
