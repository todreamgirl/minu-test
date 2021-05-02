package com.minutest.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minutest.model.ClienteModel.Client;
import com.minutest.model.ClienteResponse;
import com.minutest.repository.ClienteRepository;

@RestController
@RequestMapping("/api/v1/clients")
public class ClienteController {
	@Autowired
	private ClienteRepository clientRepository;
	
	@GetMapping
	public ResponseEntity<Iterable<Client>> getClients() {
	    var data = clientRepository.findAll();
	    return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@PostMapping(produces="application/json")
	public ResponseEntity<ClienteResponse> createClient(@Validated @RequestBody Client client) {
	    Optional<Client> cpf = clientRepository.findClientByCpf(client.getCpf());
	    Optional<Client> email = clientRepository.findClientByEmail(client.getEmail());
	    ClienteResponse response = new ClienteResponse();
	    if(cpf.isPresent() || email.isPresent()){
	        if (cpf.isPresent() && email.isPresent()) {
	            response.setMessage("E-mail is required and must be unique. " +
	                    "CPF is required and must be unique");
	        } else if(email.isEmpty()) {
	            response.setMessage("CPF is required and must be unique");
	        } else {
	            response.setMessage("E-mail is required and must be unique.");
	        }
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }
	    else{
	        var newClient = ClienteRepository.save(client);
	        response.setClient(newClient);
	        response.setMessage("Success");
	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	    }
	    @PutMapping(path = "/{id}")
	    public ResponseEntity<ClienteResponse> updateClient(@RequestBody Client client, @PathVariable int id) {
	        boolean data = clientRepository.existsById(id);
	        ClienteResponse response = new ClienteResponse();
	        if(data){
	            client.setId(id);
	            ClienteRepository.save(client);
	            response.setClient(client);
	            response.setMessage("Success");
	            return new ResponseEntity<>(response, HttpStatus.OK);
	        }else{
	            response.setMessage("Client id: "+id+" cannot be found");
	            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
	        }
	        @GetMapping(path = "/{id}")
	        public ResponseEntity<ClienteResponse> getClientById(@PathVariable int id) {
	            var client = clientRepository.findById(id);
	            ClienteResponse response = new ClienteResponse();
	            if(client.isPresent()){
	                response.setClient(client.get());
	                response.setMessage("Success");
	                return new ResponseEntity<>(response, HttpStatus.OK);
	            }
	            response.setMessage("Client id: "+id+" cannot be found");
	            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
	        }
	        @DeleteMapping(path = "/{id}")
	        public ResponseEntity<ClienteResponse> deleteClient(@PathVariable int id) {
	            Optional<Client> data = clientRepository.findById(id);
	            ClienteResponse response = new ClienteResponse();
	            if(data.isPresent()){
	                clienteRepository.delete(data.get());
	                response.setMessage("Client deleted");
	                return new ResponseEntity<>(response, HttpStatus.OK);
	            }
	            response.setMessage("Client id: "+id+" cannot be found");
	            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
	        }

}
