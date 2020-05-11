package org.fiap.homework.fiap.rebu.models;

public class User {
       
	private String nome;
    private String profissao;
    
    //Contructors
	public User(String nome, String profissao) {
		this.nome = nome;
		this.profissao = profissao;
	}
    
    public User() {}
    
    //Getters and Setters    
    public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

}
