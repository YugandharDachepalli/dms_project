package com.te.dms.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.te.dms.entity.DocumentType;
import com.te.dms.entity.Projects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
public class FetchDocumentsDto {

    private Integer id;
		
	private List<FetchDocumentFilesDto> documentFilesDto= new ArrayList<FetchDocumentFilesDto>();
	
	private LocalDateTime deletedAt;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
}
