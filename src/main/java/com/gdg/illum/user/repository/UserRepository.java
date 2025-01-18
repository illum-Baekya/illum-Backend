package com.gdg.illum.user.repository;

import com.gdg.illum.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.loginId = :loginId and u.password = :password")
    Optional<User> findUserByLoginIdAndPassword(@Param("loginId") String loginId, @Param("password") String password);
}
