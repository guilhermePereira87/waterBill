package br.edu.ifsc.waterbill.client;

//@author Guilherme Pereira


import br.com.caelum.stella.bean.validation.CPF;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="client")
public class Client {
	
	@Id
	@CPF
	private String cpf;
	
	@Column(name="name")
	private String name;
	
	
	public Client() {}
	

	public Client(String cpf, String name) {
		super();
		this.cpf = cpf;
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
