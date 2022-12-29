package com.te.dms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.te.dms.entity.DocumentFiles;

public interface DocumentFilesRepository extends JpaRepository<DocumentFiles, Integer> {

	Optional<DocumentFiles> findByFileName(String fileName);

	@Query(value = "select * from document_files where documents_id=?1 and deleted_at is null", nativeQuery = true)
	Optional<List<DocumentFiles>> findAllByDocuments(Integer id);

	@Query(value = "select * from document_files where file_name =?1 and deleted_at is null", nativeQuery = true)
	Optional<DocumentFiles> findByFilename(String fileName);

	

}
