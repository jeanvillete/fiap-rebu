package org.fiap.homework.fiap.rebu.models;

public class Car {

	private String marca;
    private String modelo;
    private String ano;
    
    //Contructors  
	public Car(String marca, String modelo, String ano) {
		this.marca = marca;
		this.modelo = modelo;
		this.ano = ano;
	}
	
	public Car() {
	}
	
	//Getters and Setters
	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}
}
