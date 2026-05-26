package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.project.entity.User;
import com.project.repository.UserRepository;

@RestController
@CrossOrigin


public class AuthController {
	
	 @Autowired
	    private UserRepository userRepo;

	    @PostMapping("/login")
	    public String login(@RequestBody User user) {

	        User dbUser = userRepo.findByUsername(user.getUsername());

	        if (dbUser == null) {
	            return "User Not Found";
	        }

	        if (!dbUser.getPassword().equals(user.getPassword())) {
	            return "Wrong Password";
	        }

	        return dbUser.getRole(); // ADMIN / TEACHER / PARENT
	    }


}
