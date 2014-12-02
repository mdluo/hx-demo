package com.cd120.unicorn.chat;

import java.io.Serializable;

public class ChatEntity implements Serializable {
	private static final long serialVersionUID = -7060210544600464481L;
	private String chatTime;
	private String content;
	private boolean isComeMsg;
	private int userImage;

	public String getChatTime() {
		return chatTime;
	}

	public String getContent() {
		return content;
	}

	public int getUserImage() {
		return userImage;
	}

	public boolean isComeMsg() {
		return isComeMsg;
	}

	public void setChatTime(String chatTime) {
		this.chatTime = chatTime;
	}

	public void setComeMsg(boolean isComeMsg) {
		this.isComeMsg = isComeMsg;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setUserImage(int userImage) {
		this.userImage = userImage;
	}

}
