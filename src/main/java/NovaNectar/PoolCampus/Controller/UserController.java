package NovaNectar.PoolCampus.Controller;

import NovaNectar.PoolCampus.Service.UserService;
import NovaNectar.PoolCampus.Exception.UserNotFoundException;
import NovaNectar.PoolCampus.Model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users") //Base URL
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    //http://localhost:8080/api/users/register
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.OK).body("User registered successfully");
        //new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    //http://localhost:8080/api/users/login
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        try {
            userService.loginUser(user);
            return ResponseEntity.ok("Login successful");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    //http://localhost:8080/api/users/1
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    //Find ID
    //http://localhost:8080/api/users/find
    @PostMapping("/find")
    public ResponseEntity<?> findUser(@RequestBody User user) {
        Optional<User> foundUser = userService.findUser(user.getUsername(), user.getPassword(), user.getEmail());

        if (foundUser.isPresent()) {
            return ResponseEntity.ok(foundUser.get().getId());  // Return only the user id
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

//Update User Details
//http://localhost:8080/api/users/9
@PutMapping("/{id}")
public User updateUser(@PathVariable Long id, @RequestBody User user) {
    return userService.updateUser(id, user.getUsername(), user.getEmail());
}

}
