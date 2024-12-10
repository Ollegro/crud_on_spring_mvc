import org.example.dao.TaskDAO;
import org.example.domain.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TaskDAOTest {
    private Session session;
    private TaskDAO taskDAO;

    @BeforeEach
    void setUp() {
        SessionFactory sessionFactory = mock(SessionFactory.class);
        session = mock(Session.class);

        when(sessionFactory.getCurrentSession()).thenReturn(session);

        taskDAO = new TaskDAO(sessionFactory);
    }

    @Test
    void testGetAllTasks() {
        // Настройка
        Query<Task> query = mock(Query.class);
        when(session.createQuery("select t from Task t", Task.class)).thenReturn(query);
        List<Task> expectedTasks = Arrays.asList(new Task(), new Task());
        when(query.setFirstResult(anyInt())).thenReturn(query);
        when(query.setMaxResults(anyInt())).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedTasks);

        // Выполнение
        List<Task> tasks = taskDAO.getAllTasks(0, 10);

        // Проверка
        assertEquals(expectedTasks, tasks);
        verify(query).setFirstResult(0);
        verify(query).setMaxResults(10);
        verify(query).getResultList();
    }

    @Test
    void testGetAllCount() {
        // Настройка
        Query<Long> query = mock(Query.class);
        when(session.createQuery("select count(*) from Task t", Long.class)).thenReturn(query);
        when(query.uniqueResult()).thenReturn(100L);

        // Выполнение
        int count = taskDAO.getAllCount();

        // Проверка
        assertEquals(100, count);
        verify(query).uniqueResult();
    }

    @Test
    void testGetTaskById() {
        // Настройка
        Query<Task> query = mock(Query.class);
        Task expectedTask = new Task();
        when(session.createQuery("select t from Task t where t.id = :id", Task.class)).thenReturn(query);
        when(query.setParameter("id", 1)).thenReturn(query);
        when(query.uniqueResult()).thenReturn(expectedTask);

        // Выполнение
        Task task = taskDAO.getTaskById(1);

        // Проверка
        assertEquals(expectedTask, task);
        verify(query).setParameter("id", 1);
        verify(query).uniqueResult();
    }

    @Test
    void testSaveOrUpdateTask() {
        // Настройка
        Task task = new Task();

        // Выполнение
        taskDAO.saveOrUpdateTask(task);

        // Проверка
        verify(session).persist(task);
    }

    @Test
    void testDeleteTask() {
        // Настройка
        Task task = new Task();

        // Выполнение
        taskDAO.deleteTask(task);

        // Проверка
        verify(session).remove(task);
    }
}
