package com.devsuperior.client.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.devsuperior.client.dto.ClientDTO;
import com.devsuperior.client.entities.Client;
import com.devsuperior.client.repositories.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	
	@Transactional
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
	    Page<Client> page = repository.findAll(pageRequest);
	    return page.map(client -> new ClientDTO(client));
	}
	
	@Transactional
	public Optional<ClientDTO> findById(Long id) {
	    Optional<Client> obj = repository.findById(id);
	    
	    if (!obj.isPresent()) {
	    	return Optional.empty();
	    }
        return Optional.of(new ClientDTO(obj.get()));
	}
	
}
