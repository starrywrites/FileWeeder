import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class MainGUI {
	
	Path rootPath;  // Root filepath to search chosen by user
	ArrayList<ItemData> fileArr;  // Array of files and their metadata
	ArrayList<ItemData> dirArr;  // Array of directories and their metadata
	TableView<ItemData> myTableView;  // The TableView that will display
	boolean tableIsFiles;  // Used to tell whether TableView is currently showing files or directories
	
	MainGUI(File f) throws IOException {
		
		// Path is established in StartGUI class
		rootPath = f.toPath();
		
		// Create CollectFiles object (see CollectFiles.java)
		// Source: https://docs.oracle.com/javase/tutorial/essential/io/walk.html
		CollectFiles collect = new CollectFiles();
		
		// Get all directories and files in provided path
		// This will call the method to set the table
		Files.walkFileTree(rootPath, collect);
		
		// Get arraylists of current files and directories
		fileArr = collect.getFileArray();
		dirArr = collect.getDirArray();		

		// Create new stage
		Stage subStage = new Stage();
		subStage.setTitle("File Hound");
		
		// Create a border pane
		BorderPane pane = new BorderPane();
		
		// Create table object and view indicator
		myTableView = new TableView<ItemData>();
		tableIsFiles = true;
		
		//Place nodes in the pane
		pane.setTop(getHBox());
		pane.setBottom(getVBox());
	
		// Add pane to scene and adjust default size
	    Scene scene = new Scene(pane, 915, 400);
		
		// Display window
		subStage.setScene(scene);
		subStage.show();
		
		// Action on close
		subStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	              System.out.println("Stage closed.");
	              subStage.close();
	              System.exit(0);
	          }
	      });
	}
	
	
	private HBox getHBox() {
		
		HBox hBox = new HBox(15);
		hBox.setPadding(new Insets(15, 15, 15, 15));
		hBox.setStyle("-fx-background-color: gold");
		
		Button viewFiles = new Button("View Files");
		Button viewDirectories = new Button("View Directories");
		Button delete = new Button("Delete Selection");
		
		hBox.getChildren().add(viewFiles);
		hBox.getChildren().add(viewDirectories);
		hBox.getChildren().add(delete);

        viewFiles.setOnAction(eve-> loadFilesToTable());
        viewDirectories.setOnAction(eve-> loadDirsToTable());
        delete.setOnAction(eve-> {
    		ItemData row = myTableView.getSelectionModel().getSelectedItem(); // Get selected row in TableView
    		if (row != null) {
    	   		if (tableIsFiles) {
        			new ConfirmAndDelete(myTableView, row, fileArr);
        			loadFilesToTable();
        		}
        		else {
        			new ConfirmAndDelete(myTableView, row, dirArr);
        			loadDirsToTable();
        		}
    		}
    		else {
    			System.out.println("Nothing selected.");
    		}
        });
		
		return hBox;
	}
	
	private VBox getVBox() {
	    TableColumn<ItemData, ?> nameCol = new TableColumn<>("Name");
	    TableColumn<ItemData, ?> sizeCol = new TableColumn<>("Size");
	    TableColumn<ItemData, ?> createCol = new TableColumn<>("Created");
	    TableColumn<ItemData, ?> modCol = new TableColumn<>("Last Mod");

	    nameCol.setCellValueFactory(new PropertyValueFactory<>("path"));  // these values must be the same as in the ItemData class
	    sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
	    createCol.setCellValueFactory(new PropertyValueFactory<>("created"));
	    modCol.setCellValueFactory(new PropertyValueFactory<>("modded"));
	    
	    myTableView.getColumns().add(nameCol);
	    myTableView.getColumns().add(sizeCol);
	    myTableView.getColumns().add(createCol);
	    myTableView.getColumns().add(modCol);
		
		// Fill table with files
		loadFilesToTable();
	    
	    TableViewSelectionModel<ItemData> selectionModel = myTableView.getSelectionModel();
	    selectionModel.setSelectionMode(SelectionMode.SINGLE);

	    VBox vbox = new VBox(myTableView);
		
		return vbox;
	}
	
	
	private void loadFilesToTable() {
		//Clear current table
		myTableView.getItems().clear();
		
		for (ItemData i: fileArr) {
			myTableView.getItems().add(i);
		}
		tableIsFiles = true;
	}
	
	
	private void loadDirsToTable() {
		//Clear current table
		myTableView.getItems().clear();
		
		for (ItemData i: dirArr) {
			myTableView.getItems().add(i);
		}
		tableIsFiles = false;
	}
}