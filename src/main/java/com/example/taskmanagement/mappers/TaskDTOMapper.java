package com.example.taskmanagement.mappers;

import com.example.taskmanagement.models.Task;
import com.example.taskmanagement.models.response.TaskResponse;
import org.springframework.stereotype.Component;

@Component
public class TaskDTOMapper {
    public TaskResponse convertToTaskDTO(Task task) {
        TaskResponse taskDto = new TaskResponse();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setDueDate(task.getDueDate());
        taskDto.setPriority(task.getPriority());
        taskDto.setStatus(task.getStatus());
        taskDto.setCategory(task.getCategory());
        return taskDto;
    }
}
