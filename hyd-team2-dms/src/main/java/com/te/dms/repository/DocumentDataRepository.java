package com.te.dms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.te.dms.entity.DocumentData;

public interface DocumentDataRepository extends JpaRepository<DocumentData, Integer> {

	Optional<DocumentData> findByKey1(String key);

}
