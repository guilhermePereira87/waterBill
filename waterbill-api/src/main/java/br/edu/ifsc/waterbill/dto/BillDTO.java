package br.edu.ifsc.waterbill.dto;


//@author Guilherme Pereira

import javax.persistence.Id;

public class BillDTO {
	
	private String name;
	@Id
	private String cpf;
	private int consume;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public int getConsumo() {
		return consume;
	}
	public void setConsume(int consume) {
		this.consume = consume;
	}
	public BillDTO(String name, String cpf, int consume) {
		super();
		this.name = name;
		this.cpf = cpf;
		this.consume = consume;
	}
}
