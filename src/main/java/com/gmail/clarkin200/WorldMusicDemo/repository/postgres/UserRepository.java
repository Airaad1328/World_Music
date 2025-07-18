package com.gmail.clarkin200.WorldMusicDemo.repository.postgres;

import com.gmail.clarkin200.WorldMusicDemo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional <User> findByEmail(String email);
}
