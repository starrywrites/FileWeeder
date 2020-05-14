import java.io.File;

public class ActualDeleteFile {
	
	ActualDeleteFile (String s) {
  		// DELETE DATA FILES! BE CAREFUL!!!!! O.O
  		try {         
  			File del = new File(s);  //file to be delete 
  			
  			if(del.delete()) System.out.println(del.getName() + " deleted!");  //getting and printing the file name  
  			else System.out.println("Failed to delete!");  
  			
  		} catch(Exception e) {  
  		e.printStackTrace();  
  		}	
	}
}
