
import org.example.controller.TaskInfo;
import org.example.dao.TaskDAO;
import org.example.domain.Status;
import org.example.domain.Task;
import org.example.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskDAO taskDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTask() {

        TaskInfo taskInfo = new TaskInfo("New Task", Status.IN_PROGRESS);
        doNothing().when(taskDAO).saveOrUpdateTask(any(Task.class));


        taskService.createTask(taskInfo.getDescription(), taskInfo.getStatus());


        verify(taskDAO).saveOrUpdateTask(any(Task.class));
    }
}
