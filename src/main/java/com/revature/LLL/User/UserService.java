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
        return null;
    }

    @Override
    public Boolean update(User updatedObject) {
        return null;
    }

    @Override
    public Boolean delete(User deletedObject) {
        return null;
    }

    public List<UserResponseDTO> findAllFarmers() {
        return userRepository.findByUserType(User.userType.OWNER)
                .orElseThrow(() -> new DataNotFoundException("No farmers found."))
                .stream()
                .map(UserResponseDTO::new)
                .toList();
    }
}
