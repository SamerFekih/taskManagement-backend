package com.example.taskmanagement.models.response;

import java.util.Map;

public class TasksStatisticsResponse {
    private Map<String, Long> statusCount;

    public TasksStatisticsResponse(Map<String, Long> statusCount) {
        this.statusCount = statusCount;
    }

    public Map<String, Long> getStatusCount() {
        return statusCount;
    }

    public void setStatusCount(Map<String, Long> statusCount) {
        this.statusCount = statusCount;
    }
}
