package it.polito.tdp.yelp.model;

public class Adiacenza {
	
	public Adiacenza(Business v1, Business v2, double peso) {
		super();
		this.v1 = v1;
		this.v2 = v2;
		this.peso = peso;
	}
	private Business v1;
	private Business v2;
	private double peso;
	public Business getV1() {
		return v1;
	}
	public void setV1(Business v1) {
		this.v1 = v1;
	}
	public Business getV2() {
		return v2;
	}
	public void setV2(Business v2) {
		this.v2 = v2;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	

}
