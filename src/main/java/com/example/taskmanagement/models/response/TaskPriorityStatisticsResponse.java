package com.example.taskmanagement.models.response;
import lombok.Data;

import java.util.Map;
public class TaskPriorityStatisticsResponse {
    private Map<String, Map<String, Double[]>> priorityCount;

    public TaskPriorityStatisticsResponse(Map<String, Map<String, Double[]>> priorityCount) {
        this.priorityCount = priorityCount;
    }

    public Map<String, Map<String, Double[]>> getPriorityCount() {
        return priorityCount;
    }

    public void setPriorityCount(Map<String, Map<String, Double[]>> priorityCount) {
        this.priorityCount = priorityCount;
    }
}
