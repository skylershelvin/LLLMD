package com.revature.LLL.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.revature.LLL.util.interfaces.Serviceable;

import java.util.List;

@Service
public class UserService implements Serviceable<User> {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
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
}
