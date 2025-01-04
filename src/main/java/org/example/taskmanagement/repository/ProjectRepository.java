package org.example.taskmanagement.repository;

import org.example.taskmanagement.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
