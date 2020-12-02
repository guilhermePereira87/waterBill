package br.edu.ifsc.waterbill.bill;

/*
@author Guilherme Pereira
 */

import java.time.LocalDate;
import java.util.*;

import br.edu.ifsc.waterbill.client.ClientService;
import br.edu.ifsc.waterbill.dto.BillDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsc.waterbill.client.Client;

@Service
public class BillService {
	@Autowired
	private BillRepository billRepository;

	@Autowired
	private ClientService clientService;

	public List<Bill> getAllBills(){
		List<Bill> bills = new ArrayList<>();
		billRepository.findAll().forEach(bills::add);
		return bills;
	}

	public Optional<Bill> getBill(Long id) {
		return billRepository.findById(id);
	}

	public void addBill (Bill bill) {
		billRepository.save(bill);
	}

	public List<Bill> getBillByCpf(String cpf) {
		List<Bill> byCpf = new ArrayList<>();

		List<Bill> bills = new ArrayList<>();
		billRepository.findAll().forEach(bills::add);

		Client client = clientService.checkClient(cpf);

		for(Bill bill : bills) {
			if(bill.getClient().equals(client)) {
				byCpf.add(bill);
			}
		}
		return byCpf;
	}

	public void deleteBillByCpf(String cpf) {
		List<Bill> bills = new ArrayList<>();
		billRepository.findAll().forEach(bills::add);

		Client client = clientService.checkClient(cpf);

		for(Bill bill : bills) {
			if(bill.getClient().equals(client)) {
				billRepository.delete(bill);
			}
		}
	}

	public BillDTO createSummary(String cpf){
		{
			Client client = clientService.checkClient(cpf);
			List<Bill>byCpf = getBillByCpf(cpf);
			Collections.sort(byCpf, (a, b) -> Integer.valueOf(b.getDate().compareTo(a.getDate())));
			int reading = byCpf.get(0).getReading() - byCpf.get(1).getReading();
			BillDTO billDTO = new BillDTO(client.getName(), cpf, reading);
			return billDTO;
		}
	}

	public String  updateBill (Long id, String date, int reading) {
		LocalDate parsedDate = stringToLocalDate(date);
		try{Optional<Bill> checkBill = billRepository.findById(id);
		if(!checkBill.isPresent() || !parsedDate.getMonth().equals(LocalDate.now().getMonth()))
			throw new IllegalArgumentException();
		Bill bill = checkBill.get();
		bill.setReading(reading);
		billRepository.save(bill);
		return "Bill Updated";}catch (IllegalArgumentException exception){
			System.out.println("Invalid or inaccessible bill.");
			return "Invalid or inaccessible bill";
		}
	}

	public String createBill(Long id, String cpf, String date, int reading) {
			Client client = clientService.checkClient(cpf);
			Bill bill = new Bill();
			bill.setId(id);
			bill.setClient(client);
			bill.setDate(date);
			bill.setReading(reading);
			billRepository.save(bill);
			return "Bill Created";
		}

		public LocalDate stringToLocalDate(String date){
			LocalDate x = LocalDate.parse(date);
			return x;
		}
}
