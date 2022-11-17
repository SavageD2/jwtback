package jwt.implementation.domain.appuser;

import jwt.implementation.domain.role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data //génére les getters et setters
@NoArgsConstructor // génére un constructeur sans arguments
@AllArgsConstructor//génére un constructeur avec des arguments
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roleList = new ArrayList<>();

}
