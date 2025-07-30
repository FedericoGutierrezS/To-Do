package dev.sarim.Data;

import java.util.List;

import org.springframework.stereotype.Repository;

import dev.sarim.Task.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class TaskRepository {
	@PersistenceContext
	EntityManager entityManager;
	
	public Task getById(int id) {
		TypedQuery<Task> query = entityManager.createQuery("FROM Task AS t WHERE t.id = :id", Task.class);
		return query.setParameter("id", id).getSingleResult();
	}
	
	public void saveTask(Task t) {	
		entityManager.persist(t);
	}
	
	public int getFirstIdByTitle(String title) {
		TypedQuery<Task> query = entityManager.createQuery("FROM Task AS t WHERE t.title = :title AND t.state = OPEN", Task.class);
		return query.setParameter("title", title).getResultList().get(0).getId();
	}
	
	public void update(Task t) {
		entityManager.merge(t);
	}
	
	public List<Task> getAll(){
		TypedQuery<Task> query = entityManager.createQuery("FROM Task", Task.class);
		return query.getResultList();
	}
	
	public Task getFirstTaskByTitle(String title) {
		TypedQuery<Task> query = entityManager.createQuery("FROM Task AS t WHERE t.title = :title AND t.state = CLOSED", Task.class);
		return query.setParameter("title", title).getResultList().get(0);
	}
	
	public void delete(Task t) {
		entityManager.remove(t);
	}
}
