package br.edu.ifsc.waterbill.client;

//@author Guilherme Pereira

import java.util.List;
import java.util.Optional;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value="Controller of client related operations.")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@Autowired
	private ClientRepository clientRepository;

	@GetMapping("/client/all")
	@ApiOperation(value = "Get and return all clients.", response = Client.class)
	public List<Client> getAllClients() {
		return clientService.getAllClients();
	}

	@PostMapping("/client/add")
	@ApiOperation(value = "Create a new client register.", response = Client.class)
	public String addClient(@RequestParam String cpf,
							@RequestParam String name) {
		boolean validation = clientService.validateCPF(cpf);
		if (validation) {
			Client newClient = new Client();
			newClient.setCpf(cpf);
			newClient.setName(name);
			clientRepository.save(newClient);
			return "Saved";
		} else {
			return "Invalid Document";
		}
	}

	@PutMapping("/client/update")
	@ApiOperation(value = "Updates information on a existing client.", response = Client.class)
	public String updateClient(@RequestParam String cpf,
							   @RequestParam String name) {
		Client client = clientService.checkClient(cpf);
		client.setCpf(cpf);
		client.setName(name);
		clientRepository.save(client);

		return "client updated";
	}
}
