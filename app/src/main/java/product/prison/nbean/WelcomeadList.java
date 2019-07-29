package product.prison.nbean;

import java.io.Serializable;

public class WelcomeadList implements Serializable {
	private int adid;

	private String bgMusic;

	private int id;

	private int inter;

	private String name;

	private String path;

	private int position;

	public void setAdid(int adid) {
		this.adid = adid;
	}

	public int getAdid() {
		return this.adid;
	}

	public void setBgMusic(String bgMusic) {
		this.bgMusic = bgMusic;
	}

	public String getBgMusic() {
		return this.bgMusic;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setInter(int inter) {
		this.inter = inter;
	}

	public int getInter() {
		return this.inter;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return this.path;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPosition() {
		return this.position;
	}
}
