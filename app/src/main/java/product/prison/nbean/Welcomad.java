package product.prison.nbean;

import java.io.Serializable;
import java.util.List;

public class Welcomad implements Serializable {
	private List<WelcomeadList> addeList;

	private int category;

	private int contentType;

	private int id;

	private String name;

	private int position;

	public void setAddeList(List<WelcomeadList> addeList) {
		this.addeList = addeList;
	}

	public List<WelcomeadList> getAddeList() {
		return this.addeList;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getCategory() {
		return this.category;
	}

	public void setContentType(int contentType) {
		this.contentType = contentType;
	}

	public int getContentType() {
		return this.contentType;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPosition() {
		return this.position;
	}
}
