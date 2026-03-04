package com.Project.Personalized_Learning_System.repository;

import com.Project.Personalized_Learning_System.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepo extends JpaRepository<Topic, Long>, JpaSpecificationExecutor<Topic> {

    Page<Topic> findAll(Pageable pageable);
}
