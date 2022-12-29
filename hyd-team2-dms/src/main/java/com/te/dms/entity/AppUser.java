package com.te.dms.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

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
public class AppUser {

	@Id
	private String username;

	private String password;

	@ManyToMany(mappedBy = "appUsers",cascade = CascadeType.ALL)
	private List<Roles> roles = new ArrayList<Roles>();

}
