package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entity.User;
import com.project.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public String login(User user) {

        User dbUser = userRepo.findByUsername(user.getUsername());

        if (dbUser == null) {
            return "User Not Found";
        }

        if (!dbUser.getPassword().equals(user.getPassword())) {
            return "Wrong Password";
        }

        return dbUser.getRole();
    }

    // Optional: add user
    public User saveUser(User user) {
        return userRepo.save(user);
    }
}
