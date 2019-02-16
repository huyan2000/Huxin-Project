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
	},
	/**
	 * 获取本地缓存中的联系人列表
	 */
	getContactList: function(){
		var contatckListStr = plus .storage.getItem("contactList");
		
		if (!this.isNotNull(contactListStr)) {
			return [];
			
		}
		return JSON.parse(contactListStr);
		
	},
	
	/**
	 * 与后端枚举对应
	 */
	CONNECT:1,// "第一次(或重连)初始化连接"
	CHAT:2, //"聊天消息"
	SIGNED:3, //"消息签收"
	KEEPALIVE:4, //"客户端保持心跳"
	
	/**
	 * 与后端聊天模型对象保持一致
	 */
	ChatMsg: function(senderId, receiverId,msg,msgId){
			this.senderId = senderId;
			this.receiverId = receiverId;
			this.msg = msg;
			this.msgId = msgId;
		}
	/**
	 * 构建消息DataContent模型对象
	 */
	DataContent: function(action, charMsg,extand){
			this.action = action;
			this.charMsg = charMsg;
			this.extand = extand;
		}
	}
