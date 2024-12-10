import org.example.controller.TaskController;
import org.example.controller.TaskInfo;
import org.example.domain.Status;
import org.example.domain.Task;
import org.example.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    private static final Logger log = LoggerFactory.getLogger(TaskControllerTest.class);
    @Mock
    private TaskService taskService;

    @Mock
    private Model model;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Инициализация моков
    }

    @Test
    void getAllTasks() {

        List<Task> mockTasks = Arrays.asList(
                new Task(1, "Task 1", Status.DONE),
                new Task(2, "Task 2", Status.IN_PROGRESS)
        );
        when(taskService.getAllTasks(0, 10)).thenReturn(mockTasks);
        when(taskService.getAllCount()).thenReturn(20);


        String viewName = taskController.getAllTasks(model, 1, 10);

        // Assert
        assertEquals("tasks", viewName);
        verify(model).addAttribute("tasks", mockTasks);
        verify(model).addAttribute("current_page", 1);
        ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);
        verify(model).addAttribute(eq("page_numbers"), captor.capture());
        assertEquals(Arrays.asList(1, 2), captor.getValue());
    }

    @Test
    void editTask() {
        // Arrange
        TaskInfo info = new TaskInfo();
        info.setDescription("Updated description");
        info.setStatus(Status.IN_PROGRESS);
        int taskId = 1;

        // Act
        String viewName = taskController.editTask(model, taskId, info);

        // Assert
        assertEquals("tasks", viewName);
        verify(taskService).editTask(taskId, "Updated description", Status.IN_PROGRESS);
        verify(taskService).getAllTasks(0, 10); // Перезагрузка задач
    }

    @Test
    void addTask() {
        // Arrange
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setDescription("New Task");
        taskInfo.setStatus(Status.IN_PROGRESS);

        // Act
        String viewName = taskController.addTask(model, taskInfo);

        // Assert
        assertEquals("tasks", viewName);
        verify(taskService).createTask("New Task", Status.IN_PROGRESS);
        verify(taskService).getAllTasks(0, 10); // Перезагрузка задач
    }

    @Test
    void deleteTask() {
        // Arrange
        int taskId = 1;

        // Act
        String viewName = taskController.deleteTask(model, taskId);

        // Assert
        assertEquals("tasks", viewName);
        verify(taskService).deleteTask(taskId);
        verify(taskService).getAllTasks(0, 10); // Перезагрузка задач
    }
}
