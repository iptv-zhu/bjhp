package product.prison.nbean;

import java.io.Serializable;

public class Live implements Serializable{
	private String address;

	private int defaultPlay;

	private String hasPower;

	private int id;

	private String name;

	private int position;

	private int rank;

	private int rankId;

	private String targetAgent;

	private String userTypeId;

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}

	public void setDefaultPlay(int defaultPlay) {
		this.defaultPlay = defaultPlay;
	}

	public int getDefaultPlay() {
		return this.defaultPlay;
	}

	public void setHasPower(String hasPower) {
		this.hasPower = hasPower;
	}

	public String getHasPower() {
		return this.hasPower;
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

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getRank() {
		return this.rank;
	}

	public void setRankId(int rankId) {
		this.rankId = rankId;
	}

	public int getRankId() {
		return this.rankId;
	}

	public void setTargetAgent(String targetAgent) {
		this.targetAgent = targetAgent;
	}

	public String getTargetAgent() {
		return this.targetAgent;
	}

	public void setUserTypeId(String userTypeId) {
		this.userTypeId = userTypeId;
	}

	public String getUserTypeId() {
		return this.userTypeId;
	}

}
