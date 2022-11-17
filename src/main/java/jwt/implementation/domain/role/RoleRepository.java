package jwt.implementation.domain.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByName(String name); //par défaut en public,
    // la méthode findByName comme findByUsername n'existe pas sur JPA il faut donc les inclure manuellement
}
