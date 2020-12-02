package br.edu.ifsc.waterbill;

/*
@author Guilherme Pereira
 */

import br.edu.ifsc.waterbill.address.Address;
import br.edu.ifsc.waterbill.address.AddressService;
import br.edu.ifsc.waterbill.bill.BillService;
import br.edu.ifsc.waterbill.client.Client;
import br.edu.ifsc.waterbill.client.ClientService;
import br.edu.ifsc.waterbill.dto.BillDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Api(value="Main controller that intersects client and bill services.")
public class MainController {

	@Autowired
	private ClientService clientService;

	@Autowired
	private BillService billService;

	@Autowired
	private AddressService addressService;

	@PostMapping("/bill/add")
	@ApiOperation(value="Add a new bill reading.")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Bill Created"),
			@ApiResponse(code = 404, message = "Client not found")
	})
	public @ResponseBody String createBill (
			@RequestParam Long id,
			@RequestParam String cpf,
			@RequestParam String date,
			@RequestParam int reading) {
		return billService.createBill(id, cpf, date, reading);
	}

	@GetMapping("bill/summary")
	@ApiOperation(value="Create a new bill summary.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Summary Created"),
			@ApiResponse(code = 404, message = "Client not found")
	})
	public @ResponseBody BillDTO createSummary(
			@RequestParam String cpf) {
		return billService.createSummary(cpf);
	}
	
	@DeleteMapping("client/remove/")
	@ApiOperation(value="Remove a client and the respective bills.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Client removed"),
			@ApiResponse(code = 404, message = "Client not found")
	})
	public String removeClient(@RequestParam String cpf) {
		billService.deleteBillByCpf(cpf);
		clientService.deleteClient(cpf);
		return "client deleted";
	}
	
	@PutMapping("bill/update")
	@ApiOperation(value="Update a bill information. Only works for bills of the current month.", response = Client.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Bill Updated"),
			@ApiResponse(code = 401, message = "Unauthorized date"),
			@ApiResponse(code = 404, message = "Client not found")
	})
	@ResponseBody String updateBill (
			@RequestParam Long id,
			@RequestParam String date,
			@RequestParam int reading) {
		return billService.updateBill(id, date, reading);

	}

	@PostMapping("client/new")
	@ApiOperation(value="Create new client and Address")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Client created"),
			@ApiResponse(code = 404, message = "Client not found")
	})
	public String createClient (
			@RequestParam int id,
			@RequestParam String cpf,
			@RequestParam String name,
			@RequestParam int cep,
			@RequestParam int number,
			@RequestParam String details) throws JSONException, IOException {

		if(!clientService.validateCPF(cpf)) return "Invalid document";
		Client client = new Client(cpf, name);
		JSONObject addressJson = addressService.createAddress(cep);
		Address address = new Address();
		address.setCep(cep);
		address.setStreet((String) addressJson.get("logradouro"));
		address.setCity((String) addressJson.get("localidade"));
		address.setClient(client);
		address.setNeighborhood((String) addressJson.get("bairro"));
		address.setState((String) addressJson.get("uf"));
		address.setNumber(number);
		address.setDetails(details);
		address.setId(id);
		clientService.addClient(client);
		addressService.saveAddress(address);
		return "Saved";
	}

	@PostMapping("client/add_propriety")
	@ApiOperation("Add new propriety to existing client")
	public String addPropriety(@RequestParam String cpf,
							   @RequestParam int id,
							   @RequestParam int cep,
							   @RequestParam int number,
							   @RequestParam String details) throws IOException, JSONException {

		Client client = clientService.checkClient(cpf);
		JSONObject addressJson = addressService.createAddress(cep);
		Address address = new Address();
		address.setCep(cep);
		address.setStreet((String) addressJson.get("logradouro"));
		address.setCity((String) addressJson.get("localidade"));
		address.setClient(client);
		address.setNeighborhood((String) addressJson.get("bairro"));
		address.setState((String) addressJson.get("uf"));
		address.setNumber(number);
		address.setDetails(details);
		address.setId(id);
		clientService.addClient(client);
		addressService.saveAddress(address);
		return "Saved";
	}
}
