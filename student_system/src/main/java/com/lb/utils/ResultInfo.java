package com.lb.utils;

public class ResultInfo {
	private String status;
	private String message;
	private Object object;

	public ResultInfo(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public ResultInfo(String status, String message, Object object) {
		super();
		this.status = status;
		this.message = message;
		this.object = object;
	}

	@Override
	public String toString() {
		return "Result [status=" + status + ", message=" + message + ", object=" + object + "]";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
}
