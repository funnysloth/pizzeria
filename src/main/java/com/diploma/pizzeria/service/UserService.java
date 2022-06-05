package com.diploma.pizzeria.service;

import com.diploma.pizzeria.entities.User;
import com.diploma.pizzeria.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository repository;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    public void saveUser(User user){
        repository.save(user);
    }

    public List<User> getUsersByRole(String role){
        return repository.findAllByUserRoleOrderByIdDesc(role);
    }

    public boolean existsWithEmail(String email){
        return repository.findByEmail(email).isPresent();
    }

    public User getUserByEmail(String email) {
        return repository.findUserByEmail(email);
    }

    public User getUserById(Long id){
        return repository.findUserById(id);
    }
}