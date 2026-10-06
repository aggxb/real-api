package com.aggxb.real_api.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query("""
                SELECT u
                FROM UserEntity u
                LEFT JOIN FETCH u.roles
                WHERE LOWER(u.username) = LOWER(:username)
            """)
    Optional<UserEntity> findByUsername(String username);

    @Query("""
                SELECT u
                FROM UserEntity u
                LEFT JOIN FETCH u.roles
                WHERE LOWER(u.username) = LOWER(:identifier)
                OR LOWER(u.email) = LOWER(:identifier)
            """)
    Optional<UserEntity> findByIdentifier(String identifier);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
