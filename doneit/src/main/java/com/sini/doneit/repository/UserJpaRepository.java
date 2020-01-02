package com.sini.doneit.repository;

import com.sini.doneit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);
}
