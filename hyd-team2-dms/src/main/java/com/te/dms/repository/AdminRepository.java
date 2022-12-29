package com.te.dms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.te.dms.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

}
