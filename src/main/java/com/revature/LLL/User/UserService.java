package com.revature.LLL.User;

import com.revature.LLL.User.dtos.UserResponseDTO;
import com.revature.LLL.util.exceptions.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.revature.LLL.util.interfaces.Serviceable;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements Serviceable<User> {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User findByEmailAndPassword(String email, String password) throws AuthenticationException{
        return userRepository.findByEmailAndPassword(email, password).orElseThrow(() -> new AuthenticationException("Incorrect email or password."));
    }
    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public User create(User newUser) {
        return userRepository.saveAndFlush(newUser);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("No user found with id " + id));
    }

    @Override
    public Boolean update(User updatedObject) {
        userRepository.save(updatedObject);
        return true;
    }

    @Override
    public Boolean delete(User deletedObject) {
        return null;
    }

    /**
     * Retrieves a list of all users with the userType OWNER and converts them to UserResponseDTO objects.
     *
     * @return a list of UserResponseDTO objects representing all users with the userType OWNER.
     * @throws DataNotFoundException if no users with the userType OWNER are found.
     */
    public List<UserResponseDTO> findAllFarmers() {
        return userRepository.findByUserType(User.userType.OWNER)
                .orElseThrow(() -> new DataNotFoundException("No farmers found."))
                .stream()
                .map(UserResponseDTO::new)
                .toList();
    }
    //todo: find by email

    public User findByEmail(String email) throws AuthenticationException {
        return (User) userRepository.findByEmail(email).orElseThrow(()-> new AuthenticationException("Incorrect email"));
    }

}
