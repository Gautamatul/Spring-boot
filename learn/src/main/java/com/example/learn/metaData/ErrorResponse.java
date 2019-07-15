package com.example.learn.metaData;

import java.util.List;

public class ErrorResponse {
	private String type;
	private List<String> error;

	public ErrorResponse(String type, List<String> error) {
		super();
		this.type = type;
		this.error = error;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getError() {
		return error;
	}

	public void setError(List<String> error) {
		this.error = error;
	}

}
