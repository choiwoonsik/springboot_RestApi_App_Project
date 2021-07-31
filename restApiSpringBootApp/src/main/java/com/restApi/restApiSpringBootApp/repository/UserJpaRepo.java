package com.restApi.restApiSpringBootApp.repository;

import com.restApi.restApiSpringBootApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserJpaRepo extends JpaRepository<User, Long> {

    User findByName(String name);

    User findByEmail(String email);
}
