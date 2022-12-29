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
public class Documents {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Projects projectId;
	
	@ManyToOne
	private DocumentType documentType;
	
	@OneToMany(mappedBy = "documents",cascade = CascadeType.ALL)
	private List<DocumentData> documentDataId= new ArrayList<DocumentData>();;
	
	@OneToMany(mappedBy = "documents",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<DocumentFiles> documentFiles= new ArrayList<DocumentFiles>();
	
	private LocalDateTime deletedAt;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	
	
	
}
