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

	// Méthode qui se délenche après que mon application ait été initialisée
	// Permet d'effectuer un ensemble de tâches précises. Ici, créer des roles, créer des utilisateurs, attribuer les rôles aux utilisateurs
	@Bean
	CommandLineRunner run(AppUserService appUserService, RoleService roleService) {
		return args -> {

			// Création des roles
			roleService.saveRole(new Role(null, "ROLE_USER"));
			roleService.saveRole(new Role(null, "ROLE_MANAGER"));
			roleService.saveRole(new Role(null, "ROLE_ADMIN"));

			// Création des utilisateurs
			appUserService.saveUser(new AppUser(null, "James", "james-bond", "007", new ArrayList<>()));
			appUserService.saveUser(new AppUser(null, "Calamity", "calamity-jane", "008", new ArrayList<>()));
			appUserService.saveUser(new AppUser(null, "MarvinWizard", "marvin-wizard", "009", new ArrayList<>()));

			// Attribution des rôles pour les utilisateurs
			appUserService.addRoleToAppUser("james-bond", "ROLE_USER");
			appUserService.addRoleToAppUser("james-bond", "ROLE_MANAGER");
			appUserService.addRoleToAppUser("james-bond", "ROLE_ADMIN");

			appUserService.addRoleToAppUser("calamity-jane", "ROLE_USER");

			appUserService.addRoleToAppUser("marvin-wizard", "ROLE_USER");
			appUserService.addRoleToAppUser("marvin-wizard", "ROLE_MANAGER");
		};
	}


	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
