package com.owen.springboot.controller;

import com.owen.springboot.entity.Client;
import com.owen.springboot.service.ClientService;
import com.owen.springboot.util.WebUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(value = "/client")
public class ClientController {
    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping({"/"})
    @PreAuthorize("hasRole('OPERATOR') or hasRole('MANAGER') or hasRole('ADMIN')")
    @ApiOperation(value = "get All Clients", nickname = "getClients", notes = "Return Clients", response = ResponseEntity.class, tags={ "client", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = ResponseEntity.class)})
    public ResponseEntity<Iterable<Client>> getClients(){
        return ResponseEntity.ok().body(clientService.findAll());
    }

    @GetMapping({"/{id}"})
    @PreAuthorize("hasRole('OPERATOR') or hasRole('MANAGER') or hasRole('ADMIN')")
    @ApiOperation(value = "get Client detail", nickname = "getClient", notes = "get client detail by client id", response = ResponseEntity.class, tags={ "client", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = ResponseEntity.class)})
    public ResponseEntity<Optional<Client>> getClient(@PathVariable Long id){
        return ResponseEntity.ok().body(clientService.find(id));
    }

    @DeleteMapping({"/{id}"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @ApiOperation(value = "delete Client", nickname = "deleteClient", notes = "delete client by client id", response = ResponseEntity.class, tags={ "client", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = ResponseEntity.class)})
    public ResponseEntity deleteClient(@PathVariable Long id){
        return ResponseEntity.ok().build();
    }

    @PutMapping({"/{id}"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @ApiOperation(value = "update Company", nickname = "updateCompany", notes = "update client detail by client id", response = ResponseEntity.class, tags={ "client", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = ResponseEntity.class)})
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @ApiParam(value = "client detail to update to",required=true) @RequestBody Client updateClient){
        Optional<Client> c = clientService.find(id);
        Client client = c.get();
        String userName = WebUtils.getUserName();

        client.setUpdatedAt(new Date());
        client.setUpdatedBy(userName);
        client.setName(updateClient.getName() == null?client.getName():updateClient.getName());
        client.setEmail(updateClient.getEmail() == null? client.getEmail():updateClient.getEmail());
        client.setPhone(updateClient.getPhone() == null? client.getPhone(): updateClient.getPhone());
        clientService.update(client);
        return ResponseEntity.ok().body(client);
    }

    @PostMapping({"/"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
    @ApiOperation(value = "add Clients", nickname = "addClients", notes = "add multi clients at once", response = ResponseEntity.class, tags={ "client", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = ResponseEntity.class)})
    public ResponseEntity<Iterable<Client>> addClients(@RequestBody Iterable<Client> clientList){
        String userName = WebUtils.getUserName();

        for (Client client:clientList) {
            client.setCreatedAt(new Date());
            client.setUpdatedAt(new Date());
            client.setCreatedBy(userName);
            client.setUpdatedBy(userName);
        }
        clientService.save(clientList);
        return ResponseEntity.ok().body(clientList);
    }
    
}
