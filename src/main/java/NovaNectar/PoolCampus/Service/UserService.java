package NovaNectar.PoolCampus.Service;
import NovaNectar.PoolCampus.Model.User;

import java.util.Optional;

public interface UserService {

    void registerUser(User user);
    boolean loginUser(User user);
    User getUserById(Long id);
    User updateUser(Long id, String username, String email);
    Optional<User> findUser(String username, String password, String email);
}
