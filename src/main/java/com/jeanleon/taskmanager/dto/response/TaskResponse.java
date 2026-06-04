// TaskResponse.java
package com.jeanleon.taskmanager.dto.response;

import lombok.*;
import com.jeanleon.taskmanager.enums.TaskStatus;
import com.jeanleon.taskmanager.enums.TaskPriority;
import java.time.LocalDateTime;

@Data
@Builder
public class TaskResponse {
    private String id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private Integer position;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueDate;
    private String userId;
    private String userName;
}