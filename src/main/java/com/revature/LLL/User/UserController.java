package com.revature.LLL.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.LLL.User.dtos.PasswordRequestDTO;
import com.revature.LLL.User.dtos.UserResponseDTO;
import com.revature.LLL.util.exceptions.DataNotFoundException;
import com.revature.LLL.util.exceptions.InvalidInputException;
import com.revature.LLL.util.exceptions.UnauthorizedException;

import jakarta.validation.Valid;

/**
 * The UserController handles all HTTP requests related to User operations such
 * as registering a new user, getting a user, updating a user, etc...
 *
 * It uses the userService to handle business logic.
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * Constructor for UserController. T The UserService is injected using
     * constructor injection.
     *
     * @param userService the service that handles the business logic.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registers a new user by accepting a POST request with a User object.
     *
     * @param user the User object to be created
     * @return ResponseEntity containing the newly created User object or
     * returns a status 400
     */
    @PostMapping("/register")
    public ResponseEntity<User> postNewUser(@RequestBody User user) {
        try {
            return ResponseEntity
                    .status(201)
                    .body(userService.create(user));

        } catch (InvalidInputException e) {
            return ResponseEntity
                    .status(400)
                    .build();
        }
    }

    /**
     * Retrieves user info by userId. The request is only authorized if the
     * userId in the URL matches the userId passed in the header.
     *
     * @param userId the ID of the user to retrieve
     * @param parseId the userId passed in the header
     * @return ResponseEntity containing the User object, or an error status
     * otherwise
     */
//    @GetMapping("/{userId}")
//    public ResponseEntity<User> getUserById(@PathVariable int userId, @RequestHeader("userId") String parseId){
//        try{
//            int headerId = Integer.parseInt(parseId);
//
//            if(userId != headerId){
//                throw new UnauthorizedException("You are not allowed to view this profile!");
//            }
//
//            return ResponseEntity
//                    .status(200)
//                    .body(userService.findById(userId));
//
//        } catch (DataNotFoundException e){
//            return ResponseEntity
//                    .status(404)
//                    .build();
//        } catch (UnauthorizedException e){
//            return ResponseEntity
//                    .status(403)
//                    .build();
//        }
//
//    }

    /**
     * Retrieves a user by their ID, but only if the requester is a
     * veterinarian.
     *
     * @param userId the ID of the user to retrieve
     * @return a ResponseEntity containing the user if found and the requester is authorized, or an appropriate error status
     * @throws DataNotFoundException if no user with the given ID is found
     * @throws UnauthorizedException if the requester is not a vet
     */
    @GetMapping("/farmers/{userId}")
    public ResponseEntity<User> getUserByUserId(@PathVariable int userId){
        try{
            return ResponseEntity
                    .status(200)
                    .body(userService.findById(userId));

        } catch (DataNotFoundException e) {
            return ResponseEntity
                    .status(404)
                    .build();
        } catch (UnauthorizedException e) {
            return ResponseEntity
                    .status(403)
                    .build();
        }

    }

    /**
     * Updates the information of an existing user.
     *
     * @param user the User object containing updated info
     * @return ResponseEntity containing the updated User object, or an error
     * status otherwise
     */
    @PutMapping()
    public ResponseEntity<User> putUpdateInfo(@RequestBody User user) {
        try {
            return ResponseEntity
                    .status(200)
                    .body(userService.update(user));
        } catch (InvalidInputException e) {
            return ResponseEntity
                    .status(400)
                    .build();
        } catch (DataNotFoundException e) {
            return ResponseEntity
                    .status(404)
                    .build();
        }
    }

    /**
     * Retrieves a list of all farmers, but only if the requester is a
     * veterinarian.
     *
     * @return a ResponseEntity containing a list of UserResponseDTO objects representing all owners if the requester is authorized, or an appropriate error status
     * @throws DataNotFoundException if no farmers are found
     * @throws UnauthorizedException if the requester is not a vet
     */
    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAllFarmers(){
        try{
            return ResponseEntity.ok(userService.findAllFarmers());
        } catch (DataNotFoundException e) {
            return ResponseEntity
                    .status(404)
                    .build();
        } catch (UnauthorizedException e) {
            return ResponseEntity
                    .status(403)
                    .build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PatchMapping("/forgotPassword")
    private ResponseEntity<Void> patchForgotPassword(@Valid @RequestBody PasswordRequestDTO request) {
        try {
            User user = userService.findByEmail(request.getEmail());
            user.setPassword(request.getPassword());
            userService.update(user);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }

}
