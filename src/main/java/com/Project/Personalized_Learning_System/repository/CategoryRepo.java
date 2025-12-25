package com.Project.Personalized_Learning_System.repository;

import com.Project.Personalized_Learning_System.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    List<Category> findByUserId(long userId);

    @Query("""
    SELECT c FROM Category c
    WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
""")
    public List<Category> searchCategory(String keyword);

    Optional<Category> findByUserIdAndId(long userId, long categoryId);
}
