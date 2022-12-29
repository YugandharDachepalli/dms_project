package com.te.dms.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
@Entity
public class DmsUser {

	private String firstName;

	private String lastName;

	@Id
	private String userId;

	private String emailId;

	private Long phoneNo;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<Projects> projects;

}
