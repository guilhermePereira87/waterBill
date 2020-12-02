package br.edu.ifsc.waterbill.bill;

/*
@author Guilherme Pereira
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.edu.ifsc.waterbill.client.Client;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name="bill")
public class Bill {
	
	@Id
	private Long id;

	@ManyToOne
	@JoinColumn(name="cpf")
	private Client client;
	
	@Column(name="date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String date;
	
	@Column(name="reading")
	private int reading;

	public Bill() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getReading() {
		return reading;
	}

	public void setReading(int reading) {
		this.reading = reading;
	}
	
	
	

}
