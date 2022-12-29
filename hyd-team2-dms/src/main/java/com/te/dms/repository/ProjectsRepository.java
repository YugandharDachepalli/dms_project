package com.te.dms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.te.dms.entity.Projects;

@Repository
public interface ProjectsRepository extends JpaRepository<Projects, Integer>{

	Optional<Projects> findByProjectId(String projectId);

	Optional<Projects> findByProjectName(String projectName);

	@Query(value = "select * from projects where project_name=?1 and user_user_id=?2 and deleted_at is null",nativeQuery = true)
	Optional<Projects> findByProjectandUser(String projectName, String userId);

}
