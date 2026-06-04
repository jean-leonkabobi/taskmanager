// TaskService.java
package com.jeanleon.taskmanager.service;

import com.jeanleon.taskmanager.dto.request.TaskRequest;
import com.jeanleon.taskmanager.dto.response.DashboardStats;
import com.jeanleon.taskmanager.dto.response.TaskResponse;
import com.jeanleon.taskmanager.enums.TaskStatus;
import com.jeanleon.taskmanager.exception.ResourceNotFoundException;
import com.jeanleon.taskmanager.model.Task;
import com.jeanleon.taskmanager.model.User;
import com.jeanleon.taskmanager.repository.TaskRepository;
import com.jeanleon.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public List<TaskResponse> getAllTasks(String userId, TaskStatus status) {
        List<Task> tasks;
        if (status != null) {
            tasks = taskRepository.findByUserIdAndStatusOrderByPositionAsc(userId, status);
        } else {
            tasks = taskRepository.findByUserIdOrderByPositionAsc(userId);
        }

        return tasks.stream()
                .map(this::mapToTaskResponse)
                .collect(Collectors.toList());
    }

    public TaskResponse getTaskById(String userId, String taskId) {
        Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Tâche non trouvée"));
        return mapToTaskResponse(task);
    }

    @Transactional
    public TaskResponse createTask(String userId, TaskRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        // Déterminer la position
        int position = request.getPosition() != null ?
                request.getPosition() :
                (int) taskRepository.countByUserId(userId);

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : TaskStatus.TODO)
                .priority(request.getPriority() != null ? request.getPriority() : null)
                .position(position)
                .dueDate(request.getDueDate())
                .user(user)
                .build();

        Task savedTask = taskRepository.save(task);
        return mapToTaskResponse(savedTask);
    }

    @Transactional
    public TaskResponse updateTask(String userId, String taskId, TaskRequest request) {
        Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Tâche non trouvée"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        if (request.getStatus() != null) task.setStatus(request.getStatus());
        if (request.getPriority() != null) task.setPriority(request.getPriority());
        if (request.getPosition() != null) task.setPosition(request.getPosition());
        task.setDueDate(request.getDueDate());

        Task updatedTask = taskRepository.save(task);
        return mapToTaskResponse(updatedTask);
    }

    @Transactional
    public void deleteTask(String userId, String taskId) {
        Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Tâche non trouvée"));
        taskRepository.delete(task);
    }

    @Transactional
    public TaskResponse updateTaskStatus(String userId, String taskId, TaskStatus status) {
        Task task = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Tâche non trouvée"));
        task.setStatus(status);
        Task updatedTask = taskRepository.save(task);
        return mapToTaskResponse(updatedTask);
    }

    @Transactional
    public void reorderTasks(String userId, List<String> taskIds) {
        for (int i = 0; i < taskIds.size(); i++) {
            Task task = taskRepository.findByIdAndUserId(taskIds.get(i), userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Tâche non trouvée"));
            task.setPosition(i);
            taskRepository.save(task);
        }
    }

    public DashboardStats getDashboardStats(String userId) {
        long totalTasks = taskRepository.countByUserId(userId);
        long completedTasks = taskRepository.countByUserIdAndStatus(userId, TaskStatus.DONE);
        long inProgressTasks = taskRepository.countByUserIdAndStatus(userId, TaskStatus.IN_PROGRESS);
        long todoTasks = taskRepository.countByUserIdAndStatus(userId, TaskStatus.TODO);
        double completionRate = totalTasks > 0 ? (completedTasks * 100.0) / totalTasks : 0;

        return new DashboardStats(totalTasks, completedTasks, inProgressTasks, todoTasks,
                Math.round(completionRate * 10.0) / 10.0);
    }

    private TaskResponse mapToTaskResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .position(task.getPosition())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .dueDate(task.getDueDate())
                .userId(task.getUser().getId())
                .userName(task.getUser().getFullName())
                .build();
    }
}