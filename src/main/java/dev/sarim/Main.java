package dev.sarim;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	
	

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Stage root = FXMLLoader.load(getClass().getResource("toDoUi.fxml"));
		root.setHeight(600);
		root.setWidth(400);
		root.setTitle("ToDoApp");
		root.initStyle(StageStyle.UNDECORATED);
		root.show();
	}

}
