package com.example.polls.common;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "header", "data" })
public class CommonResponse<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("header")
	CommonResponseHeader commonResponseHeader;

	@JsonProperty("data")
	T data;

	public CommonResponse(CommonResponseHeader commonResponseHeader,T data) {
		super();
		this.commonResponseHeader=commonResponseHeader;
		this.data=data;
	}

	public CommonResponseHeader getCommonResponseHeader() {
		return commonResponseHeader;
	}

	public void setCommonResponseHeader(CommonResponseHeader commonResponseHeader) {
		this.commonResponseHeader = commonResponseHeader;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	

}

