package com.Venta_LB.cl.VentaLuxeBags.Controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import com.Venta_LB.cl.VentaLuxeBags.Model.ClienteDTO;
import com.Venta_LB.cl.VentaLuxeBags.Model.Cupon;
import com.Venta_LB.cl.VentaLuxeBags.Model.Detalle_Venta;
import com.Venta_LB.cl.VentaLuxeBags.Model.ProductoDTO;
import com.Venta_LB.cl.VentaLuxeBags.Model.Venta;
import com.Venta_LB.cl.VentaLuxeBags.Service.ClienteRestClient;
import com.Venta_LB.cl.VentaLuxeBags.Service.VentaService;


@WebMvcTest(VentaController.class)
public class VentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VentaService ventaService;

    @MockBean
    private ClienteRestClient clienteRestClient;

    @Autowired
    private ObjectMapper objectMapper;

    private Venta venta;
    private ClienteDTO clienteMock;
    private ProductoDTO productoMock;

    @BeforeEach
    void setUp() {

        clienteMock = new ClienteDTO(4, "RUT123456", "Juan", "Pérez", "1980-01-01", "juan@example.com", "123456789");
        productoMock = new ProductoDTO(1, "Smartphone Galaxy", "Smartphone de última generación", "299990");


        venta = new Venta();
        venta.setId(1);
        venta.setIdProducto(1);;
        venta.setIdCliente(4);  
        venta.setTotal(100.0);
        venta.setFecha("2025-06-19");
        venta.setDetalleventa(new Detalle_Venta());
        venta.setCupon(new Cupon());
    }

    @Test
    public void testListarVentas() throws Exception {
        when(ventaService.listarVentas()).thenReturn(List.of(venta));
        when(clienteRestClient.obtenerClientePorId(4)).thenReturn(clienteMock);

        mockMvc.perform(get("/venta/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idProducto").value(1))
                .andExpect(jsonPath("$[0].idCliente").value(4));
    }

    @Test
    public void testRealizarVenta() throws Exception {
        when(ventaService.realizarVenta(any(Venta.class))).thenReturn("La venta con el ID: 1 fue agregada");

        mockMvc.perform(post("/venta/guardar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venta)))
                .andExpect(status().isCreated())
                .andExpect(content().string("La venta con el ID: 1 fue agregada"));
    }

    @Test
    public void testActualizarVenta() throws Exception {
        when(ventaService.buscarPoriD(1)).thenReturn(venta);
        when(ventaService.actualizarVenta(any(Venta.class))).thenReturn("Cliente con ID: 1 Actualizado");

        mockMvc.perform(put("/venta/Actualizar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venta)))
                .andExpect(status().isOk())
                .andExpect(content().string("Cliente con ID: 1 Actualizado"));
    }

    @Test
    public void testEliminarVenta() throws Exception {
        when(ventaService.buscarPoriD(1)).thenReturn(venta);
        when(ventaService.eliminarVenta(1)).thenReturn("Se elimino la venta con el ID: 1");

        mockMvc.perform(delete("/venta/eliminar/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Se elimino la venta con el ID: 1"));
    }

    @Test
    public void testBuscarVentaPorId() throws Exception {
        when(ventaService.buscarPoriD(1)).thenReturn(venta);
        when(clienteRestClient.obtenerClientePorId(4)).thenReturn(clienteMock);

        mockMvc.perform(get("/venta/listar/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProducto").value(1))
                .andExpect(jsonPath("$.idCliente").value(4));
    }
}


