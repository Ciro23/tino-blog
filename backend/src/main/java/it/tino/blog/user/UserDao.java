package it.tino.blog.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

interface UserDao extends JpaRepository<SpringUser, UUID> {

    Optional<SpringUser> findByEmail(String username);
}
