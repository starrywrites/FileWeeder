import java.io.File;
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
		btnBox.setAlignment(Pos.CENTER);		
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
	
	ConfirmAndDelete(TableView<ItemData> tv, ItemData row, ArrayList<ItemData> arr) {
		Stage popStage = new Stage();
		popStage.setTitle("Deletion Confirmation");
		
		// Create a border pane
		BorderPane popPane = new BorderPane();
		
		// Get id and path to be deleted
		String pathString = row.getPath();
		
		// Create box and message
		VBox messageBox = new VBox();
		messageBox.setAlignment(Pos.CENTER);
		messageBox.getChildren().add(new Text("Are you sure you want to delete this file?"));
		messageBox.getChildren().add(new Text(pathString));
		
		// Create box and message
		HBox btnBox = new HBox();
		btnBox.setAlignment(Pos.CENTER);		
		Button btnYes = new Button("Yes");
		Button btnNo = new Button("No");
		btnBox.getChildren().add(btnYes);
		btnBox.getChildren().add(btnNo);
		btnYes.setOnAction(eve-> {
			deleteFile(tv, row, arr, pathString);
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
	
	private void deleteFile (TableView<ItemData> tv, ItemData row, ArrayList<ItemData> arr, String s) {
  		// DELETE DATA FILES! BE CAREFUL!!!!! O.O
  		try {         
  			File del = new File(s);  //file to be delete 
  			
  			if (!del.exists()) new ConfirmAndDelete("File or Folder does not exist.");
  			else if(del.delete()) {
  				new ConfirmAndDelete("File or Folder was deleted.");  //getting and printing the file name
  				tv.getItems().remove(row); // Remove row from the TableView
  				arr.remove(arr.indexOf(row));
  			}  
  			else new ConfirmAndDelete("File or Folder exists but is unable to be deleted.");
  			
  		} catch(Exception e) {  
  		e.printStackTrace();  
  		}	
	}
	
	
}