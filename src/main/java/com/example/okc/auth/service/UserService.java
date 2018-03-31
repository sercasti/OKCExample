package com.example.okc.auth.service;

import com.example.okc.auth.model.User;

public interface UserService {
	void save(User user);

	User findByUsername(String username);
}
