import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class StartGUI extends Application {
	@Override public void start(Stage primaryStage) { // Override the start method in the Application class
		
		primaryStage.setTitle("FILE HOUND - Select Directory"); // Set the stage title
		
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("JavaFX Projects");
		File defaultDirectory = new File("C:/Users");
		chooser.setInitialDirectory(defaultDirectory);
		File selectedDirectory = chooser.showDialog(primaryStage);

		try {
			if (selectedDirectory != null) new MainGUI(selectedDirectory);
			else new MainGUI(defaultDirectory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// Call GUI class
		Application.launch(args);
	}
}
