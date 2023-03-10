package com.te.dms.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.te.dms.entity.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {

}
