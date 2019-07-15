package com.example.learn.metaData;

public class ResponseBody<T> {

	private T entity;
	private MsgLst msgLst;

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public MsgLst getMsgLst() {
		return msgLst;
	}

	public void setMsgLst(MsgLst msgLst) {
		this.msgLst = msgLst;
	}

}
