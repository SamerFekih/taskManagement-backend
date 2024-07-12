package com.example.taskmanagement.services;

import com.example.taskmanagement.models.Task;
import com.example.taskmanagement.models.response.TaskPriorityStatisticsResponse;
import com.example.taskmanagement.repositories.TaskRepository;
import com.example.taskmanagement.repositories.UserRepository;
import com.example.taskmanagement.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class StatisticsService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    @Autowired
    public StatisticsService(TaskRepository taskRepository,UserRepository userRepository){
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }
    public TaskPriorityStatisticsResponse getTaskPriorityStatistics(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Task> tasks = taskRepository.findByUser(user);
        Map<String, Map<String, Double[]>> priorityCount = tasks.stream()
                .collect(Collectors.groupingBy(
                        Task::getCategory,
                        Collectors.collectingAndThen(
                                Collectors.groupingBy(
                                        Task::getPriority,
                                        Collectors.counting()
                                ),
                                map -> {
                                    long totalTasks = map.values().stream().mapToLong(Long::longValue).sum();
                                    return map.entrySet().stream().collect(Collectors.toMap(
                                            Map.Entry::getKey,
                                            entry -> new Double[]{
                                                    (double) entry.getValue() / totalTasks * 100,
                                                    entry.getValue().doubleValue()
                                            }
                                    ));
                                }
                        )
                ));

        return new TaskPriorityStatisticsResponse(priorityCount);
    }
}
