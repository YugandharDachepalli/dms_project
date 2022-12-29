package com.te.dms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.te.dms.entity.DmsUser;

public interface DmsUserRepository extends JpaRepository<DmsUser, String>{

}
