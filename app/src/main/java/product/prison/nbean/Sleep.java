package product.prison.nbean;

public class Sleep {
//	private String onTime;

	private String offTime;
	private String rebootTime;

	public String getRebootTime() {
		return rebootTime;
	}

	public void setRebootTime(String rebootTime) {
		this.rebootTime = rebootTime;
	}

	//
	// public void setOnTime(String onTime) {
	// this.onTime = onTime;
	// }
	//
	// public String getOnTime() {
	// return this.onTime;
	// }

	public void setOffTime(String offTime) {
		this.offTime = offTime;
	}

	public String getOffTime() {
		return this.offTime;
	}

}
