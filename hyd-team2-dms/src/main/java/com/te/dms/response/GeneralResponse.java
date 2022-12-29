package com.te.dms.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GeneralResponse<T> {

	private String message;

	private String token;

	private LocalDateTime timeStamp = LocalDateTime.now();

	private T data;

	public GeneralResponse(String message, T data, String token) {
		this.message = message;
		this.data = data;
		this.token = token;
	}

}
