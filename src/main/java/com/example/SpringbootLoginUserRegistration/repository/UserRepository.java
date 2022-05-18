package com.example.SpringbootLoginUserRegistration.repository;

import com.example.SpringbootLoginUserRegistration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);

    @Query("SELECT c FROM User c WHERE c.username = ?1")
    public User findByEmail(String email);

    public User findByResetPasswordToken(String token);

}
