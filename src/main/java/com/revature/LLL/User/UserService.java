package com.revature.LLL.User;

import com.revature.LLL.util.exceptions.DataNotFoundException;
import com.revature.LLL.util.exceptions.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.revature.LLL.util.interfaces.Serviceable;

import javax.naming.AuthenticationException;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
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
    public User create(User newUser) throws InvalidInputException {
        if(newUser.getEmail() == null || newUser.getPassword() == null){
            throw new InvalidInputException("User must contain an email and password");
        }

        return userRepository.saveAndFlush(newUser);
    }

    @Override
    public User findById(int id){
        try{
            return userRepository.findById(id).get();
        } catch(NoSuchElementException e){
            throw new DataNotFoundException("No such user with that id found");
        }

    }

    @Override
    public User update(User updatedUser) {
        return userRepository.save(updatedUser);
    }

    @Override
    public Boolean delete(User deletedObject) {
        return null;
    }
}
