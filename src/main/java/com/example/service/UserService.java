package com.example.service;

import com.example.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);

}
