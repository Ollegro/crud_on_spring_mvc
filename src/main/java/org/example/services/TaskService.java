package org.example.services;

import org.example.dao.TaskDAO;
import org.example.domain.Status;
import org.example.domain.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.isNull;

@Service

public class TaskService {
    private final TaskDAO taskDAO;

    public TaskService(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public List<Task> getAllTasks(int offset, int limit) {
        return taskDAO.getAllTasks(offset, limit);
    }

     public int getAllCount() {
        return taskDAO.getAllCount();
    }

    @Transactional
    public Task editTask(int id, String description, Status status) {
        Task task = taskDAO.getTaskById(id);
        if (isNull(task)) {
            throw new RuntimeException("Task not found");
        }

        task.setDescription(description);
        task.setStatus(status);
        taskDAO.saveOrUpdateTask(task);
        return task;

    }

    public Task createTask(String description, Status status) {
        Task task = new Task();
        task.setDescription(description);
        task.setStatus(status);
        taskDAO.saveOrUpdateTask(task);
        return task;
    }

    @Transactional
    public void deleteTask(int id) {

        Task task = taskDAO.getTaskById(id);
        if (task == null) {
            throw new RuntimeException("Task not found");
        }

        taskDAO.deleteTask(task);

    }


}
