package com.revature.LLL.User;

import com.revature.LLL.User.dtos.UserResponseDTO;
import com.revature.LLL.util.exceptions.DataNotFoundException;
import com.revature.LLL.util.exceptions.InvalidInputException;
import com.revature.LLL.util.exceptions.UnauthorizedException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
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
        try{
            return ResponseEntity
                    .status(201)
                    .body(userService.create(user));

        } catch (InvalidInputException e){
            return ResponseEntity
                    .status(400)
                    .body(user);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable int userId, @RequestHeader("userId") String parseId){
        try{
            int headerId = Integer.parseInt(parseId);

            if(userId != headerId){
                throw new UnauthorizedException("You are not allowed to view this profile!");
            }

            return ResponseEntity
                    .status(200)
                    .body(userService.findById(userId));

        } catch (DataNotFoundException e){
            return ResponseEntity
                    .status(404)
                    .build();
        } catch (UnauthorizedException e){
            return ResponseEntity
                    .status(403)
                    .build();
        }

    }

    @PutMapping()
    public ResponseEntity<User> putUpdateInfo(@RequestBody User user){
        try{
            return ResponseEntity
                    .status(200)
                    .body(userService.update(user));
        } catch(InvalidInputException e){
            return ResponseEntity
                    .status(400)
                    .build();
        } catch(DataNotFoundException e){
            return ResponseEntity
                    .status(404)
                    .build();
        }
    }

    @GetMapping("/farmers")
    public ResponseEntity<List<UserResponseDTO>> getAllFarmers(){
        return ResponseEntity.ok(userService.findAllFarmers());
    }
}
