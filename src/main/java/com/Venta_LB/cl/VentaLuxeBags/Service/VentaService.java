package com.Venta_LB.cl.VentaLuxeBags.Service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.Venta_LB.cl.VentaLuxeBags.Model.ClienteDTO;
import com.Venta_LB.cl.VentaLuxeBags.Model.Detalle_Venta;
import com.Venta_LB.cl.VentaLuxeBags.Model.ProductoDTO;
import com.Venta_LB.cl.VentaLuxeBags.Model.Venta;

import com.Venta_LB.cl.VentaLuxeBags.Repository.VentaRepository;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class VentaService {

    @Autowired
    private VentaRepository ventarepository;

    private final RestTemplate restTemplate = new RestTemplate();

    
    public ClienteDTO obtenerCliente(Integer idCliente) {
        String url = "http://localhost:8080/cliente/listar/ID/" + idCliente;
        System.out.println("Consultando cliente en URL: " + url);
        try {
            return restTemplate.getForObject(url, ClienteDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            System.out.println("Cliente con ID " + idCliente + " no encontrado");
            throw new RuntimeException("Cliente no encontrado");
        }
    }


    public ProductoDTO obtenerProducto(Integer idProducto) {
        String url = "http://localhost:8080/Producto/listar/" + idProducto; 
        System.out.println("Consultando producto en URL: " + url);
        try {

            return restTemplate.getForObject(url, ProductoDTO.class);
        } catch (HttpClientErrorException.NotFound e) {

            System.out.println("Producto con ID " + idProducto + " no encontrado");
            throw new RuntimeException("Producto no encontrado");
        }
    }


    public String realizarVenta(Venta venta) {
        try {
            ClienteDTO cliente = obtenerCliente(venta.getIdCliente());
        } catch (RuntimeException e) {
            return "Cliente con ID " + venta.getIdCliente() + " no existe";
        }

        try {
            ProductoDTO producto = obtenerProducto(venta.getIdProducto());
        } catch (RuntimeException e) {
            return "Producto con ID " + venta.getIdProducto() + " no existe";
        }

        if (ventarepository.existsByDetalleventaId(venta.getDetalleventa().getId())) {
            return "Ese ID de Detalle de Venta ya está asociado a otra venta.";
        }

        if (ventarepository.existsByIdCliente(venta.getIdCliente())) {
            return "Ya existe una venta registrada con el cliente ID: " + venta.getIdCliente();
        }

        if (ventarepository.existsByIdProducto(venta.getIdProducto())) {
            return "Ya existe una venta registrada con el producto ID: " + venta.getIdProducto();
        }
        ventarepository.save(venta);
        return "La venta con el ID: " + venta.getId() + " fue agregada";
    }


    public List<Venta> listarVentas() {
        return ventarepository.findAll();
    }


 
    public String guardarVentas(List<Venta> ventas) {
        ventarepository.saveAll(ventas);
        return "Hay: " + ventas.size() + " Ventas guardadas con éxito";
    }


    public String eliminarVenta(Integer Idventa) {
        ventarepository.deleteById(Idventa);
        return "Se elimino la venta con el ID: " + Idventa;
    }


    public Venta buscarPoriD(Integer id) {
        return ventarepository.findById(id).orElse(null);
    }


    public String actualizarVenta(Venta venta) {
        ventarepository.save(venta);
        return "Venta con ID: " + venta.getId() + " actualizada";
    }
}

