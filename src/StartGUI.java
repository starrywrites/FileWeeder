import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class StartGUI extends Application {
	@Override public void start(Stage primaryStage) { // Override the start method in the Application class
		
		// Create Directory chooser object and 
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("FILE WEEDER: File Selection");
		
		// Opens chooser with base diectory
		File defaultDirectory = new File("C:/Users");
		chooser.setInitialDirectory(defaultDirectory);
		
		// Show Directory choices and allow user to select one
		File selectedDirectory = chooser.showDialog(primaryStage);

		try {
			// If directory selected, enter main class
			if (selectedDirectory != null) new MainGUI(selectedDirectory);
			// If no directory selected, close program
			else {
				System.out.println("Nothing selected. Program closing.");
				System.exit(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// Call GUI class
		Application.launch(args);
	}
}
