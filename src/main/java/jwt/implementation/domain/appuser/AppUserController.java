package jwt.implementation.domain.appuser;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AppUserController {

    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppUser>> getAllUsers() {
        List<AppUser> appUserList = appUserService.getAllUsers();
        return  new ResponseEntity<>(appUserList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<AppUser> createAppUser(@RequestBody AppUser appUser) throws Exception {
        AppUser appUserCreated = appUserService.saveUser(appUser);
        return  new ResponseEntity<>(appUserCreated, HttpStatus.CREATED);
    }

    @PostMapping("/add-role-to-user")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) throws Exception {
        appUserService.addRoleToAppUser(form.getUsername(), form.getRoleName());
        return  new ResponseEntity<>(HttpStatus.OK);
    }

}

@Data
class RoleToUserForm {

    private String username;
    private String roleName;

}