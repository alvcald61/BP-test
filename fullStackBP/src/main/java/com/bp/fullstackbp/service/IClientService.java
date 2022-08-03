package com.bp.fullstackbp.service;

import com.bp.fullstackbp.request.ClientRequest;
import com.bp.fullstackbp.response.ClientResponse;

import java.util.List;

public interface IClientService {
  List<ClientResponse> getAllClients();

  ClientResponse getClientById(Long id);

  ClientResponse createClient(ClientRequest client);

  ClientResponse updateClient(ClientRequest client);

  boolean deleteClient(Long id);
}
