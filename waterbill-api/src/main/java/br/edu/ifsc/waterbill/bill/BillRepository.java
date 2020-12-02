package br.edu.ifsc.waterbill.bill;

/*
@author Guilherme Pereira
 */

import br.edu.ifsc.waterbill.address.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends CrudRepository<Bill, Long> {



}
