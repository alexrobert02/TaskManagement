package com.taskmanagement.repository;

import com.taskmanagement.model.Priority;
import com.taskmanagement.model.Status;
import com.taskmanagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.assignedUser.id = :userId AND t.dueDate > CURRENT_DATE")
    List<Task> findTasksWithUpcomingDueDates(Long userId);

    @Query("SELECT t FROM Task t WHERE " +
            "(:status IS NULL OR t.status = :status) AND " +
            "(:priority IS NULL OR t.priority = :priority) AND " +
            "(:assignedUserId IS NULL OR t.assignedUser.id = :assignedUserId)")
    List<Task> searchTasks(Status status, Priority priority, Long assignedUserId);
}

