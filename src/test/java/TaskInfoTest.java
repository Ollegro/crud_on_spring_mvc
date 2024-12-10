import org.example.controller.TaskInfo;
import org.example.domain.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TaskInfoTest {

    @Test
    public void testDefaultConstructor() {
        // Arrange
        TaskInfo taskInfo = new TaskInfo();

        // Act & Assert
        assertNull(taskInfo.getDescription());
        assertNull(taskInfo.getStatus());
    }

    @Test
    public void testParameterizedConstructor() {
        // Arrange
        String description = "Sample Task";
        Status status = Status.IN_PROGRESS; // Предположим, что это значение вашей доменной модели

        // Act
        TaskInfo taskInfo = new TaskInfo(description, status);

        // Assert
        assertEquals(description, taskInfo.getDescription());
        assertEquals(status, taskInfo.getStatus());
    }

    @Test
    public void testSetDescription() {
        // Arrange
        TaskInfo taskInfo = new TaskInfo();
        String description = "Another Sample Task";

        // Act
        taskInfo.setDescription(description);

        // Assert
        assertEquals(description, taskInfo.getDescription());
    }

    @Test
    public void testSetStatus() {
        // Arrange
        TaskInfo taskInfo = new TaskInfo();
        Status status = Status.DONE;

        // Act
        taskInfo.setStatus(status);

        // Assert
        assertEquals(status, taskInfo.getStatus());
    }
}

