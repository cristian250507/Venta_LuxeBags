package com.Venta_LB.cl.VentaLuxeBags.Service;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import com.Venta_LB.cl.VentaLuxeBags.Model.ProductoDTO;

@Service
public class ProductoRestClient {

    private final RestTemplate restTemplate;

    
    public ProductoRestClient() {
        this.restTemplate = new RestTemplate();
    }


    public List<ProductoDTO> obtenerProductos() {
        String url = "http://localhost:8080/Producto/listar"; 
        ResponseEntity<ProductoDTO[]> response = restTemplate.getForEntity(url, ProductoDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public ProductoDTO obtenerProductosID(Integer idProducto) {
        String url = "http://localhost:8080/Producto/listar/" + idProducto;
        try {
            return restTemplate.getForObject(url, ProductoDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Cliente no encontrado");
        }

    }



}
