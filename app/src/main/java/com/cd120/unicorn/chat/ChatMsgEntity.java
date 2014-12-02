package com.cd120.unicorn.chat;

/**
 * 一个聊天消息的JavaBean
 * 
 * @author way
 * 
 */
public class ChatMsgEntity {
	private String date;// 消息日期
	private int img;
	private boolean isComMeg = true;// 是否为收到的消息
	private String message;// 消息内容
	private String name;// 消息来自

	public ChatMsgEntity() {

	}

	public ChatMsgEntity(String name, String date, String text, int img,
			boolean isComMsg) {
		super();
		this.name = name;
		this.date = date;
		this.message = text;
		this.img = img;
		this.isComMeg = isComMsg;
	}

	public String getDate() {
		return date;
	}

	public int getImg() {
		return img;
	}

	public String getMessage() {
		return message;
	}

	public boolean getMsgType() {
		return isComMeg;
	}

	public String getName() {
		return name;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setImg(int img) {
		this.img = img;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setMsgType(boolean isComMsg) {
		isComMeg = isComMsg;
	}

	public void setName(String name) {
		this.name = name;
	}
}
