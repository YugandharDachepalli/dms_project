package com.te.dms;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.te.dms.entity.Admin;
import com.te.dms.entity.AppUser;
import com.te.dms.entity.Roles;
import com.te.dms.repository.AdminRepository;
import com.te.dms.repository.AppUserRepository;
import com.te.dms.repository.RolesRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SpringBootApplication
public class DocumentManagementSystemApplication {

	private final AppUserRepository appUserRepository;
	private final RolesRepository rolesRepository;
	private final AdminRepository adminRepository;
 
	public static void main(String[] args) {
		SpringApplication.run(DocumentManagementSystemApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner() {
		return args -> {
			Roles user = Roles.builder().roleName("ROLE_USER").appUsers(new ArrayList<AppUser>()).build();
			Roles admin = Roles.builder().roleName("ROLE_ADMIN").appUsers(new ArrayList<AppUser>()).build();
			Admin admin01 = Admin.builder().adminId("admin01").adminName("Admin").build();
			AppUser adminCredentials = AppUser.builder().username(admin01.getAdminId()).password("password")
					.roles(new ArrayList<Roles>()).build();
			adminCredentials.getRoles().add(admin);
			admin.getAppUsers().add(adminCredentials);
			rolesRepository.save(user);
			rolesRepository.save(admin);
	    	adminRepository.save(admin01);
			appUserRepository.save(adminCredentials);
			
		};
	}

}
