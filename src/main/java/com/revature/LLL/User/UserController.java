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

    /**
     * Retrieves a user by their ID, but only if the requester is a veterinarian.
     *
     * @param userId the ID of the user to retrieve
     * @param userType the type of the user making the request, which must be "VET" to access this endpoint
     * @return a ResponseEntity containing the user if found and the requester is authorized, or an appropriate error status
     * @throws DataNotFoundException if no user with the given ID is found
     * @throws UnauthorizedException if the requester is not a vet
     */
    @GetMapping("/farmers/{userId}")
    public ResponseEntity<User> getUserByUserIdForVets(@PathVariable int userId, @RequestHeader("userType") String userType){
        try{
            if(!userType.equals("VET")){
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

    /**
     * Retrieves a list of all owners, but only if the requester is a veterinarian.
     *
     * @param userType the type of the user making the request, which must be "VET" to access this endpoint
     * @return a ResponseEntity containing a list of UserResponseDTO objects representing all owners if the requester is authorized, or an appropriate error status
     * @throws DataNotFoundException if no owners are found
     * @throws UnauthorizedException if the requester is not a vet
     */
    @GetMapping("/farmers")
    public ResponseEntity<List<UserResponseDTO>> getAllOwnersForVets(@RequestHeader("userType") String userType){
        try{
            if(!userType.equals("VET")){
                throw new UnauthorizedException("You are not allowed to view this profile!");
            }

            return ResponseEntity.ok(userService.findAllOwners());
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
}
