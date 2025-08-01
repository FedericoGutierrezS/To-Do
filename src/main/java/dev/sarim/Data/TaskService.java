package dev.sarim.Data;

import java.util.List;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.sarim.Task.Task;
import dev.sarim.Task.TaskState;

import jakarta.jms.Connection;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

@Service
@Transactional
public class TaskService {
	
	@Autowired
	TaskRepository repo;
	
	@Autowired
	ActiveMQConnectionFactory queueFactory;
	
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
		archiveTask(t);
		repo.delete(t);
	}
	
	private void archiveTask(Task t) {
		Connection queueConn = null;
		Session session = null;
		MessageProducer producer = null;
		TextMessage msg = null;
		try {
			queueConn = queueFactory.createConnection();
			queueConn.start();
			session = queueConn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			System.err.println("Connection to the ActiveMQ queue failed");
			e.printStackTrace();
		}
		try {
			Destination destination = session.createQueue("taskArchive");
			producer = session.createProducer(destination);
		} catch (JMSException e) {
			System.err.println("Failed to create the queue");
			e.printStackTrace();
		}
		try {
			msg = session.createTextMessage(t.getTitle());
			producer.send(msg);
		} catch (JMSException e) {
			System.err.println("Failed while sending the message");
			e.printStackTrace();
		}

	}

}
