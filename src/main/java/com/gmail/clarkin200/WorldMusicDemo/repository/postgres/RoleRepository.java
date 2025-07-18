package com.gmail.clarkin200.WorldMusicDemo.repository.postgres;

import com.gmail.clarkin200.WorldMusicDemo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role,Long> {
}
