package org.fiap.homework.fiap.rebu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.fiap.homework.fiap.rebu.models.Car;
import org.fiap.homework.fiap.rebu.models.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			System.out.println("Bem vindo ao sistema de gerenciamento de veiculos!\n"
					+ "Digite (1) Para cadastrar cliente ou (2) para cadastrar veiculo:");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String choose = reader.readLine();
			System.out.println(choose);
			
			switch(choose) {
			  case "1":
			    AddUser();
			    break;
			  case "2":
			    AddCar();
			    break;
			}
		};
	}

	private void AddCar() {
		Car car = new Car();
		System.out.println("Cadastre um fabricante:");
		try {
		car.setMarca((new BufferedReader(new InputStreamReader(System.in))).readLine());
		System.out.println("Cadastre um modelo:");
		car.setModelo((new BufferedReader(new InputStreamReader(System.in))).readLine());
		System.out.println("Cadastre o ano de fabricacao:");
		car.setAno((new BufferedReader(new InputStreamReader(System.in))).readLine());
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		//To do 
		//Chamar metodo para gravar o usuario
		
	}

	private void AddUser() {
		User user = new User();
		System.out.println("Cadastre um nome:");
		try {
		user.setNome((new BufferedReader(new InputStreamReader(System.in))).readLine());
		System.out.println("Cadastre um profissão:");
		user.setProfissao((new BufferedReader(new InputStreamReader(System.in))).readLine());
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		//To do 
		//Chamar metodo para gravar o usuario
	};

}
