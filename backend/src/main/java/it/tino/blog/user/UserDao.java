package it.tino.blog.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserDao extends JpaRepository<SpringUser, UUID> {

    Optional<SpringUser> findByEmail(String username);
}
