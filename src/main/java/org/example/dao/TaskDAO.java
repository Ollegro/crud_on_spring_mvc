package org.example.dao;

import org.example.domain.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TaskDAO {
    public final SessionFactory sessionFactory;

    public TaskDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Task> getAllTasks(int offset, int limit) {
        Query<Task> query = getSession().createQuery("select t from Task t", Task.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public int getAllCount() {
        Query<Long> query = getSession().createQuery("select count(*) from Task t", Long.class);
//return query.getSingleResult().intValue();  протестировать в дальнейшем
        return Math.toIntExact(query.uniqueResult());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Task getTaskById(int id) {
        Query<Task> query = getSession().createQuery
                ("select t from Task t where t.id = :id", Task.class);
        query.setParameter("id", id);
        return query.uniqueResult();
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdateTask(Task task) {
        getSession().persist(task);
    }



    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteTask(Task task) {
        getSession().remove(task);
    }



    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
