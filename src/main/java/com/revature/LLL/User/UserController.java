package com.revature.LLL.User;

import com.revature.LLL.User.dtos.PasswordRequestDTO;
import com.revature.LLL.User.dtos.UserResponseDTO;

import jakarta.validation.Valid;

import com.revature.LLL.util.exceptions.DataNotFoundException;
import com.revature.LLL.util.exceptions.InvalidInputException;
import com.revature.LLL.util.exceptions.UnauthorizedException;
import org.apache.coyote.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The UserController handles all HTTP requests related to User operations such as
 * registering a new user, getting a user, updating a user, etc...
 *
 * It uses the userService to handle business logic.
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    /**
     * Constructor for UserController. T
     * The UserService is injected using constructor injection.
     * @param userService the service that handles the business logic.
     */
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * Registers a new user by accepting a POST request with a User object.
     *
     * @param user the User object to be created
     * @return ResponseEntity containing the newly created User object or returns a status 400
     */
    @PostMapping("/register")
    public ResponseEntity<User> postNewUser(@RequestBody User user){
        try{
            return ResponseEntity
                    .status(201)
                    .body(userService.create(user));

        } catch (InvalidInputException e){
            return ResponseEntity
                    .status(400)
                    .build();
        }
    }

    /**
     * Retrieves user info by userId. The request is only authorized if the userId
     * in the URL matches the userId passed in the header.
     *
     * @param userId  the ID of the user to retrieve
     * @param parseId the userId passed in the header
     * @return ResponseEntity containing the User object, or an error status otherwise
     */
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

    /**
     * Updates the information of an existing user.
     *
     * @param user the User object containing updated info
     * @return ResponseEntity containing the updated User object, or an error status otherwise
     */
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

    /**
     * Retrieves all users who are designated as farmers.
     *
     * @return ResponseEntity containing a list of UserResponseDTOs for all farmers
     */
    @GetMapping("/farmers")
    public ResponseEntity<List<UserResponseDTO>> getAllFarmers(){
        return ResponseEntity.ok(userService.findAllFarmers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PatchMapping("/forgotPassword")
    private ResponseEntity<Void> patchForgotPassword(@Valid @RequestBody PasswordRequestDTO request){
        try{
            User user = userService.findByEmail(request.getEmail());
            user.setPassword(request.getPassword());
            userService.update(user);
            return ResponseEntity.noContent().build();
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }

}
