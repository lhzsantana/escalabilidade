package br.com.escalabilidade.entity;

public class Bill {

	private String biller;
	private double price;

	public String getBiller() {
		return biller;
	}

	public void setBiller(String biller) {
		this.biller = biller;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
