package com.example.imagecaptionbackend.service;

import com.example.imagecaptionbackend.entity.User;
import com.example.imagecaptionbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private void getPermission(String role) {
        if (!role.equals("admin"))
            throw new IllegalArgumentException("You are not an admin");
    }

    public User registerUser(String username, String password, int role) {
        if (role != 0 && role != 1)
            throw new IllegalArgumentException("Role must be 0 or 1");
        User user = new User(username, password, role == 0 ? "admin" : "user");
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Username already exists");
        }
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("Username does not exist");
        }
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Password is incorrect");
        }
        return user;
    }

    public void updateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("Username does not exist");
        }
        user.setPassword(password);
        userRepository.save(user);
    }

    public void deleteUser(String role, String username) {
        getPermission(role);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("Username does not exist");
        }
        userRepository.delete(user);
    }

    public User getUser(String role, String username) {
        getPermission(role);
        return userRepository.findByUsername(username);
    }

    public Page<User> getAllUsers(String role, int page, int size) {
        getPermission(role);
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }
}
