
public class ItemData {
	// Declare variables
	String path;
	String size;
	String created;
	String modded;
	
	ItemData () {
	}
	
	ItemData (String path, String size, String created, String modded) {
		this.path = path;
		this.size = size;
		this.created = created;
		this.modded = modded;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setSize(String size) {
		this.size = size;
	}
	
	public String getSize() {
		return size;
	}
	
	public void setCreated(String created) {
		this.created = created;
	}
	
	public String getCreated() {
		return created;
	}
	
	public void setModded(String modded) {
		this.modded = modded;
	}
	
	public String getModded() {
		return modded;
	}

    @Override
    public String toString() {
        return path + "," + size + "," + created + "," + modded + "\n";
    }
}
