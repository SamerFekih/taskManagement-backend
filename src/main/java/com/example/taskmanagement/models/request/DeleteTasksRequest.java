package com.example.taskmanagement.models.request;

import lombok.Data;

import java.util.List;

@Data
public class DeleteTasksRequest {
    private List<Long> taskIds;
}
