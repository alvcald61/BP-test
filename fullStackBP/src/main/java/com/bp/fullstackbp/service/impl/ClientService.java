package com.bp.fullstackbp.service.impl;

import com.bp.fullstackbp.entities.Client;
import com.bp.fullstackbp.exceptions.CustomException;
import com.bp.fullstackbp.repository.ClientRepository;
import com.bp.fullstackbp.request.ClientRequest;
import com.bp.fullstackbp.response.ClientResponse;
import com.bp.fullstackbp.service.IClientService;
import com.bp.fullstackbp.utils.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService implements IClientService {

  Logger logger = LoggerFactory.getLogger(ClientService.class);

  private final ClientRepository clientRepository;

  private final ObjectMapper objectMapper = new ObjectMapper();

  public ClientService(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public List<ClientResponse> getAllClients() {
    try {
      return clientRepository
        .findAllByIsActiveIsTrue()
        .stream()
        .map(client -> objectMapper.map(client, ClientResponse.class))
        .collect(Collectors.toList());
    } catch (Exception e) {
      throw new CustomException("Error al cargar todos los clientes", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ClientResponse getClientById(Long id) {
    Optional<Client> client = clientRepository.findById(id);
    if (client.isPresent()) {
      return objectMapper.map(client.get(), ClientResponse.class);
    }
    throw new CustomException("Cliente no encontrado", HttpStatus.NOT_FOUND);
  }

  @Override
  public ClientResponse createClient(ClientRequest client) {
    try {
      logger.trace("Creando cliente: {}", client);
      System.out.println("Creando cliente: " + client);
      Client newClient = objectMapper.map(client, Client.class);
      newClient.setActive(true);
      newClient.setId(null);
      return objectMapper.map(clientRepository.save(newClient), ClientResponse.class);
    } catch (Exception e) {
      throw new CustomException("Error al crear el cliente", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ClientResponse updateClient(ClientRequest client) {
    Optional<Client> clientToUpdate = clientRepository.findById(client.getId());
    if (clientToUpdate.isPresent()) {
      Client newClient = objectMapper.map(client, Client.class);
      return objectMapper.map(clientRepository.save(newClient), ClientResponse.class);
    }
    throw new CustomException("Cliente no encontrado", HttpStatus.NOT_FOUND);
  }

  @Override
  public boolean deleteClient(Long id) {
    Optional<Client> clientToDelete = clientRepository.findById(id);
    if (clientToDelete.isPresent()) {
      Client client = clientToDelete.get();
      client.setActive(false);
      clientRepository.save(client);
      return true;
    }
    return false;
  }

  @Override
  public ClientResponse getClient(Long id) {
    if (id == null) {
      throw new CustomException("Id de cliente no enviado", HttpStatus.BAD_REQUEST);
    }
    Optional<Client> client = clientRepository.findById(id);
    if (client.isPresent()) {
      return objectMapper.map(client.get(), ClientResponse.class);
    }
    throw new CustomException("Cliente no encontrado", HttpStatus.NOT_FOUND);
  }
}