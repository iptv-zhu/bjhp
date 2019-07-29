package product.prison.nbean;

import java.io.Serializable;

public class Vod2Grid implements Serializable {

	private int fileType;

	public int getFileType() {
		return fileType;
	}


	public void setFileType(int fileType) {
		this.fileType = fileType;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	private String path;

	private int id;


	private String name;


}
