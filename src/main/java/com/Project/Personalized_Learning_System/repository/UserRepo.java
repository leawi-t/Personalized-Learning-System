package com.Project.Personalized_Learning_System.repository;

import com.Project.Personalized_Learning_System.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"subjects"})
    Optional<User> findById(Long id);

    @EntityGraph(attributePaths = {"subjects"})
    Optional<User> findByUsername(String username);

    @EntityGraph(attributePaths = {"subjects"})
    Optional<User> findByEmail(String email);

    Page<User> findAll(Pageable pageable);
}
