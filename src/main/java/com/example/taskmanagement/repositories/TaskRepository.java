package com.example.taskmanagement.repositories;

import com.example.taskmanagement.models.Task;
import com.example.taskmanagement.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM tasks WHERE id IN :ids AND user_id = :userId", nativeQuery = true)
    void deleteByTaskIdsAndUserId(@Param("ids") List<Long> ids, @Param("userId") Long userId);
}