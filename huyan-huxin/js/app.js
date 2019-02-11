window.app = {
	/*
	 *后端发布的Url地址
	 * 
	 * */
	serverUrl: 'http://192.168.1.4:8080',
	
	/**
	 * 图片服务器的URL地址
	 * 
	 */
	imgServerUrl: 'http://192.168.1.70:88/huyan/',
	
	/**
	 * 判断字符串是否为空
	 * */
	isNotNull: function(str){
		if (str != null && str != "" && str != undefined) {
			return true;
		}
		return false;
	},
	
	/**
	 * 封装消息提示框，默认mui的不支持居中和自定义icon，所以使用h5+
	 * @param {Object} msg
	 * @param {Object} type
	 */
	showToast: function(msg,type){
		plus.nativeUI.toast(msg, 
			{icon: "image/" + type + ".png", verticalAlign: "center"})
		},
		
		/**
		 * 保存用户的全局对象
		 * @param {Object} user 
		 * */
		setUserGlobalnfo: function(user){
			var userInfoStr = JSON.stringify(user);
			plus.storage.setItem("userInfo", userInfoStr);
		},
		/**
		 * 获取用户的全局对象
		 */
		getUserGlobalnfo: function(user){
			var userInfoStr = plus.storage.setItem("userInfo");
			return JSON.parse(userInfoStr);
		},
		
	/**
	 * 登出后，移除用户全局对象
	 */
	userLogout: function(){
		plus.storage.removeItem("userInfo");
	}
	
	}
