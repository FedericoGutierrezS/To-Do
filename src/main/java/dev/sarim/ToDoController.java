package dev.sarim;

import java.net.URL;
import java.util.ResourceBundle;

import dev.sarim.Data.TaskRepository;
import dev.sarim.Data.TaskService;
import dev.sarim.Task.Task;
import dev.sarim.Task.TaskState;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ToDoController{
	
	private TaskService taskServ;

	public void setTaskService(TaskService serv) {
		this.taskServ = serv;
	}

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label OngoingTask;

    @FXML
    private Button addTask;

    @FXML
    private Label addedTask;

    @FXML
    private Label appName;

    @FXML
    private Button close;

    @FXML
    private Button deleteTask;

    @FXML
    private Label doneOverTotal;

    @FXML
    private Button editTask;

    @FXML
    private Button minimize;

    @FXML
    private ImageView ongoingTaskStatus;

    @FXML
    private TextField taskInput;

    @FXML
    private ImageView taskStatus;

    @FXML
    private VBox tasksPane;
    
    @FXML
    private Label lblStatus;
    
    @FXML
    private BorderPane bp;
    
    @FXML
    private Stage primaryStage;
    
    private int taskDoneCounter = 0;
    private int taskTotalCounter = 0;
    
    private double xOffset = 0;
	private double yOffset = 0;

    @FXML
    void initialize() {        
        lblStatus.setText("App ready.");
        bp.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        bp.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
        
    }
    
    public void loadTasks() {
    	taskServ.getAllTasks().stream().forEach(task -> addTask(task));
    	updateDoneOverTotal();
    }
    
    void addTask(Task t) {
    	HBox hbox = new HBox();
		 hbox.setPrefHeight(6);
		 hbox.getStyleClass().add("task");
		 hbox.setPrefWidth(Region.USE_COMPUTED_SIZE);
		 hbox.setSpacing(5);
		 hbox.setPadding(new Insets(9));
		 hbox.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		 hbox.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		 
		 Text textco = new Text(t.getTitle());
		 Label label = new Label();
		 label.prefWidth(310);
		 label.prefHeight(25);
		 label.setMinWidth(278);
		 textco.setFill(Color.WHITE);
		 if(t.taskClosed()) {
			 textco.setStrikethrough(true);
			 textco.fillProperty().set(Color.GREY);
			 taskDoneCounter++;
		 }
		 label.setGraphic(textco);
		 textco.setFont(Font.font("Arial", FontWeight.BOLD, 13));
		 
		 Button btnDel = new Button();
		 btnDel.prefHeight(32);
		 btnDel.prefWidth(32);
		 Image imageDel = new Image(getClass().getResourceAsStream("Remove.png"));
		 ImageView imageViewDel = new ImageView(imageDel);
		 imageViewDel.setFitHeight(32);
		 imageViewDel.setFitWidth(32);
		 btnDel.setGraphic(imageViewDel);
		 btnDel.setOnAction(new EventHandler<ActionEvent>() {
			    @Override
			    public void handle(ActionEvent event) {
			        deleteTask(event);
			    }
			});
		 
		 hbox.getChildren().addAll(label, btnDel);
		 tasksPane.getChildren().addFirst(hbox);
		 taskTotalCounter++;
    }
    
	 @FXML
    void addTask(ActionEvent event) {
		 String title = taskInput.getText();
		 if(title == null || title.isBlank()) {
			 lblStatus.setText("Task title can't be empty");
		 } else {
			 HBox hbox = new HBox();
			 hbox.setPrefHeight(6);
			 hbox.getStyleClass().add("task");
			 hbox.setPrefWidth(Region.USE_COMPUTED_SIZE);
			 hbox.setSpacing(5);
			 hbox.setPadding(new Insets(9));
			 hbox.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
			 hbox.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
			 
			 Text textco = new Text(title);
			 Label label = new Label();
			 label.prefWidth(310);
			 label.prefHeight(25);
			 label.setMinWidth(278);
			 
			 label.setGraphic(textco);
			 textco.setFill(Color.WHITE);
			 textco.setFont(Font.font("Arial", FontWeight.BOLD, 13));
			 
			 Button btnDel = new Button();
			 btnDel.prefHeight(32);
			 btnDel.prefWidth(32);
			 Image imageDel = new Image(getClass().getResourceAsStream("Remove.png"));
			 ImageView imageViewDel = new ImageView(imageDel);
			 imageViewDel.setFitHeight(32);
			 imageViewDel.setFitWidth(32);
			 btnDel.setGraphic(imageViewDel);
			 btnDel.setOnAction(new EventHandler<ActionEvent>() {
				    @Override
				    public void handle(ActionEvent event) {
				        deleteTask(event);
				    }
				});
			 
			 hbox.getChildren().addAll(label, btnDel);
			 tasksPane.getChildren().addFirst(hbox);
			 Task newTask = new Task(title,TaskState.OPEN);
			 taskServ.saveTask(newTask);
			 taskTotalCounter++;
			 lblStatus.setText("Task " + title + " created");
			 updateDoneOverTotal();
		 }
		 
    }

    @FXML
    void close(ActionEvent event) {
    	primaryStage.close();
    }
    
    void updateDoneOverTotal() {
    	doneOverTotal.setText("" + taskDoneCounter + "/" + taskTotalCounter);
    	if(taskTotalCounter != 0) {
	    	float doneProportion = (float)taskDoneCounter/(float)taskTotalCounter;
	    	if(doneProportion == 1) { 
	    		OngoingTask.setText("Nicely done!!");
	    	} else if(doneProportion > 0.9) {
	    		OngoingTask.setText("You are almost there!");
	    	} else if( doneProportion > 0.5) {
	    		OngoingTask.setText("Good work! Keep it up now");
	    	} else if( doneProportion > 0.2) {
	    		OngoingTask.setText("Let's keep this thing rolling!");
	    	} else if( doneProportion > 0) {
	    		OngoingTask.setText("You can do it!");
	    	} else OngoingTask.setText("Shall we get this started?");
    	} else {
    		OngoingTask.setText("Add your first task");
    	}
    }

    @FXML
    void deleteTask(ActionEvent event) {
       	Button delBtn = (Button) event.getSource();
    	HBox task = (HBox)delBtn.getParent();
    	Label taskLbl = (Label)task.getChildren().get(0);
    	Text taskTxt = (Text) taskLbl.getChildrenUnmodifiable().get(0);
    	if(!taskTxt.isStrikethrough()) {
	    	taskTxt.setStrikethrough(true);
	    	taskTxt.fillProperty().set(Color.GREY);
	    	taskServ.closeTask(taskTxt.getText());
	    	taskDoneCounter++;
	    	updateDoneOverTotal();
    	} else {
    		((VBox)task.getParent()).getChildren().remove(task);
    		taskDoneCounter--;
    		taskTotalCounter--;
    		updateDoneOverTotal();
    		taskServ.deleteTask(taskTxt.getText());
    	}
    }
    
    @FXML
    void minimize(ActionEvent event) {
    	primaryStage.setIconified(true);
    }
}
