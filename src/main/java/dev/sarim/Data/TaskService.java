package dev.sarim.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.sarim.Task.Task;

@Service
@Transactional
public class TaskService {
	
	@Autowired
	TaskRepository repo;
	
	public Task getTask(int id) {
		return repo.getById(id);
	}
	
	public void saveTask(Task t) {
		repo.saveTask(t);
	}

}
