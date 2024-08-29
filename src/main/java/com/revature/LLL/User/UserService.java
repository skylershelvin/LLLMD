package com.revature.LLL.User;

import com.revature.LLL.User.dtos.OwnerInfoDTO;
import com.revature.LLL.User.dtos.UserResponseDTO;
import com.revature.LLL.util.exceptions.DataNotFoundException;
import com.revature.LLL.util.exceptions.InvalidInputException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.revature.LLL.User.dtos.UserResponseDTO;
import com.revature.LLL.util.exceptions.DataNotFoundException;
import com.revature.LLL.util.exceptions.InvalidInputException;
import com.revature.LLL.util.interfaces.Serviceable;

import javax.naming.AuthenticationException;
import javax.xml.crypto.Data;
import java.util.List;

/**
 * UserService provides business logic for user operations such as creating a
 * user, finding a user by email in password for authenthication, and conversion
 * of user info into other form of data through DTO's
 *
 * It interacts with the UserRepository to perform database interactions
 */
@Service
public class UserService implements Serviceable<User> {

    private final UserRepository userRepository;
    //including Passwordencoder spring bean here, this function comes from SecurityConfig file on line 46
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for UserService. The UserRepository is injected through
     * constructor injection.
     *
     * @param userRepository the repository that handles database interactions
     */
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Authenticates a user by providing email and password
     *
     * @param email the email of the user attempting to log in
     * @param password the password of the user attempting to log in
     * @return the User object if the email and password are correct otherwise
     * throws exception
     * @throws AuthenticationException gets thrown if email or password are
     * incorrect
     */
    public User findByEmailAndPassword(String email, String password) throws AuthenticationException {
        return userRepository.findByEmailAndPassword(email, password).orElseThrow(() -> new AuthenticationException("Incorrect email or password."));
    }

    /**
     * Only implemented since its part of servicable interface
     *
     * might be implemented later
     *
     * @return nothing
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Creates a new user and saves it to the database.
     *
     * @param newUser the User object to be created
     * @return the created User object after being saved to database
     * @throws InvalidInputException if the User object does not contain an
     * email or password
     */
    @Override
    public User create(User newUser) throws InvalidInputException {
        if (newUser.getEmail() == null || newUser.getPassword() == null) {
            throw new InvalidInputException("User must contain an email and password");
        }
        //hashing and salting users password here before it gets saved to the database
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.saveAndFlush(newUser);
    }

    /**
     * Finds a user by providing a id
     *
     * @param id the id given to find user
     * @return a user that matches given id
     * @throws if no user is found with provided id
     */
    @Override
    public User findById(int id) {
        try {
            return userRepository.findById(id).get();
        } catch (DataNotFoundException e) {
            throw new DataNotFoundException("No such user with that id found");
        }

    }

    /**
     * Updates an existing user into the database
     *
     * @param updatedObject the User object containing updated info
     * @return the updated User object after being saved to database
     */
    @Override
    public User update(User updatedObject) {
        return userRepository.save(updatedObject);
//     public User update(User updatedUser) {
//         return userRepository.save(updatedUser);

    }

    /**
     * Deletes a user from the database.
     *
     * not yet implemented
     *
     * @param deletedObject the User object to be deleted
     * @return a boolean seeing if delete was successful or not
     */
    @Override
    public Boolean delete(User deletedObject) {
        return null;
    }

    /**
     * Retrieves a list of all users with the userType OWNER and converts them
     * to UserResponseDTO objects.
     *
     * @return a list of UserResponseDTO objects representing all users with the
     * userType OWNER.
     * @throws DataNotFoundException if no users with the userType OWNER are
     * found.
     */
    public List<UserResponseDTO> findAllFarmers() {
        List<User> owners = userRepository.findAll();
        if (owners.isEmpty()) {
            throw new DataNotFoundException("No farmers found.");
        }

        return owners.stream()
                .map(UserResponseDTO::new)
                .toList();
    }
    //todo: find by email

    public User findByEmail(String email) throws AuthenticationException {
        return (User) userRepository.findByEmail(email).orElseThrow(() -> new AuthenticationException("Incorrect email"));
    }

}
