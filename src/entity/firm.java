package entity;

public class firm {
	private double PEratio;
	private double returnOnAssets;
	private double PBRatio;
	public firm(double PEratio,double returnOnAssets,double pbratio){
		this.PEratio=PEratio;
		this.PBRatio = pbratio;
		this.returnOnAssets = returnOnAssets;
	}
	public double getPEratio() {
		return PEratio;
	}
	public void setPEratio(double pEratio) {
		PEratio = pEratio;
	}
	public double getReturnOnAssets() {
		return returnOnAssets;
	}
	public void setReturnOnAssets(double returnOnAssets) {
		this.returnOnAssets = returnOnAssets;
	}
	public double getPBRatio() {
		return PBRatio;
	}
	public void setPBRatio(double pBRatio) {
		PBRatio = pBRatio;
	}

}
