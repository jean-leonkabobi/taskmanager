// TaskRequest.java
package com.jeanleon.taskmanager.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import com.jeanleon.taskmanager.enums.TaskStatus;
import com.jeanleon.taskmanager.enums.TaskPriority;
import java.time.LocalDateTime;

@Data
public class TaskRequest {
    @NotBlank(message = "Le titre est obligatoire")
    @Size(min = 1, max = 200)
    private String title;

    @Size(max = 1000)
    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    @Min(0)
    private Integer position;

    private LocalDateTime dueDate;
}