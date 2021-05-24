package com.devsuperior.client.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.devsuperior.client.dto.ClientDTO;
import com.devsuperior.client.entities.Client;
import com.devsuperior.client.repositories.ClientRepository;
import com.devsuperior.client.services.exceptions.DatabaseException;
import com.devsuperior.client.services.exceptions.ResourceNotFoundException;

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
	public ClientDTO findById(Long id) {
	    Optional<Client> obj = repository.findById(id);
        Client client = obj.orElseThrow(() -> new ResourceNotFoundException("Client not found! id:"+id));
        return new ClientDTO(client);
	}
	
	@Transactional
	public ClientDTO insert(ClientDTO clientDTO) {
		Client client = new Client();
		copyClientDtoToClient(clientDTO,client);
		client = repository.save(client);
	    return new ClientDTO(client);
	}

	@Transactional
	public ClientDTO update(Long id, ClientDTO clientDTO) {
		try {
			Client client = repository.getOne(id);
			copyClientDtoToClient(clientDTO,client);
			client = repository.save(client);
		    return new ClientDTO(client);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Client not found! id:"+id);
		}

	}
	

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Client not found! id:"+id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation!");
		}
	}	
	
	private void copyClientDtoToClient(ClientDTO clientDTO, Client client) {
		client.setBirthDate(clientDTO.getBirthDate());
		client.setChildren(clientDTO.getChildren());
		client.setCpf(clientDTO.getCpf());
		client.setIncome(clientDTO.getIncome());
		client.setName(clientDTO.getName());
	}
}
