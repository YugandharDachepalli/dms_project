package com.te.dms.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.te.dms.entity.DmsUser;

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
public class FetchProjectDto {

	private Integer id;

	private String projectId;

	private String contactName;

	private String projectName;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private List<FetchDocumentsDto> documentsDto = new ArrayList<FetchDocumentsDto>();

}
