window.app = {
	/*
	 *后端发布的Url地址
	 * 
	 * */
	serverUrl: 'http://192.168.1.10:8080',
	
	/**
	 * 判断字符串是否为空
	 * */
	
	isNotNull: function(str){
		if (str != null && str != "" && str != undefined) {
			return true;
		}
		return false;
	},
	
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
			return userInfoStr;
		},
	}
