package dev.sarim.Task;

public class Task {
	private int id;
	private String title;
	private TaskState state;
	
	public Task() {}
	
	public Task(String title, TaskState state) {
		this.title = title;
		this.state = state;
	}
	
	public Task(int id, String title, TaskState state) {
		this.id = id;
		this.title = title;
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public TaskState getState() {
		return state;
	}

	public void setState(TaskState state) {
		this.state = state;
	}
	
	

}
