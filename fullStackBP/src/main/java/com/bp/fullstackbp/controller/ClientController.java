package com.bp.fullstackbp.controller;

import com.bp.fullstackbp.exceptions.CustomException;
import com.bp.fullstackbp.request.ClientRequest;
import com.bp.fullstackbp.response.ClientResponse;
import com.bp.fullstackbp.response.StandardResponse;
import com.bp.fullstackbp.service.impl.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClientController {
  private final ClientService clientService;

  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @GetMapping("/listarTodos")
  public StandardResponse<List<ClientResponse>> getAllClients() {
    try {
      return new StandardResponse<>(clientService.getAllClients());
    } catch (CustomException e) {
      return new StandardResponse<>(e);
    }
  }

  @PutMapping("/actualizar")
  public StandardResponse<ClientResponse> updateClient(@RequestBody ClientRequest clientRequest) {
    try {
      return new StandardResponse<>(clientService.updateClient(clientRequest));
    } catch (CustomException e) {
      return new StandardResponse<>(e);
    }
  }

  @PostMapping("/guardar")
  public StandardResponse<ClientResponse> createClient(@RequestBody ClientRequest clientRequest) {
    try {
      return new StandardResponse<>(clientService.createClient(clientRequest));
    } catch (CustomException e) {
      return new StandardResponse<>(e);
    }
  }

  @DeleteMapping("/eliminar/{id}")
  public StandardResponse<Boolean> deleteClient(@PathVariable Long id) {
    try {
      return new StandardResponse<>(clientService.deleteClient(id));
    } catch (CustomException e) {
      return new StandardResponse<>(e);
    }
  }

  @GetMapping("/buscar/{id}")
  public StandardResponse<ClientResponse> getClient(@PathVariable Long id) {
    try {
      return new StandardResponse<>(clientService.getClient(id));
    } catch (CustomException e) {
      return new StandardResponse<>(e);
    }
  }
}
