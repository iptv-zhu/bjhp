package product.prison.nbean;

import java.io.Serializable;

public class RecordData implements Serializable {
	private String admin;

	private String agent;

	private String endTime;

	private int id;

	private String job_name;

	private String likeName;

	private String live;

	private int liveId;

	private String name;

	private String path;

	private int rank;

	private int rankId;

	private int serverId;

	private String startTime;

	private int status;

	private String timeStatus;

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getAdmin() {
		return this.admin;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getAgent() {
		return this.agent;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}

	public String getJob_name() {
		return this.job_name;
	}

	public void setLikeName(String likeName) {
		this.likeName = likeName;
	}

	public String getLikeName() {
		return this.likeName;
	}

	public void setLive(String live) {
		this.live = live;
	}

	public String getLive() {
		return this.live;
	}

	public void setLiveId(int liveId) {
		this.liveId = liveId;
	}

	public int getLiveId() {
		return this.liveId;
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

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public int getServerId() {
		return this.serverId;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return this.status;
	}

	public void setTimeStatus(String timeStatus) {
		this.timeStatus = timeStatus;
	}

	public String getTimeStatus() {
		return this.timeStatus;
	}
}
