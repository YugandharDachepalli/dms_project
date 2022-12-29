package com.te.dms.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FetchUserDto {
	
	private String firstName;

	private String lastName;

	private String userId;

	private String emailId;
	
	
	private List<FetchProjectDto > projectDto = new ArrayList<FetchProjectDto>();
	

}
