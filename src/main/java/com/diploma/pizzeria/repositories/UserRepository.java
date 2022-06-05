package com.diploma.pizzeria.repositories;

import com.diploma.pizzeria.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, PagingAndSortingRepository<User, Long> {
    List<User> findAllByUserRoleOrderByIdDesc(String role);
    Optional<User> findByEmail(String email);
    User findUserByEmail(String email);
    User findUserById(Long id);
}