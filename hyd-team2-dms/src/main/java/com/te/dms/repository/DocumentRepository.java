package com.te.dms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.te.dms.entity.Documents;

public interface DocumentRepository extends JpaRepository<Documents, Integer> {

	@Query(value = "select * from documents where id=?1 and project_id_id=?2 and deleted_at is null", nativeQuery = true)
	Optional<Documents> findByDocumentAndProject(Integer documentId, Integer id);

}
