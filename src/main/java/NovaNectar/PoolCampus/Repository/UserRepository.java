package NovaNectar.PoolCampus.Repository;

import NovaNectar.PoolCampus.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findByUsernameAndPasswordAndEmail(String username, String password, String email);
}