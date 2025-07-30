package dev.sarim.Data;

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
}
