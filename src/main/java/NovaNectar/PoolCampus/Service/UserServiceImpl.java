package NovaNectar.PoolCampus.Service;

import NovaNectar.PoolCampus.Exception.UserNotFoundException;
import NovaNectar.PoolCampus.Model.User;
import NovaNectar.PoolCampus.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerUser(User user) {
        // Directly save the user into the database
        userRepository.save(user);
    }

    @Override
    public boolean loginUser(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());

        //Case 1: Matching User
        if (existingUser == null) {
            throw new UserNotFoundException("Username not found");
        }
        // Case 2: Comparing passwords
        if (!user.getPassword().equals(existingUser.getPassword())) {
            throw new UserNotFoundException("Invalid password");
        }
        return true;
    }

    //Get user by id
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    //Find ID by username
    public Optional<User> findUser(String username, String password, String email) {
        return userRepository.findByUsernameAndPasswordAndEmail(username, password, email);
    }

    //Update
    public User updateUser(Long id, String username, String email) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(username);
            user.setEmail(email);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with ID: " + id);
        }
    }
}