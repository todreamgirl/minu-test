package com.minutest.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.minutest.model.ClienteModel;

import java.util.Optional;

public interface ClienteRepository extends CrudRepository<ClienteModel, Integer> {
	 @Query("select c from Client c where c.cpf like ?1")
	    Optional<ClienteModel> findClientByCpf(String cpf);

	    @Query("select c from Client c where c.email like ?1")
	    Optional<ClienteModel> findClientByEmail(String email);

}
 