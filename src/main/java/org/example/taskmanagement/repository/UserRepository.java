package org.example.taskmanagement.repository;

import org.example.taskmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
