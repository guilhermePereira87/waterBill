package br.edu.ifsc.waterbill.bill;

/*
@author Guilherme Pereira
 */



import java.util.List;

import br.edu.ifsc.waterbill.client.Client;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value="Controller of bill related operations.")
public class BillController {
	@Autowired
	BillService billService;

	@GetMapping("/bills")
	@ApiOperation(value="Get and return all bill.")
	public List<Bill> getAllBills(){
		return billService.getAllBills();
	}
	
	@GetMapping("/bills/bycpf")
	@ApiOperation(value="Get and return all bills from the same client.")
	public List<Bill> getBillsByCpf(@RequestParam String cpf){
		return billService.getBillByCpf(cpf);
	}
}
