package it.polito.tdp.yelp.model;



public class LocaleMigliore {
	Business b;
	double delta;
	
	public LocaleMigliore(Business b, double delta) {
		super();
		this.b = b;
		this.delta = delta;
	}

	public Business getB() {
		return b;
	}

	public void setB(Business b) {
		this.b = b;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	@Override
	public String toString() {
		return "LocaleMigliore: " + b.getBusinessName();
	}
	
	
	
	
}