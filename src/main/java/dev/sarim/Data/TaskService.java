package dev.sarim.Data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.sarim.Task.Task;
import dev.sarim.Task.TaskState;

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
	
	public void closeTask(String title) {
		int id = repo.getFirstIdByTitle(title);
		Task t = new Task(id, title, TaskState.CLOSED);
		repo.update(t);
	}
	
	public List<Task> getAllTasks(){
		return repo.getAll();
	}

	public void deleteTask(String title) {
		Task t = repo.getFirstTaskByTitle(title);
		repo.delete(t);
	}

}
