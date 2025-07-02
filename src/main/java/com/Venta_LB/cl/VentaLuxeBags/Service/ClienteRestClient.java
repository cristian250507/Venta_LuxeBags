package com.Venta_LB.cl.VentaLuxeBags.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.Venta_LB.cl.VentaLuxeBags.Model.ClienteDTO;

import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;

@Service
public class ClienteRestClient {

    private final RestTemplate restTemplate;

    
    public ClienteRestClient() {
        this.restTemplate = new RestTemplate();
    }

    
    public List<ClienteDTO> obtenerClientes() {
        String url = "http://localhost:8080/cliente/listar"; 
        ResponseEntity<ClienteDTO[]> response = restTemplate.getForEntity(url, ClienteDTO[].class);
        return Arrays.asList(response.getBody());
    }


    public ClienteDTO obtenerClientePorId(Integer idCliente) {
        String url = "http://localhost:8080/cliente/listar/ID/" + idCliente;
        try {
            return restTemplate.getForObject(url, ClienteDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Cliente no encontrado");
        }
    }
}