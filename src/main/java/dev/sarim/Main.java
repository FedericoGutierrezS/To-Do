package dev.sarim;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import dev.sarim.Data.TaskService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@Configuration
@ComponentScan("dev.sarim.Config")
@ComponentScan("dev.sarim.Data")
public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ApplicationContext appContext = new AnnotationConfigApplicationContext(Main.class);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("toDoUi.fxml"));
		Stage root = loader.load();
		ToDoController uiController = loader.getController();
		uiController.setTaskService(appContext.getBean(TaskService.class));
		root.setHeight(600);
		root.setWidth(400);
		root.setTitle("ToDoApp");
		root.initStyle(StageStyle.UNDECORATED);
		uiController.loadTasks();
		root.show();
	}

}
