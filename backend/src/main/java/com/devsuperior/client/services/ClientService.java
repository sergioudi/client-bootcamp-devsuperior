package com.devsuperior.client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.client.entities.Client;
import com.devsuperior.client.repositories.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	
	public Page<Client> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
}
