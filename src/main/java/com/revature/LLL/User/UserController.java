package com.revature.LLL.User;

import com.revature.LLL.User.dtos.UserResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> postNewUser(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(user));
    }

    @GetMapping("/farmers")
    public ResponseEntity<List<UserResponseDTO>> getAllFarmers(){
        return ResponseEntity.ok(userService.findAllFarmers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PatchMapping("/forgotPassword")
    private ResponseEntity<Void> patchForgotPassword(@Valid @RequestBody String password, String email){
        try{
            User user = userService.findByEmail(email);
            user.setPassword(password);
            userService.update(user);
            return ResponseEntity.noContent().build();
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }


}
