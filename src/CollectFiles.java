import static java.nio.file.FileVisitResult.*;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CollectFiles extends SimpleFileVisitor<Path> {
	
	String filepath;
	String size;
	String createDate;
	String modDate;
	
	ArrayList<ItemData> fileArray = new ArrayList<ItemData>();
	ArrayList<ItemData> dirArray = new ArrayList<ItemData>();;
	
	
    // Print information about
    // each type of file.
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
		// Create date format
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
        if (attr.isSymbolicLink()) {
        	// THIS CODE DOES NOT CURRENTLY HANDLE SYMLINK FILES
        } else if (attr.isRegularFile()) {
            // Set variables and write to CSV file
            filepath = file.toAbsolutePath().toString();
            size = Long.toString(attr.size());
            createDate = df.format(attr.creationTime().toMillis());
            modDate = df.format(attr.lastModifiedTime().toMillis());
            
            // Write file data to ItemData Object
            fileArray.add(new ItemData(filepath, size, createDate, modDate));
            
        } else {
        	// THIS CODE DOES NOT CURRENTLY HANDLE "OTHER" FILES
        }
    	
    	return CONTINUE;
    }

    // Print each directory visited.
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
    	try {    		
    		// Create date format
    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    		
    		// Create Attributes object to get directory attributes
    		BasicFileAttributes attr = Files.readAttributes(dir, BasicFileAttributes.class);
	        
	        // Set variables and write to CSV file
	        filepath = dir.toAbsolutePath().toString();
	        size = Long.toString(attr.size());
	        createDate = df.format(attr.creationTime().toMillis());
	        modDate = df.format(attr.lastModifiedTime().toMillis());       
	        
	        // Write file data to ItemData Object
            dirArray.add(new ItemData(filepath, size, createDate, modDate));

    	} catch (IOException e) {
    		System.out.println(e);
    	}
        
        return CONTINUE;
    }

    
    // If there is some error accessing
    // the file, let the user know.
    // If you don't override this method
    // and an error occurs, an IOException 
    // is thrown.
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        System.err.println(exc);
        return CONTINUE;
    }

    
    // Method to return File Array
    public ArrayList<ItemData> getFileArray() {
    	return fileArray;
    }
    
    
    // Method to return Directory Array
    public ArrayList<ItemData> getDirArray() {
    	return dirArray;
    }
}
