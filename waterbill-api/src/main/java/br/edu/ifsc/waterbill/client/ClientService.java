package br.edu.ifsc.waterbill.client;

//@author Guilherme Pereira

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.caelum.stella.tinytype.CPF;
import br.com.caelum.stella.validation.CPFValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	public List<Client> getAllClients(){
		List<Client> clients = new ArrayList<>();
		clientRepository.findAll().forEach(clients::add);
		return clients;
	}
	
	public Optional<Client> getClient(String id) {
		return clientRepository.findById(id);
	}
	
	public void addClient (Client client) { clientRepository.save(client);
	}
	
	public void updateClient(Client client) {
		clientRepository.save(client);
	}
	
	public void deleteClient(String cpf) {
		clientRepository.deleteById(cpf);
	}

	public Client checkClient(String cpf) {
		try {
			Optional<Client> checkClient = clientRepository.findById(cpf);

			if (!checkClient.isPresent()) {
				throw new IllegalArgumentException();
			}
			Client client = checkClient.get();
			return client;
		}catch (IllegalArgumentException exception){
			System.out.println("Client not Found");
		}
		return null;
	}

	public boolean validateCPF(String cpf) {
		CPFValidator cpfValidator = new CPFValidator();
		try {
			cpfValidator.assertValid(cpf);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
