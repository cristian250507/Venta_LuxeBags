package com.Venta_LB.cl.VentaLuxeBags.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import com.Venta_LB.cl.VentaLuxeBags.Model.Detalle_Venta;
import com.Venta_LB.cl.VentaLuxeBags.Model.Venta;
import com.Venta_LB.cl.VentaLuxeBags.Repository.VentaRepository;

@SpringBootTest
public class VentaServiceTest {

    @Autowired
    private VentaService ventaService;

    @MockBean
    private VentaRepository ventaRepository;

  
    @Test
    public void testListarVentas() {
        Venta venta = new Venta();
        venta.setIdProducto(1);
        venta.setIdCliente(1);
        venta.setTotal(100.0);
        venta.setFecha("2025-06-19");

        when(ventaRepository.findAll()).thenReturn(List.of(venta));

        List<Venta> ventas = ventaService.listarVentas();

        assertNotNull(ventas);
        assertEquals(1, ventas.size());
        assertEquals(1, ventas.get(0).getIdProducto());
    }

    @Test
    public void testRealizarVentaConDetalleRepetido() {
        Venta venta = new Venta();
        venta.setIdProducto(1);
        venta.setIdCliente(1);
        venta.setTotal(100.0);
        venta.setFecha("2025-06-19");

        Detalle_Venta detalleVenta = new Detalle_Venta();
        detalleVenta.setId(1);
        venta.setDetalleventa(detalleVenta);

        when(ventaRepository.existsByDetalleventaId(1)).thenReturn(true);

        String respuesta = ventaService.realizarVenta(venta);

        assertEquals("Ese ID de Detalle de Venta ya está asociado a otra venta.", respuesta);
    }

    @Test
    public void testRealizarVentaExitosa() {
        Venta venta = new Venta();
        venta.setIdProducto(1);;
        venta.setIdCliente(1);
        venta.setTotal(200.0);
        venta.setFecha("2025-06-19");

        Detalle_Venta detalleVenta = new Detalle_Venta();
        detalleVenta.setId(1);
        venta.setDetalleventa(detalleVenta);

        when(ventaRepository.existsByDetalleventaId(1)).thenReturn(false);
        when(ventaRepository.save(venta)).thenReturn(venta);

        String respuesta = ventaService.realizarVenta(venta);

        assertEquals("La venta con el ID: " + venta.getId() + " fue agregada", respuesta);
    }
    
    @Test
    public void testGuardarVentas() {
        Venta venta1 = new Venta();
        venta1.setIdProducto(1);;
        
        Venta venta2 = new Venta();
        venta2.setIdProducto(1);;

        List<Venta> ventas = List.of(venta1, venta2);

        when(ventaRepository.saveAll(ventas)).thenReturn(ventas);

        String respuesta = ventaService.guardarVentas(ventas);

        assertEquals("Hay: 2 Ventas guardadas con éxito", respuesta);
    }


    @Test
    public void testEliminarVenta() {
        Integer idVenta = 1;

        doNothing().when(ventaRepository).deleteById(idVenta);

        String respuesta = ventaService.eliminarVenta(idVenta);

        assertEquals("Se elimino la venta con el ID: " + idVenta, respuesta);
    }


    @Test
    public void testBuscarPorId() {
        Integer idVenta = 1;
        Venta venta = new Venta();
        venta.setId(idVenta);

        when(ventaRepository.findById(idVenta)).thenReturn(Optional.of(venta));

        Venta resultado = ventaService.buscarPoriD(idVenta);

        assertNotNull(resultado);
        assertEquals(idVenta, resultado.getId());
    }

    @Test
    public void testActualizarVenta() {
        Venta venta = new Venta();
        venta.setId(1);
        venta.setFecha("25/05/2025");;

        when(ventaRepository.save(venta)).thenReturn(venta);

        String respuesta = ventaService.actualizarVenta(venta);

         assertEquals("Venta con ID: " + venta.getId() + " actualizada", respuesta);
    }
}


