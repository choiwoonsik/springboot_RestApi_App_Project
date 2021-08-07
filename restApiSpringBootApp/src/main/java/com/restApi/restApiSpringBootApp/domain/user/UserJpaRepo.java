package com.restApi.restApiSpringBootApp.domain.user;

import com.restApi.restApiSpringBootApp.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepo extends JpaRepository<User, Long> {

    List<User> findByName(String name);

    Optional<User> findByEmail(String email);
}
