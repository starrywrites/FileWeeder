import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ConfirmAndDelete {
	
	private Path rootPath;
	private ArrayList<ItemData> fileArr;
	private ArrayList<ItemData> dirArr;
	private String pathToDel;
	
	ConfirmAndDelete(String s) {
		Stage popStage = new Stage();
		popStage.setTitle("Deletion Confirmation");
		
		// Create a border pane
		BorderPane popPane = new BorderPane();
		
		// Create box and message
		HBox messageBox = new HBox();
		messageBox.setAlignment(Pos.CENTER);
		messageBox.getChildren().add(new Text(s));
		
		// Create box and message
		HBox btnBox = new HBox();
		btnBox.setAlignment(Pos.TOP_CENTER);		
		Button btn = new Button("Ok");
		btnBox.getChildren().add(btn);
		btn.setOnAction(eve-> popStage.close());
		
		popPane.setTop(messageBox);
		popPane.setBottom(btnBox);
		
		Scene scene = new Scene(popPane, 400, 200);
		
		popStage.setScene(scene);
		popStage.show();
		
		// Action on close
		popStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	        	  popStage.close();
	          }
	      });
	}
	
	ConfirmAndDelete(Path rootPath, TableView<ItemData> tv, ItemData row, ArrayList<ItemData> fileArr, ArrayList<ItemData> dirArr) {
		// Create a border pane
		BorderPane popPane = new BorderPane();
		
		// Set class variables
		this.rootPath = rootPath;
		this.pathToDel = row.getPath();
		this.dirArr = dirArr;
		this.fileArr = fileArr;
		
		// Create stage
		Stage popStage = new Stage();
		popStage.setTitle("Deletion Confirmation");

		// Create box and message
		VBox messageBox = new VBox();
		messageBox.setAlignment(Pos.CENTER);
		messageBox.getChildren().add(new Text("Are you sure you want to delete this file?"));
		messageBox.getChildren().add(new Text(pathToDel));
		
		// Create box and message
		HBox btnBox = new HBox();
		btnBox.setAlignment(Pos.TOP_CENTER);		
		Button btnYes = new Button("Yes");
		Button btnNo = new Button("No");
		btnBox.getChildren().add(btnYes);
		btnBox.getChildren().add(btnNo);
		btnYes.setOnAction(eve-> {
			deleteFile();
			popStage.close();
		});
		btnNo.setOnAction(eve-> {
			popStage.close();
		});
		
		popPane.setTop(messageBox);
		popPane.setBottom(btnBox);
		
		Scene scene = new Scene(popPane, 400, 200);
		
		popStage.setScene(scene);
		popStage.show();
		
		// Action on close
		popStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	        	  popStage.close();
	          }
	      });
	}
	
	private void deleteFile () {
  		// DELETE DATA FILES! BE CAREFUL!!!!! O.O
  		try {         
  			File del = new File(pathToDel);  // File to be delete 
  			
  			if (!del.exists()) new ConfirmAndDelete("File or Folder does not exist.");
  			else if(del.delete()) {
  				new ConfirmAndDelete("File/Folder was deleted.");  //getting and printing the file name
  				refreshArrays();
  			}  
  			else new ConfirmAndDelete("File/Folder exists but is unable to be deleted.");
  			
  		} catch(Exception e) {  
  		e.printStackTrace();  
  		}	
	}
	
	private void refreshArrays() throws IOException {
		// Create new CollectFiles object
		CollectFiles newCollect = new CollectFiles();
		
		// Get all directories and files in provided path
		Files.walkFileTree(rootPath, newCollect);
		
		// Get arraylists of current files and directories
		ArrayList<ItemData> newFileArr = newCollect.getFileArray();
		ArrayList<ItemData> newDirArr = newCollect.getDirArray();
		
		// Clear old lists
		fileArr.clear();
		dirArr.clear();
		
		// Set old arrays to new
		fileArr.addAll(newFileArr);
		dirArr.addAll(newDirArr);
	}
	
	
}