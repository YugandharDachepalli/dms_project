package com.te.dms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserRegisterDto {

	private String firstName;

	private String lastName;

	private String userId;

	private String emailId;

	private Long phoneNo;

	private String password;

}
