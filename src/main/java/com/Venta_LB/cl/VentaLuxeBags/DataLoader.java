package com.Venta_LB.cl.VentaLuxeBags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.time.LocalDate;

import com.Venta_LB.cl.VentaLuxeBags.Model.ClienteDTO;
import com.Venta_LB.cl.VentaLuxeBags.Model.Cupon;
import com.Venta_LB.cl.VentaLuxeBags.Model.Detalle_Venta;
import com.Venta_LB.cl.VentaLuxeBags.Model.ProductoDTO;
import com.Venta_LB.cl.VentaLuxeBags.Model.Venta;
import com.Venta_LB.cl.VentaLuxeBags.Repository.CuponRepository;
import com.Venta_LB.cl.VentaLuxeBags.Repository.DetalleventaRepository;
import com.Venta_LB.cl.VentaLuxeBags.Repository.VentaRepository;
import com.Venta_LB.cl.VentaLuxeBags.Service.ClienteRestClient;
import com.Venta_LB.cl.VentaLuxeBags.Service.ProductoRestClient;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner{

    @Autowired
    private DetalleventaRepository detalleventaRepository;

    @Autowired
    private CuponRepository cuponRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ClienteRestClient clienteRestClient;

    @Autowired
    private ProductoRestClient productoRestClient;
    
    @Override
        public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();
        List<Detalle_Venta> detalles = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Detalle_Venta detalleVenta = new Detalle_Venta();
            detalleVenta.setId(i); 
            detalleVenta.setIdProducto(i);;
            int cantidad = faker.number().numberBetween(1, 10);
            detalleVenta.setCantidad(cantidad);
            double precioUnitario = faker.number().randomDouble(2, 5, 100);
            detalleVenta.setPrecioUnitario(precioUnitario);
            detalleVenta.setTotal(cantidad * precioUnitario);
            detalleventaRepository.save(detalleVenta);
            detalles.add(detalleVenta);
        }


        List<Cupon> cupones = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Cupon cupon = new Cupon();
            cupon.setCodigo("CUPON" + faker.number().digits(5));
            BigDecimal descuento = BigDecimal.valueOf(faker.number().randomDouble(2, 5, 50));
            cupon.setDescuento(descuento);
            LocalDate fechaFutura = LocalDate.now().plusDays(faker.number().numberBetween(5, 60));
            cupon.setFechaExpiracion(fechaFutura.toString()); 
            cupon.setUsosRestantes(faker.number().numberBetween(1, 10));
            cupon.setActivo(true);
            cuponRepository.save(cupon);
            cupones.add(cupon);
        }

        List<ClienteDTO> clientes = clienteRestClient.obtenerClientes();
        List<ProductoDTO> producto = productoRestClient.obtenerProductos();
        for (int i = 0; i < 3; i++) {
            Venta venta = new Venta();
            Detalle_Venta detalle = detalles.get(i); 
            Cupon cupon = cupones.get(i); 

            venta.setIdProducto(producto.get(i).getIdProducto());
            venta.setIdCliente(clientes.get(i).getIdCliente());
            venta.setTotal(detalle.getTotal());
            venta.setFecha(LocalDate.now().toString());
            venta.setDetalleventa(detalle);
            venta.setCupon(cupon);

            ventaRepository.save(venta);
        }
    }
}



