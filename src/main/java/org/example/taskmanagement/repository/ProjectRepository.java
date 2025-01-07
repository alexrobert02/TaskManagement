package org.example.taskmanagement.repository;

import org.example.taskmanagement.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p WHERE " +
            "(:name IS NULL OR p.name LIKE %:name%) AND " +
            "(:description IS NULL OR p.description LIKE %:description%)")
    List<Project> searchProjects(String name, String description);
}
