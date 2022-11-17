package jwt.implementation;

import jwt.implementation.domain.appuser.AppUser;
import jwt.implementation.domain.appuser.AppUserService;
import jwt.implementation.domain.role.Role;
import jwt.implementation.domain.role.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class ImplementationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImplementationApplication.class, args);
	}

	@Bean
	CommandLineRunner run(AppUserService appUserService, RoleService roleService) {
		return args -> {

			roleService.saveRole(new Role(null, "ROLE_USER"));
			roleService.saveRole(new Role(null, "ROLE_MANAGER"));
			roleService.saveRole(new Role(null, "ROLE_ADMIN"));

			appUserService.saveUser(new AppUser(null,"Carl Johnson","CJ","1532", new ArrayList<>()));
			appUserService.saveUser(new AppUser(null,"Sean Johnson","Sweet","4265", new ArrayList<>()));
			appUserService.saveUser(new AppUser(null,"Melvin Harris","BigSmoke","4865", new ArrayList<>()));

			appUserService.addRoleToAppUser("CJ","ROLE_USER");
			appUserService.addRoleToAppUser("CJ","ROLE_MANAGER");
			appUserService.addRoleToAppUser("CJ","ROLE_ADMIN");

			appUserService.addRoleToAppUser("Sweet","ROLE_USER");
			appUserService.addRoleToAppUser("Sweet","ROLE_MANAGER");

			appUserService.addRoleToAppUser("BigSmoke","ROLE_USER");

        };
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
