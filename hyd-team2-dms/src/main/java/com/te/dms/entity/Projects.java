package com.te.dms.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Projects {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String projectId;

	@ManyToOne(cascade = CascadeType.ALL)
	private DmsUser user;

	private String contactName;

	private String projectName;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
	
	private LocalDateTime deletedAt;

	@OneToMany(mappedBy = "projectId", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<Documents> documents = new ArrayList<Documents>();

}
