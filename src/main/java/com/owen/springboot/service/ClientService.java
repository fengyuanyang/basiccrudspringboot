package com.owen.springboot.service;

import com.owen.springboot.entity.Client;
import com.owen.springboot.respository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    public ClientService(@Autowired ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Iterable<Client> findAll() {
        return clientRepository.findAll();
    }

    public Iterable<Client> save(Iterable<Client> entities) {
        return clientRepository.saveAll(entities);
    }

    public Optional<Client> find(Long id) {
        return clientRepository.findById(id);
    }

    public Client update(Client client) {
        return clientRepository.save(client);
    }

    public void delete(Long id) {
        clientRepository.deleteById(id);
    }
}
