package com.huyan.pojo.vo;

/**
 *	@Description: 好友请求发送方的信息
 */
public class FriendRequestVO {
    private String sendUserid;
    private String sendUsername;
    private String sendFaceImage;
    private String sendNickname;
	/**
	 * @return the sendUserid
	 */
	public String getSendUserid() {
		return sendUserid;
	}
	/**
	 * @param sendUserid the sendUserid to set
	 */
	public void setSendUserid(String sendUserid) {
		this.sendUserid = sendUserid;
	}
	/**
	 * @return the sendUsername
	 */
	public String getSendUsername() {
		return sendUsername;
	}
	/**
	 * @param sendUsername the sendUsername to set
	 */
	public void setSendUsername(String sendUsername) {
		this.sendUsername = sendUsername;
	}
	/**
	 * @return the sendFaceImage
	 */
	public String getSendFaceImage() {
		return sendFaceImage;
	}
	/**
	 * @param sendFaceImage the sendFaceImage to set
	 */
	public void setSendFaceImage(String sendFaceImage) {
		this.sendFaceImage = sendFaceImage;
	}
	/**
	 * @return the sendNickname
	 */
	public String getSendNickname() {
		return sendNickname;
	}
	/**
	 * @param sendNickname the sendNickname to set
	 */
	public void setSendNickname(String sendNickname) {
		this.sendNickname = sendNickname;
	}
    
}