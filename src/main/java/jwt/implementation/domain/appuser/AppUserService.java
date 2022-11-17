package jwt.implementation.domain.appuser;

import jwt.implementation.domain.role.Role;
import jwt.implementation.domain.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AppUserService implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository,
                          RoleRepository roleRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser userAlreadyExists =  appUserRepository.findByUsername(username);
        if (userAlreadyExists == null) {
            System.out.println("User not found in DataBase");
            throw new UsernameNotFoundException("User could not be found in DataBase");
        }else{
            System.out.println("User found in DataBase");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        //je recupère le user qui est lier à un ou plusieurs role
        // et je lui ajoute un role pour chacun de ceux qui existent
        userAlreadyExists.getRoleList().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        // je construit un "Objet" User appartenant à SpringSecurity
        // celui est construit à partir de notre User de AppUser
        return new User( userAlreadyExists.getUsername(), userAlreadyExists.getPassword(),authorities);
    }

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll(); // methode findAll et findBy sont reconnues et importantes pour JPA
    }

    public AppUser saveUser(AppUser appUser) throws Exception {
        AppUser userAlreadyExist=  appUserRepository.findByUsername(appUser.getUsername());

        if(userAlreadyExist == null){
            System.out.println("User saved :" + appUser.getUsername());
            appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
            return appUserRepository.save(appUser);
        } else {
            System.out.println("Sorry pal ");
            throw new Exception("User already exists");

        }
    }

    public void addRoleToAppUser(String username, String roleName) throws Exception {

        AppUser userAlreadyExists = appUserRepository.findByUsername(username);

        if(userAlreadyExists == null){
            throw new Exception("username already exists");
        } else{
            Role role = roleRepository.findByName(roleName);
            userAlreadyExists.getRoleList().add(role);
            appUserRepository.save(userAlreadyExists);
        }
    }

}
