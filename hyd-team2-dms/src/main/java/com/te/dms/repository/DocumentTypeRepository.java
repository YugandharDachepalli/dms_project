package com.te.dms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.te.dms.entity.DocumentType;

public interface DocumentTypeRepository extends JpaRepository<DocumentType, Integer> {

	Optional<DocumentType> findByName(String name);

}
